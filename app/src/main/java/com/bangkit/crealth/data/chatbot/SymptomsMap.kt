package com.bangkit.crealth.data.chatbot

import com.google.gson.annotations.SerializedName

data class SymptomsMap (
    @SerializedName("symptoms")
    val symptoms: List<String>
)