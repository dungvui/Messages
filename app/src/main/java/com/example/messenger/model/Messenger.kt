package com.example.messenger.model

data class Messenger(
    val id: Int,
    val msg: String,
    val userSent: User,
    val time: String,
    val isImage: Boolean,
)