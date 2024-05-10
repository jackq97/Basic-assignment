package com.example.basicassignment.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoData(
    val id: Int = 1,
    val title: String = "Title",
    val description: String = "Description",
    val channel: String = "Channel",
    val likes: Int = 1,
    val comments: Int = 1,
    val views: Int = 1,
    val date: Int = 1
)
