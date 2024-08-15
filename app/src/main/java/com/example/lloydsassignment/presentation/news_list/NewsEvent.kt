package com.example.lloydsassignment.presentation.news_list

sealed class NewsEvent {
    data object articles : NewsEvent()
}