package com.yalematta.trendingrepos.ui.repos

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.transition.MaterialElevationScale
import com.yalematta.trendingrepos.R
import com.yalematta.trendingrepos.data.model.Repo
import com.yalematta.trendingrepos.databinding.FragmentReposBinding
import com.yalematta.trendingrepos.ui.repos.adapter.RepoClickListener
import com.yalematta.trendingrepos.ui.repos.adapter.ReposAdapter
import com.yalematta.trendingrepos.ui.repos.adapter.ReposLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_trending_repo.*
import java.util.*


@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos), RepoClickListener {

    private val viewModel by viewModels<ReposViewModel>()

    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReposBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val adapter = ReposAdapter(this)

        binding.apply {

            recycler.setHasFixedSize(true)

            recycler.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { adapter.retry() },
                footer = ReposLoadStateAdapter { adapter.retry() }
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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recycler.scrollToPosition(0)
                    val languageQuery = String.format(getString(R.string.query), query)
                    viewModel.searchRepos(languageQuery)
                    searchView.clearFocus()
                    (activity as AppCompatActivity).supportActionBar?.title =
                        String.format(
                            getString(R.string.language_name),
                            query.capitalize(Locale.ROOT)
                        )
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

    override fun onRepoClickListener(repo: Repo?) {
        if (repo != null && view != null) {
            showDetails(repo, requireView())
        }
    }

    private fun showDetails(repo: Repo, view: View) {
        val bundle = bundleOf("repo" to repo)
        val extras = FragmentNavigatorExtras(avatar to "userAvatar")
        Navigation.findNavController(view).navigate(R.id.action_repos_to_details, bundle, null, extras )
    }
}