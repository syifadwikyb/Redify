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
import com.example.redify.data.remote.network.RetrofitClient
import com.example.redify.data.repository.EventRepository
import com.example.redify.databinding.FragmentHomeBinding
import com.example.redify.utils.ViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        val eventRepository = EventRepository(RetrofitClient.instance)
        val factory = ViewModelFactory(eventRepository)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    private fun setupRecyclerView() {
        homeAdapter = HomeAdapter(listOf())
        binding.recyclerVertical.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerVertical.adapter = homeAdapter
    }

    private fun getEvent() {
        homeViewModel.getVerHor()
    }

    private fun setupObservers() {
        homeViewModel.homeVerHor.observe(viewLifecycleOwner) {
            homeViewModel.updateData(it)
        }
        homeViewModel.exception.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Tidak ada internet", Toast.LENGTH_SHORT).show()
                homeViewModel.clearException()
            }
        }
    }

}