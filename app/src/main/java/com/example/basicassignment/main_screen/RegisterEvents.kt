package com.example.basicassignment.main_screen

sealed class MainEvents {

    data class GetFilteredVideoList(val searchText: String): MainEvents()
}