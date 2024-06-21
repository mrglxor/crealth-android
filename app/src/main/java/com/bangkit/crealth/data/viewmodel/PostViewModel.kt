package com.bangkit.crealth.data.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.crealth.data.forum.ForumRepository
import com.bangkit.crealth.data.model.PostModel
import com.bangkit.crealth.data.response.PostResponse
import com.bangkit.crealth.data.user.UserModel
import kotlinx.coroutines.launch

class PostViewModel(private val repository: ForumRepository): ViewModel() {

    private val _postResult = MutableLiveData<PostResponse>()
    val postResult: LiveData<PostResponse>
        get() = _postResult

    fun createPost(data: PostModel, context: Context){
        viewModelScope.launch {
            val response = repository.createPost(data,context)
            _postResult.value = response
        }
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}