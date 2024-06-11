package com.bangkit.crealth.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: UserData? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("error")
    val error: String? = null
)

data class UserData(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("email_verify_at")
    val emailVerifyAt: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("ttl")
    val ttl: String? = null,

    @SerializedName("rememberToken")
    val rememberToken: String? = null,

    @SerializedName("tokenExpiry")
    val tokenExpiry: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,
)