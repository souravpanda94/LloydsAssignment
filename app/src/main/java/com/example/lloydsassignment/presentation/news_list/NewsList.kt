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
import com.example.lloydsassignment.core.Dimensions
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
            verticalArrangement = Arrangement.spacedBy(Dimensions.heightSmall),
            contentPadding = PaddingValues(all = Dimensions.spacerHeightSmall)
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
        Column(verticalArrangement = Arrangement.spacedBy(Dimensions.height15)) {
            repeat(10) {
                ArticleCardShimmerEffect(
                    modifier = Modifier.padding(all = Dimensions.padding5)
                )
            }
        }
    }
}