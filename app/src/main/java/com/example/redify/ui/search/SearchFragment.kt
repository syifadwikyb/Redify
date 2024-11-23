package com.example.redify.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.redify.R
import com.example.redify.adapters.SearchAdapter
import com.example.redify.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val searchViewModel by lazy {
        ViewModelProvider(requireActivity())[SearchViewModel::class.java]
    }
    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupAdapter()
        setupObserver()
    }

    private fun setupSearchView() {
        binding.searchView.queryHint = "Cari..."

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchViewModel.getBookByKeyword(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupAdapter() {
        searchAdapter = SearchAdapter(listOf())
        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.adapter = searchAdapter
    }

    private fun setupObserver() {
        searchViewModel.searchBook.observe(viewLifecycleOwner) { books ->
            searchAdapter.updateData(books)

            if (books.isNotEmpty()) {
                binding.rvBooks.visibility = View.VISIBLE
                binding.tvNoResult.visibility = View.GONE
            } else {
                binding.rvBooks.visibility = View.GONE
                binding.tvNoResult.visibility = View.VISIBLE
            }
        }
    }
}