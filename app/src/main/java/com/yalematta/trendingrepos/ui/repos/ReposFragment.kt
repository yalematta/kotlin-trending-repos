package com.yalematta.trendingrepos.ui.repos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yalematta.trendingrepos.R
import com.yalematta.trendingrepos.databinding.FragmentReposBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}