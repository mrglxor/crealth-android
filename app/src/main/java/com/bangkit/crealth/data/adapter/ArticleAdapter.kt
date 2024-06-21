package com.bangkit.crealth.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.crealth.R
import com.bangkit.crealth.data.model.Article
import com.bangkit.crealth.databinding.ItemArticleListBinding
import com.bangkit.crealth.databinding.ItemLoadStateBinding
import com.bangkit.crealth.view.ArticleDetailActivity
import com.bangkit.crealth.view.fragment.ArticleFragment
import com.bumptech.glide.Glide

class ArticleAdapter : PagingDataAdapter<Article, RecyclerView.ViewHolder>(
    ARTICLE_COMPARATOR
) {

    interface OnLoadStateButtonClickListener {
        fun onPreviousClick()
        fun onNextClick()
    }

    private var listener: OnLoadStateButtonClickListener? = null

    fun setLoadStateButtonClickListener(listener: ArticleFragment) {
        this.listener = listener
    }

    private var hasPrevious: Boolean = false
    private var hasNext: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_article_list -> {
                val binding = ItemArticleListBinding.inflate(inflater, parent, false)
                ArticleViewHolder(binding)
            }
            R.layout.item_load_state -> {
                val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
                LoadStateViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                val article = getItem(position)
                article?.let {
                    holder.bind(it)
                }
            }
            is LoadStateViewHolder -> {
                holder.bindLoadState(hasPrevious, hasNext)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount && getItem(position) == null) {
            R.layout.item_load_state
        } else {
            R.layout.item_article_list
        }
    }

    fun setLoadState(hasPrevious: Boolean, hasNext: Boolean) {
        this.hasPrevious = hasPrevious
        this.hasNext = hasNext
        notifyItemChanged(itemCount - 1)
    }

    inner class ArticleViewHolder(private val binding: ItemArticleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Glide.with(binding.ivArticleList.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_default_article)
                .into(binding.ivArticleList)

            binding.titleArticleList.text = article.title
            val formattedDate = article.publishedAt.replace(Regex("T.*"), "")
            binding.dateArticleList.text = formattedDate

            binding.itemArticleList.setOnClickListener {
                val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
                intent.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, article)
                it.context.startActivity(intent)
            }
        }
    }

    inner class LoadStateViewHolder(private val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {

        private var hasPrevious: Boolean = false
        private var hasNext: Boolean = false
        init {
            binding.btnPrevious.setOnClickListener {
                listener?.onPreviousClick()
            }

            binding.btnNext.setOnClickListener {
                listener?.onNextClick()
            }
        }
        fun bindLoadState(hasPrevious: Boolean, hasNext: Boolean) {
            this.hasPrevious = hasPrevious
            this.hasNext = hasNext

            binding.btnPrevious.visibility = if (hasPrevious) View.VISIBLE else View.GONE
            binding.btnNext.visibility = if (hasNext) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}
