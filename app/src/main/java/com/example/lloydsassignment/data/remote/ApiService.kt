package com.example.lloydsassignment.data.remote

import com.example.lloydsassignment.data.remote.model.NewsResponse
import com.example.lloydsassignment.core.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getNews(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = Constants.APP_KEY
    ): NewsResponse
}