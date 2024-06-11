package com.bangkit.crealth.data.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("nama") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("ttl") val ttl: String,
    @SerializedName("gender") val gender: String
)
