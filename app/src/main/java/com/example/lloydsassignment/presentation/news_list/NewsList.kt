package com.example.lloydsassignment.presentation.news_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.lloydsassignment.presentation.common_module.ArticleCardShimmerEffect
import com.example.lloydsassignment.data.remote.model.NewsItems
import com.example.lloydsassignment.presentation.common_module.NewsCard

@Composable
fun GetNewsList(
    newsState: NewsState,
    navigateToDetails: (NewsItems) -> Unit
) {
    val articleItems = newsState.newsItems?.collectAsLazyPagingItems()
    if (articleItems?.itemCount != 0) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(all = 8.dp)
        ) {
            items(
                count = articleItems!!.itemCount,
            ) {
                articleItems[it]?.let { newsItems ->
                    NewsCard(newsItems = newsItems) { articlesItem ->
                        navigateToDetails(articlesItem)
                    }
                }
            }
        }
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            repeat(10) {
                ArticleCardShimmerEffect(
                    modifier = Modifier.padding(all = 5.dp)
                )
            }
        }
    }
}