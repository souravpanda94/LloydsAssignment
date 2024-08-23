package com.example.lloydsassignment.domain.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.lloydsassignment.data.remote.ApiService
import com.example.lloydsassignment.data.remote.repository.NewsRepository
import com.example.lloydsassignment.data.remote.model.NewsItems
import com.example.lloydsassignment.data.remote.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(private val newsApi: ApiService) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<NewsItems>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    apiService = newsApi,
                    source = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }
}


