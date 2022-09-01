package com.app.domain

data class Comment(
    val id: String,
    var idPlace: Int,
    var idUser: Int,
    val timeRegister: String,
    val nameUser: String,
    val commentText: String
)