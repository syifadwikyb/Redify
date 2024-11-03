package com.example.redify.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.redify.R
import com.example.redify.adapters.HomeAdapter
import com.example.redify.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        getEvent()
        setupObservers()
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    private fun setupRecyclerView() {
        homeAdapter = HomeAdapter(listOf())
        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.adapter = homeAdapter
    }

    private fun getEvent() {
        homeViewModel.getBooks()
    }

    private fun setupObservers() {
        homeViewModel.homeBook.observe(viewLifecycleOwner) { listBook ->
            homeAdapter.updateData(listBook)
        }
        homeViewModel.exception.observe(viewLifecycleOwner) { error ->
            if (error) {
                Toast.makeText(requireContext(), "Tidak ada internet", Toast.LENGTH_SHORT).show()
                homeViewModel.clearException()
            }
        }
    }

}