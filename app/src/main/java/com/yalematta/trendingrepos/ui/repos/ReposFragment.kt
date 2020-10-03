package com.yalematta.trendingrepos.ui.repos

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yalematta.trendingrepos.R
import com.yalematta.trendingrepos.databinding.FragmentReposBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos) {

    private val viewModel by viewModels<ReposViewModel>()

    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReposBinding.bind(view)

        val adapter = ReposAdapter()

        binding.apply {
            recycler.setHasFixedSize(true)
            recycler.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter{ adapter.retry() },
                footer = ReposLoadStateAdapter{ adapter.retry() }
            )
        }

        viewModel.repos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_repos, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    binding.recycler.scrollToPosition(0)
                    val languageQuery = String.format(getString(R.string.query), query)
                    viewModel.searchRepos(languageQuery)
                    searchView.clearFocus()
                    (activity as AppCompatActivity).supportActionBar?.title =
                        String.format(getString(R.string.language_name), query.capitalize(Locale.ROOT))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}