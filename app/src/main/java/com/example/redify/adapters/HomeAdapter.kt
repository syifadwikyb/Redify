package com.example.redify.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redify.data.remote.model.BookItem
import com.example.redify.databinding.ItemEventBinding
import com.example.redify.ui.details.DetailActivity
import com.example.redify.utils.BookUtil
import com.example.redify.utils.PriceFormat
import java.text.DecimalFormat

class HomeAdapter (private var items: List<BookItem>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(newItems: List<BookItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(
        private val itemBinding: ItemEventBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: BookItem) {
            Glide.with(itemBinding.root)
                .load(data.volumeInfo.imageLinks?.smallThumbnail)
                .centerCrop()
                .into(itemBinding.thumbnail)
            itemBinding.title.text = data.volumeInfo.title
            itemBinding.author.text = data.volumeInfo.authors?.joinToString(", ")
            if (data.saleInfo.listPrice?.amount != null) {
                itemBinding.price.text = "Rp ${PriceFormat.thousandSeparator(data.saleInfo.listPrice.amount.toInt())}"
            }
            itemBinding.publishedDate.text = data.volumeInfo.publishedDate

            itemBinding.itemContainer.setOnClickListener {
                BookUtil.bookId = data.id
                itemView.context.startActivity(Intent(itemView.context, DetailActivity::class.java))
            }
        }
    }
}