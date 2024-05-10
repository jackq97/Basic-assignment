package com.example.basicassignment.main_screen

import com.example.basicassignment.model.VideoData

data class MainStates(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = "",
    val allVideos: List<VideoData> = emptyList(),
    val filteredVideos: List<VideoData> = emptyList(),
)