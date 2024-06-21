package com.bangkit.crealth.data.response

import com.google.gson.annotations.SerializedName

data class PublicResponse(
    @SerializedName("data")
    val data: List<PublicData>? = null,

    @SerializedName("error")
    val error: String? = null
)

data class PublicData(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("post")
    val post: String? = null
)