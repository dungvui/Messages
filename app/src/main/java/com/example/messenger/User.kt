package com.example.messenger

import androidx.annotation.DrawableRes

data class User(
    val id: Int,
    val name: String,
    val message: String,
    val time: String,
    var isImage: Boolean = false,
    @DrawableRes val image: Int?
)