package com.bangkit.crealth.data.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.crealth.data.model.RegisterModel
import com.bangkit.crealth.data.response.RegisterResponse
import com.bangkit.crealth.data.user.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    private val _registerResult = MutableLiveData<RegisterResponse?>()
    val registerResult: MutableLiveData<RegisterResponse?>
        get() = _registerResult

    fun register(user: RegisterModel, context: Context) {
        viewModelScope.launch {
            val response = repository.register(user, context)
            _registerResult.value = response
        }
    }
}