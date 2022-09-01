package com.app.domain

data class Place(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val largeDescription: String,
    var images: List<ImagePlace>,
    val location: String,
    val latitude: String,
    val longitude: String,
    val favorite: Boolean
)

fun List<ImagePlace>.randomImage(): ImagePlace{
    return this.random()
}