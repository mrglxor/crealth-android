package com.bangkit.crealth.data.article

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bangkit.crealth.data.api.ApiService
import com.bangkit.crealth.data.model.Article
import kotlinx.coroutines.flow.Flow

class ArticleRepository(
    private val apiService: ApiService
) {
    fun getArticle(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePagingSource(apiService) }
        ).flow
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(apiService: ApiService): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService).also { instance = it }
            }
    }
}