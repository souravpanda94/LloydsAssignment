package com.example.lloydsassignment.presentation.nav_graph

 sealed class Route(val route : String) {
     data object NewsList : Route(route = "NewsList")
     data object NewsDetail : Route(route = "NewsDetail")
}