package com.example.redify.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class Book (
    @PrimaryKey
    val id: String,
    val thumbnail: String,
    val title: String,
    val author: List<String>,
    val price: String?,
    val publishedDate: String
)