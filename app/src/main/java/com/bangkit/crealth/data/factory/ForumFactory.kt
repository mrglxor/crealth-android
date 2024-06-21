package com.bangkit.crealth.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.crealth.data.api.Injection
import com.bangkit.crealth.data.forum.ForumRepository
import com.bangkit.crealth.data.viewmodel.PostViewModel
import com.bangkit.crealth.data.viewmodel.PublicViewModel

class ForumFactory(private val repo: ForumRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PostViewModel::class.java) -> {
                PostViewModel(repo) as T
            }
            modelClass.isAssignableFrom(PublicViewModel::class.java) -> {
                PublicViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ForumFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ForumFactory {
            if (INSTANCE == null) {
                synchronized(ForumFactory::class.java) {
                    INSTANCE = ForumFactory(Injection.provideForumRepository(context))
                }
            }
            return INSTANCE as ForumFactory
        }
    }
}