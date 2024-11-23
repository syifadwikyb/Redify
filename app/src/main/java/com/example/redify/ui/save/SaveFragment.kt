package com.example.redify.ui.save

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.redify.R
import com.example.redify.adapters.SaveAdapter
import com.example.redify.databinding.FragmentSaveBinding

class SaveFragment : Fragment(R.layout.fragment_save) {
    private val binding by viewBinding(FragmentSaveBinding::bind)
    private val saveViewModel by lazy {
        ViewModelProvider(requireActivity())[SaveViewModel::class.java]
    }
    private lateinit var saveAdapter: SaveAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        getBooks()
    }

    private fun setupAdapter() {
        saveAdapter = SaveAdapter(listOf())
        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.adapter = saveAdapter
    }

    private fun getBooks() {
        saveViewModel.getFavBooks()
    }

    private fun setupObserver() {
        saveViewModel.favBook.observe(viewLifecycleOwner) {
            saveAdapter.updateData(it)
        }
    }
}
