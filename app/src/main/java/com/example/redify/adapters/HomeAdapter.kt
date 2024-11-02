package com.example.redify.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redify.data.remote.model.Data
import com.example.redify.databinding.ItemEventBinding
import java.text.DecimalFormat

class HomeAdapter (private var items: List<Data>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class HomeViewHolder(
        private val itemBinding: ItemEventBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
            Glide.with(itemBinding.root)
                .load(data.thumbnail)
                .centerCrop()
                .into(itemBinding.thumbnail)
            itemBinding.title.text = data.title
            itemBinding.author.text = data.authors.joinToString(", ")
            val formatter = DecimalFormat("#.###,00")
            itemBinding.price.text = "Rp ${formatter.format(data.listPrice)}"
            itemBinding.publishedDate.text = data.publishedDate
        }
    }
}