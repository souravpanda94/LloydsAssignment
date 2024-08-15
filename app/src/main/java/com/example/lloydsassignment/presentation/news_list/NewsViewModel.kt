package com.example.lloydsassignment.presentation.news_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.lloydsassignment.domain.usecases.NewsUseCase
import kotlinx.coroutines.launch

/*
class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state
    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            try {
                val getNews = newsUseCase(
                    source = listOf("bbc-news", "abc-news", "al-jazeera-english")
                ).cachedIn(viewModelScope)
                _state.value = NewsState(newsItems = getNews)
            } catch (e: Exception) {
                _state.value = NewsState(error = e.message)
            }
        }
    }
}*/

class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private val _state = MutableLiveData<NewsState>()
    val state: MutableLiveData<NewsState> = _state

    init {
        getNews()
    }
    fun getNews() {
        viewModelScope.launch {
            try {
                val getNews = newsUseCase(
                    source = listOf("bbc-news", "abc-news", "al-jazeera-english")
                ).cachedIn(viewModelScope)
                _state.value = NewsState(newsItems = getNews)
            } catch (e: Exception) {
                _state.value = NewsState(error = e.message)
            }
        }
    }
}
