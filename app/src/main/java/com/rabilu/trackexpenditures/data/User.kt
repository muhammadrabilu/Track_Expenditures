package com.rabilu.trackexpenditures.data

data class User(
    val name: String,
    val email: String,
    val profileImageURL: String? = null,
)