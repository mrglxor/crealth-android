package com.bangkit.crealth.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.bangkit.crealth.data.article.ArticleRepository
import com.bangkit.crealth.data.model.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: ArticleRepository) : ViewModel() {

    private val _articles = MutableLiveData<PagingData<Article>>()
    val articles: LiveData<PagingData<Article>> get() = _articles

    private val _filteredArticles = MediatorLiveData<PagingData<Article>>()
    val filteredArticles: LiveData<PagingData<Article>> get() = _filteredArticles

    private var searchJob: Job? = null

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            repository.getArticle().cachedIn(viewModelScope).collectLatest { pagingData ->
                _articles.postValue(pagingData)
            }
        }
    }

    fun searchArticles(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _filteredArticles.removeSource(articles)
            if (query.isBlank()) {
                _filteredArticles.addSource(articles) { pagingData ->
                    _filteredArticles.value = pagingData
                }
            } else {
                delay(300)
                _filteredArticles.addSource(articles) { pagingData ->
                    val filteredData = pagingData.filter { article ->
                        article.title.contains(query, ignoreCase = true)
                    }
                    _filteredArticles.value = filteredData
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _filteredArticles.removeSource(articles)
    }
}
