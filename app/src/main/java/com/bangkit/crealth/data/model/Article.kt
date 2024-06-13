package com.bangkit.crealth.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val image: Int,
    val title: String,
    val date: String
) : Parcelable
