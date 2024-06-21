package com.bangkit.crealth.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.crealth.R
import com.bangkit.crealth.data.model.Article
import com.bangkit.crealth.databinding.ActivityArticleDetailBinding
import com.bumptech.glide.Glide

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding
    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val article = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_ARTICLE,Article::class.java)
        }else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ARTICLE)
        }

        if(article != null){
            article.urlToImage?.let {
                Glide.with(binding.ivArticleDetail.context)
                    .load(it)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(binding.ivArticleDetail)
            }
            binding.tvTitleDetail.text = article.title
            val text = "${article.source.id} | ${article.publishedAt.replace(Regex("T.*"),"")}"
            binding.desc.text = text
            binding.detail.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                startActivity(intent)
            }
        }
    }
}