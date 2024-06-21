package com.bangkit.crealth.data.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("message")
    val message: String? = null,

    @SerializedName("error")
    val error: String? = null
)