package com.yalematta.trendingrepos.ui.repos

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.SearchAutoComplete
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.LoadState
import com.yalematta.trendingrepos.R
import com.yalematta.trendingrepos.data.model.Languages
import com.yalematta.trendingrepos.data.model.Repo
import com.yalematta.trendingrepos.databinding.FragmentReposBinding
import com.yalematta.trendingrepos.ui.repos.adapter.ReposAdapter
import com.yalematta.trendingrepos.ui.repos.adapter.ReposLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_trending_repo.*
import java.util.*


@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos) {

    private val viewModel by viewModels<ReposViewModel>()

    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        _binding = FragmentReposBinding.bind(view)

        val adapter = ReposAdapter()

        binding.apply {

            recycler.apply {
                setHasFixedSize(true)
                itemAnimator = null
                this.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = ReposLoadStateAdapter { adapter.retry() },
                    footer = ReposLoadStateAdapter { adapter.retry() }
                )
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.repos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progress.isVisible = loadState.source.refresh is LoadState.Loading
                recycler.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                error.isVisible = loadState.source.refresh is LoadState.Error

                // no results found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recycler.isVisible = false
                    emptyTv.isVisible = true
                } else {
                    emptyTv.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_repos, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchAutoComplete: SearchAutoComplete = searchView.findViewById(R.id.search_src_text)

        // Get SearchView autocomplete object
        searchAutoComplete.setTextColor(Color.WHITE)
        searchAutoComplete.setDropDownBackgroundResource(R.color.colorPrimary)

        val newsAdapter: ArrayAdapter<String> = ArrayAdapter(
            this.requireContext(),
            R.layout.dropdown_item,
            Languages.data
        )
        searchAutoComplete.setAdapter(newsAdapter)

        // Listen to search view item on click event
        searchAutoComplete.onItemClickListener =
            OnItemClickListener { adapterView, _, itemIndex, _ ->
                val queryString = adapterView.getItemAtPosition(itemIndex) as String
                searchAutoComplete.setText(String.format(getString(R.string.search_query), queryString))
                binding.recycler.scrollToPosition(0)
                val languageQuery = String.format(getString(R.string.query), queryString)
                viewModel.searchRepos(languageQuery)
                searchView.clearFocus()
                (activity as AppCompatActivity).supportActionBar?.title =
                    String.format(
                        getString(R.string.language_name),
                        queryString.capitalize(Locale.ROOT)
                    )
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}