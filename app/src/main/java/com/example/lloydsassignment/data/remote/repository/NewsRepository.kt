package com.example.lloydsassignment.data.remote.repository


import androidx.paging.PagingData
import com.example.lloydsassignment.data.remote.model.NewsItems
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    fun getNews (sources: List<String>) : Flow<PagingData<NewsItems>>
}
