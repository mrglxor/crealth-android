package com.bangkit.crealth.data.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.crealth.data.forum.ForumRepository
import com.bangkit.crealth.data.response.PublicResponse
import kotlinx.coroutines.launch

class PublicViewModel(private val repository: ForumRepository): ViewModel() {

    private val _publicResult = MutableLiveData<PublicResponse>()
    val publicResult: LiveData<PublicResponse>
        get() = _publicResult

    fun getPost(context: Context){
        viewModelScope.launch {
            val response = repository.getPost(context)
            _publicResult.value = response
        }
    }
}