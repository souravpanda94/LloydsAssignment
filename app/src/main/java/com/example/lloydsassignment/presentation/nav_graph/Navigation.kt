package com.example.lloydsassignment.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lloydsassignment.data.remote.model.NewsItems
import com.example.lloydsassignment.presentation.news_list.NewsViewModel
import com.example.lloydsassignment.presentation.news_list.GetNewsList
import com.example.lloydsassignment.presentation.new_detail_screen.DetailScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.NewsList.route) {
        composable(route = Route.NewsList.route) {
            val newsViewModel = koinViewModel<NewsViewModel>()
            GetNewsList(
//                newsState = newsViewModel.state.collectAsState().value,
                newsState = newsViewModel.state.value!!,
                navigateToDetails = {
                    navigateToDetails(navController = navController, newsItems = it)
                }
            )
        }
        composable(route = Route.NewsDetail.route){
            navController.previousBackStackEntry?.savedStateHandle?.get<NewsItems?>("newsItem")
                ?.let { news ->
                    DetailScreen(
                        newsItem = news,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
        }
    }
}

private fun navigateToDetails(navController: NavController, newsItems: NewsItems){
    navController.currentBackStackEntry?.savedStateHandle?.set("newsItem", newsItems)
    navController.navigate(
        route = Route.NewsDetail.route
    )
}