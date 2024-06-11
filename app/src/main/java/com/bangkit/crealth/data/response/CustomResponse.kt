package com.bangkit.crealth.data.response

import com.google.gson.Gson
import retrofit2.HttpException

fun HttpException.toRegisterResponse(): RegisterResponse {
    val errorBody = this.response()?.errorBody()?.string()
    val errorMessage = Gson().fromJson(errorBody, RegisterResponse::class.java).error
    return RegisterResponse(error =  errorMessage)
}

fun Exception.toRegisterResponse(): RegisterResponse {
    return RegisterResponse(this.message ?: "Unknown error")
}

fun toRegisterResponse(): RegisterResponse {
    return RegisterResponse(error = "No internet connection")
}

fun HttpException.toLoginResponse(): LoginResponse {
    val errorBody = this.response()?.errorBody()?.string()
    val errorMessage = Gson().fromJson(errorBody, LoginResponse::class.java).error
    return LoginResponse(error =  errorMessage)
}

fun Exception.toLoginResponse(): LoginResponse {
    return LoginResponse(message = this.message ?: "Unknown error")
}

fun toLoginResponse(): LoginResponse {
    return LoginResponse(error = "No internet connection")
}