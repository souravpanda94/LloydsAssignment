package com.example.lloydsassignment.presentation.news_list

import androidx.paging.PagingData
import com.example.lloydsassignment.data.remote.model.NewsItems
import kotlinx.coroutines.flow.Flow

/*
data class NewsState (
    val newsItems : Flow<PagingData<NewsItems>>? = flowOf(PagingData.empty()),
    val error : String? = null
)*/

data class NewsState(
    val newsItems: Flow<PagingData<NewsItems>>? = null,
    val error: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NewsState) return false

        if (newsItems != other.newsItems) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = newsItems?.hashCode() ?: 0
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }
}