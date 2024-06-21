package com.bangkit.crealth.data.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.crealth.R
import com.bangkit.crealth.data.response.BrandedFood
import com.bangkit.crealth.databinding.ItemArticleHomeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


class ArticleHomeAdapter(private val itemList: List<BrandedFood>) : RecyclerView.Adapter<ArticleHomeAdapter.ArticleHomeViewHolder>() {

    class ArticleHomeViewHolder(private val binding: ItemArticleHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BrandedFood) {
            Glide.with(binding.ivImg.context).load(item.photo.thumb)
                .apply(RequestOptions().error(R.drawable.ic_place_holder))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivImg)
            binding.tvTitle.text = item.brandNameItemName
            val text = "${item.servingQty} ${item.servingUnit} - ${item.calories} calories"
            binding.tvDesc.text = text

            binding.cvArticleHome.setOnClickListener {
                val url = "https://www.google.com/search?q=${item.brandNameItemName}"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHomeViewHolder {
        val binding = ItemArticleHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleHomeViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size
}
