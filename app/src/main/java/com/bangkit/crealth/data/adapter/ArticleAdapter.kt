package com.bangkit.crealth.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.crealth.data.model.Article
import com.bangkit.crealth.databinding.ItemArticleListBinding
import com.bangkit.crealth.view.ArticleDetailActivity

class ArticleAdapter(private val articleList: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val binding: ItemArticleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articleList: Article) {
            binding.ivArticleList.setImageResource(articleList.image)
            binding.titleArticleList.text = articleList.title
            binding.dateArticleList.text = articleList.date

            val article = Article(articleList.id,articleList.image,articleList.title,articleList.date)

            binding.itemArticleList.setOnClickListener {
                val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
                intent.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, article)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount() = articleList.size
}