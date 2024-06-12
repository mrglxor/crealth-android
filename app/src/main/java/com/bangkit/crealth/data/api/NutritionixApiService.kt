package com.bangkit.crealth.data.api

import com.bangkit.crealth.data.response.NutritionixResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NutritionixApiService {
    @GET("search/instant")
    suspend fun searchFood(
        @Query("query") query: String
    ): NutritionixResponse
}

object NutritionixApi {
    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("x-app-id", "6a804c32")
            .addHeader("x-app-key", "05c4c46ac7addbb144b381c57af934c7")
            .build()
        chain.proceed(request)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://trackapi.nutritionix.com/v2/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: NutritionixApiService = retrofit.create(NutritionixApiService::class.java)
}
