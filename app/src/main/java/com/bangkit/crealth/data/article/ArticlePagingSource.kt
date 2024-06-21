package com.bangkit.crealth.data.article

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.crealth.data.api.ApiService
import com.bangkit.crealth.data.model.Article

private const val STARTING_PAGE_INDEX = 1

class ArticlePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX
        val pageSize = params.loadSize
        return try {
            val response = apiService.article(page, pageSize)
            LoadResult.Page(
                data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}