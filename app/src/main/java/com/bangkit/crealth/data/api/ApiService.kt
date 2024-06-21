package com.bangkit.crealth.data.api

import com.bangkit.crealth.data.chatbot.SymptomsMap
import com.bangkit.crealth.data.model.Article
import com.bangkit.crealth.data.model.LoginModel
import com.bangkit.crealth.data.model.PostModel
import com.bangkit.crealth.data.model.RegisterModel
import com.bangkit.crealth.data.response.DiagnosisResponse
import com.bangkit.crealth.data.response.LoginResponse
import com.bangkit.crealth.data.response.PostResponse
import com.bangkit.crealth.data.response.PublicResponse
import com.bangkit.crealth.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body user: RegisterModel
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body user: LoginModel
    ): LoginResponse
    @GET("article")
    suspend fun article(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Article>
    @POST("post")
    suspend fun createPost(
        @Body dataPost: PostModel
    ): PostResponse
    @GET("post")
    suspend fun getPost(): PublicResponse
}

interface PredictApiService {

    @POST("predict")
    fun submitSymptoms(@Body symptoms: SymptomsMap): Call<DiagnosisResponse>
}