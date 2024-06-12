package com.bangkit.crealth.data.user

data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val isLogin: Boolean = false,
    val token: String = ""
)
