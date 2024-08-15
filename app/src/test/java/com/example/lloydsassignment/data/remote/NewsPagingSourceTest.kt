package com.example.lloydsassignment.data.remote

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lloydsassignment.data.remote.model.NewsItems
import com.example.lloydsassignment.data.remote.model.NewsResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*

class NewsPagingSourceTest {

    private val apiService = mock(ApiService::class.java)
    private val source = "bbc-news,abc-news,al-jazeera-english"
    private val newsPagingSource = NewsPagingSource(apiService, source)

    @Test
    fun `load returns Page when on successful load`() = runBlocking {
        val newsItems = listOf(
            NewsItems(
                source = com.example.lloydsassignment.data.remote.model.Source(id = "bbc-news", name = "BBC News"),
                author = "John Doe",
                title = "Sample News",
                description = "Sample Description",
                url = "https://example.com/news1",
                urlToImage = "https://example.com/image1.jpg",
                publishedAt = "2023-10-01T12:00:00Z",
                content = "Full content of the news article."
            )
        )
        val newsResponse = NewsResponse(articles = newsItems, totalResults = 1)
        `when`(apiService.getNews(sources = source, page = 1)).thenReturn(newsResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = newsItems,
            prevKey = null,
            nextKey = null
        )

        val actualResult = newsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `load returns Page when on empty response`() = runBlocking {
        val newsResponse = NewsResponse(articles = emptyList(), totalResults = 0)
        `when`(apiService.getNews(sources = source, page = 1)).thenReturn(newsResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )

        val actualResult = newsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `load returns Error when on exception`() = runBlocking {
        val exception = RuntimeException("Test exception")
        `when`(apiService.getNews(sources = source, page = 1)).thenThrow(exception)

        val actualResult = newsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertTrue(actualResult is PagingSource.LoadResult.Error)
        assertEquals(exception, (actualResult as PagingSource.LoadResult.Error).throwable)
    }

    @Test
    fun `getRefreshKey returns correct key`() {
        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(
                        NewsItems(
                            source = com.example.lloydsassignment.data.remote.model.Source(id = "bbc-news", name = "BBC News"),
                            author = "John Doe",
                            title = "Sample News",
                            description = "Sample Description",
                            url = "https://example.com/news1",
                            urlToImage = "https://example.com/image1.jpg",
                            publishedAt = "2023-10-01T12:00:00Z",
                            content = "Full content of the news article."
                        )
                    ),
                    prevKey = null,
                    nextKey = 2
                )
            ),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val refreshKey = newsPagingSource.getRefreshKey(pagingState)
        assertEquals(1, refreshKey)
    }
}