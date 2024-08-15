package com.example.lloydsassignment.presentation.news_list

import androidx.paging.PagingData
import com.example.lloydsassignment.data.remote.model.NewsItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class NewsState (
    val newsItems : Flow<PagingData<NewsItems>>? = flowOf(PagingData.empty()),
    val error : String? = null
)