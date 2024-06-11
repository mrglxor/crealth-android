package com.bangkit.crealth.data.api

import com.bangkit.crealth.data.model.LoginModel
import com.bangkit.crealth.data.model.RegisterModel
import com.bangkit.crealth.data.response.LoginResponse
import com.bangkit.crealth.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body user: RegisterModel
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body user: LoginModel
    ): LoginResponse
}