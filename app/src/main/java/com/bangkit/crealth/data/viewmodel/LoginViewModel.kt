package com.bangkit.crealth.data.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.bangkit.crealth.data.model.LoginModel
import com.bangkit.crealth.data.response.LoginResponse
import com.bangkit.crealth.data.user.UserModel
import com.bangkit.crealth.data.user.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse>
        get() = _loginResult

    fun login(user: LoginModel,context: Context){
        viewModelScope.launch {
                val response = repository.login(user,context)
                _loginResult.value = response
        }
    }

    fun saveSession(user: UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}