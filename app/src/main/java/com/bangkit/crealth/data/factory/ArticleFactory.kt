package com.bangkit.crealth.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.crealth.data.api.Injection
import com.bangkit.crealth.data.article.ArticleRepository
import com.bangkit.crealth.data.viewmodel.ArticleViewModel

class ArticleFactory(private val repo: ArticleRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                ArticleViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ArticleFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ArticleFactory {
            if (INSTANCE == null) {
                synchronized(ArticleFactory::class.java) {
                    INSTANCE = ArticleFactory(Injection.provideArticleRepository(context))
                }
            }
            return INSTANCE as ArticleFactory
        }
    }
}