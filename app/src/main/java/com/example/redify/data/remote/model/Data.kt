package com.example.redify.data.remote.model

data class Data(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val categories: List<String>,
    val maturityRating: String,
    val thumbnail: String,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val canonicalVolumeLink: String,
    val listPrice: Double,
    val retailPrice: Double,
    val currencyCode: String,
    val isEbook: Boolean,
    val buyLink: String
)
