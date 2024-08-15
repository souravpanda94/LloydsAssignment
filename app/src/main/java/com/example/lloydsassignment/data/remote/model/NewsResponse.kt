package com.example.lloydsassignment.data.remote.model


import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @field:SerializedName("totalResults")
	val totalResults: Int? = null,

    @field:SerializedName("articles")
	val articles: List<NewsItems?>? = null,

    @field:SerializedName("status")
	val status: String? = null
)