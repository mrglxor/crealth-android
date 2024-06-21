package com.bangkit.crealth.data.model

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("name") val name: String,
    @SerializedName("post") val post: String
)
