package com.example.redify.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.redify.R
import com.example.redify.databinding.ActivityDetailBinding
import com.example.redify.utils.BookUtil
import com.example.redify.utils.PriceFormat

class DetailActivity : AppCompatActivity(R.layout.activity_detail) {
    private val binding by viewBinding(ActivityDetailBinding::bind)
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupButton()
        getDetail()
        setupObserver()
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    private fun setupButton() {
        binding.backButton.setOnClickListener{
            finish()
        }
    }

    private fun getDetail() {
        detailViewModel.getBookDetail(BookUtil.bookId)
    }

    private fun setupObserver() {
        detailViewModel.bookDetail.observe(this) {
            Glide.with(this)
                .load(it.volumeInfo.imageLinks?.smallThumbnail)
                .centerCrop()
                .into(binding.thumbnail)
            binding.title.text = it.volumeInfo.title
            binding.descriptionContent.text = it.volumeInfo.description
            binding.authorContent.text = it.volumeInfo.authors?.joinToString(", ")
            binding.publisherContent.text = it.volumeInfo.publisher
            binding.publishedDateContent.text = it.volumeInfo.publishedDate
            binding.categoriesContent.text = it.volumeInfo.pageCount.toString()
            if (it.saleInfo.listPrice?.amount != null) {
                binding.priceContent.text = "Rp ${PriceFormat.thousandSeparator(it.saleInfo.listPrice.amount.toInt())}"
            }
        }
    }
}