package com.bangkit.crealth.data.response

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

fun HttpException.toRegisterResponse(): RegisterResponse {
    val errorBody = this.response()?.errorBody()?.string()
    return if (!errorBody.isNullOrBlank()) {
        try {
            val parsedResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            val errorMessage = parsedResponse?.error ?: "Unknown error"
            RegisterResponse(error = errorMessage)
        } catch (e: JsonSyntaxException) {
            RegisterResponse(error = "Server Error!")
        } catch (e: IllegalStateException) {
            RegisterResponse(error = "Server Error!")
        }
    } else {
        RegisterResponse(error = "Unknown error")
    }
}

fun Exception.toRegisterResponse(): RegisterResponse {
    return RegisterResponse(this.message ?: "Unknown error")
}

fun toRegisterResponse(): RegisterResponse {
    return RegisterResponse(error = "No internet connection")
}

fun HttpException.toLoginResponse(): LoginResponse {
    val errorBody = this.response()?.errorBody()?.string()
    return if (!errorBody.isNullOrBlank()) {
        try {
            val parsedResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            val errorMessage = parsedResponse?.error ?: "Unknown error"
            LoginResponse(error = errorMessage)
        } catch (e: JsonSyntaxException) {
            LoginResponse(error = "Server Error!")
        } catch (e: IllegalStateException) {
            LoginResponse(error = "Server Error!")
        }
    } else {
        LoginResponse(error = "Unknown error")
    }
}

fun Exception.toLoginResponse(): LoginResponse {
    return LoginResponse(message = this.message ?: "Unknown error")
}

fun toLoginResponse(): LoginResponse {
    return LoginResponse(error = "No internet connection")
}