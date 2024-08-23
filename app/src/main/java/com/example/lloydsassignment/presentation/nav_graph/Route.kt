package com.example.lloydsassignment.presentation.nav_graph

sealed class Route(val route: String) {
    object NewsList : Route(route = "NewsList")
    object NewsDetail : Route(route = "NewsDetail")
}