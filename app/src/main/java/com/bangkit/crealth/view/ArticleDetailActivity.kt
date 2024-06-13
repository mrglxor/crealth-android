package com.bangkit.crealth.view

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.crealth.data.model.Article
import com.bangkit.crealth.databinding.ActivityArticleDetailBinding

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
            intent.getParcelableExtra<Article>(EXTRA_ARTICLE,Article::class.java)
        }else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Article>(EXTRA_ARTICLE)
        }

        if(article != null){
            binding.ivArticleDetail.setImageResource(article.image)
            binding.tvTitleDetail.text = article.title
            val text = "id: ${article.id} - date: ${article.date}"
            binding.desc.text = text
        }
    }
}