package com.bangkit.crealth.data.api

import android.content.Context
import com.bangkit.crealth.data.article.ArticleRepository
import com.bangkit.crealth.data.forum.ForumRepository
import com.bangkit.crealth.data.user.UserPreference
import com.bangkit.crealth.data.user.UserRepository
import com.bangkit.crealth.data.user.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService,preference)
    }
    fun provideArticleRepository(context: Context): ArticleRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return ArticleRepository.getInstance(apiService)
    }
    fun provideForumRepository(context: Context): ForumRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return ForumRepository.getInstance(apiService,preference)
    }
}