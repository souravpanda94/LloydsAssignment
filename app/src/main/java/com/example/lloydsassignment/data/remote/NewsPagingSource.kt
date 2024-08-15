package com.example.lloydsassignment.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lloydsassignment.data.remote.model.NewsItems

class NewsPagingSource(
    val apiService: ApiService,
    val source : String
) : PagingSource<Int, NewsItems>() {
    override fun getRefreshKey(state: PagingState<Int, NewsItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    private var totalNewsCount = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItems> {
        val page = params.key ?: 1
        return try {
            val newsResponse = apiService.getNews(sources = source, page = page)
            totalNewsCount += newsResponse.articles!!.size
            val articles: List<NewsItems> = newsResponse.articles.distinctBy { it?.title } as List<NewsItems>

            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}