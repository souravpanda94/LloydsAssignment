package com.example.lloydsassignment.domain.usecases

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.lloydsassignment.data.remote.model.NewsItems
import com.example.lloydsassignment.data.remote.model.Source
import com.example.lloydsassignment.data.remote.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
public class NewsUseCaseTest {

    // Mock the NewsRepository
    private val newsRepository: NewsRepository = mock(NewsRepository::class.java)

    // Create the use case instance
    private lateinit var newsUseCase: NewsUseCase

    @Before
    public fun setUp() {
        // Initialize the use case with the mocked repository
        newsUseCase = NewsUseCase(newsRepository)
    }

    @Test
    public fun `invoke should return a flow of PagingData from the repository`() = runTest {
        val testNewsItems = listOf(
            NewsItems(
                source = Source(id = "bbc-news", name = "BBC News"),
                author = "John Doe",
                title = "Lightning Strike Injures Two at Horseshoe Bend",
                description = "A lightning strike injured two people at Horseshoe Bend.",
                url = "https://example.com/news1",
                urlToImage = "https://example.com/image1.jpg",
                publishedAt = "2023-10-01T12:00:00Z",
                content = "Full content of the news article."
            )
        )
        // Prepare test data
        val testSources = listOf("bbc-news", "abc-news")
        val testPagingData = PagingData.from(testNewsItems)

        // Mock the repository to return the test PagingData
        `when`(newsRepository.getNews(testSources)).thenReturn(flowOf(testPagingData))

        // Call the use case
        val resultFlow = newsUseCase(testSources)

        // Collect the flow and verify the results
        resultFlow.test {
            assertEquals(testPagingData, awaitItem())
            awaitComplete()
        }
    }
}