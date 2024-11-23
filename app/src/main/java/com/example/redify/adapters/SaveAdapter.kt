package com.example.redify.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redify.data.local.entity.Book
import com.example.redify.databinding.ItemSaveBinding
import com.example.redify.ui.details.DetailActivity
import com.example.redify.utils.BookUtil
import com.example.redify.utils.PriceFormat

class SaveAdapter (private var save: List<Book>) : RecyclerView.Adapter<SaveAdapter.SaveViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveAdapter.SaveViewHolder {
        val view = ItemSaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaveViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaveAdapter.SaveViewHolder, position: Int) {
        holder.bind(save[position])
    }

    override fun getItemCount() : Int = save.size

    fun updateData(newItems: List<Book>) {
        save = newItems
        notifyDataSetChanged()
    }

    inner class SaveViewHolder(
        private val itemBinding: ItemSaveBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(book: Book) {
            Glide.with(itemBinding.root)
                .load(book.thumbnail)
                .centerCrop()
                .into(itemBinding.thumbnail)
            itemBinding.title.text = book.title
            itemBinding.author.text = book.author
            itemBinding.price.text = "Rp ${book.price?.let { PriceFormat.thousandSeparator(it.toDouble().toInt()) }}"
            itemBinding.publishedDate.text = book.publishedDate

            itemBinding.itemContainer.setOnClickListener {
                BookUtil.bookId = book.id
                itemView.context.startActivity(Intent(itemView.context, DetailActivity::class.java))
            }
        }
    }
}