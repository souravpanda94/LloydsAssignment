package com.example.lloydsassignment.domain.usecases

import androidx.paging.PagingData
import com.example.lloydsassignment.data.remote.repository.NewsRepository
import com.example.lloydsassignment.data.remote.model.NewsItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(source: List<String>): Flow<PagingData<NewsItems>> {
        return newsRepository.getNews(sources = source)
    }
}