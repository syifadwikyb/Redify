package com.example.redify.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.redify.R
import com.example.redify.data.local.entity.Book
import com.example.redify.databinding.ActivityDetailBinding
import com.example.redify.utils.BookUtil
import com.example.redify.utils.PriceFormat

class DetailActivity : AppCompatActivity(R.layout.activity_detail) {
    private val binding by viewBinding(ActivityDetailBinding::bind)
    private lateinit var detailViewModel: DetailViewModel
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupButton()
        getDetail()
        isBookSaved()
        setupObserver()
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    private fun setupButton() {
        binding.backButton.setOnClickListener{
            finish()
        }
        binding.save.setOnClickListener {
            if (!isSaved) {
                binding.save.setImageResource(R.drawable.save_blue)
                saveBook()
            } else {
                binding.save.setImageResource(R.drawable.save_white)
                detailViewModel.deleteBook(BookUtil.bookId)
            }
            isSaved = !isSaved
        }
    }

    private fun getDetail() {
        detailViewModel.getBookDetail(BookUtil.bookId)
    }

    private fun isBookSaved() {
        detailViewModel.isBookSaved(BookUtil.bookId)
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        detailViewModel.bookDetail.observe(this) {
            Glide.with(this)
                .load(it.volumeInfo.imageLinks.smallThumbnail)
                .centerCrop()
                .into(binding.thumbnail)
            binding.title.text = it.volumeInfo.title

            if (it.volumeInfo.description != null) {
                val description = Html.fromHtml(it.volumeInfo.description, Html.FROM_HTML_MODE_LEGACY)
                binding.descriptionContent.text = description
            } else {
                binding.descriptionContent.text = "Not available"
            }

            if (it.volumeInfo.authors != null) {
                binding.authorContent.text = it.volumeInfo.authors.joinToString(", ")
            } else {
                binding.authorContent.text = "Not available"
            }

            binding.publisherContent.text = it.volumeInfo.publisher
            binding.publishedDateContent.text = it.volumeInfo.publishedDate
            binding.categoriesContent.text = it.volumeInfo.pageCount.toString()
            if (it.saleInfo.listPrice?.amount != null) {
                binding.priceContent.text = "Rp ${PriceFormat.thousandSeparator(it.saleInfo.listPrice.amount.toInt())}"
            } else {
                binding.priceContent.text = "Not for sale"
            }
            BookUtil.book = it
        }

        detailViewModel.isSaved.observe(this) {
            isSaved = it
            if (it) {
                binding.save.setImageResource(R.drawable.save_blue)
            } else {
                binding.save.setImageResource(R.drawable.save_white)
            }
        }
    }

    private fun saveBook() {
        if (BookUtil.book != null) {
            detailViewModel.saveBook(
                Book(
                    BookUtil.book!!.id,
                    BookUtil.book!!.volumeInfo.imageLinks.thumbnail,
                    BookUtil.book!!.volumeInfo.title,
                    BookUtil.book!!.volumeInfo.authors!!.joinToString(", "),
                    BookUtil.book!!.saleInfo.listPrice?.amount?.toString(),
                    BookUtil.book!!.volumeInfo.publishedDate
                )
            )
        }
    }
}