package com.example.lloydsassignment.presentation.news_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.lloydsassignment.domain.usecases.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {

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
}