package com.example.lloydsassignment.presentation.news_list

import androidx.paging.PagingData
import androidx.paging.map
import com.example.lloydsassignment.data.remote.model.NewsItems
import com.example.lloydsassignment.data.remote.model.Source
import com.example.lloydsassignment.data.remote.repository.NewsRepository
import com.example.lloydsassignment.domain.usecases.NewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertNull
import org.mockito.ArgumentMatchers.anyList
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
//@RunWith(MockitoJUnitRunner::class)
@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [28],
    instrumentedPackages = ["org.robolectric", "org.robolectric.annotation", "org.robolectric.internal.bytecode"]
)
class NewsViewModelTest {

    private val newsRepository: NewsRepository = mock(NewsRepository::class.java)
    private lateinit var newsUseCase: NewsUseCase
    private lateinit var newsViewModel: NewsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        newsUseCase = NewsUseCase(newsRepository)
        newsViewModel = NewsViewModel(newsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getNews should update state with PagingData`() = runTest {
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
        val testPagingData = PagingData.from(testNewsItems)

        // Mock the repository to return the testPagingData
        `when`(newsRepository.getNews(listOf("bbc-news", "abc-news", "al-jazeera-english")))
            .thenReturn(flowOf(testPagingData))

        // Collect the items manually
//        val stateFlow = newsViewModel.state
        newsViewModel.getNews()

        // Collect the items manually from the flow
        val state = newsViewModel.state.first()

        // Assuming `state.newsItems` is a `Flow<PagingData<NewsItems>>`
        val collectedItems = mutableListOf<NewsItems>()

        state.newsItems?.first()?.map { item ->
            collectedItems.add(item)
        }

        assertEquals(testNewsItems, collectedItems)
    }

    @Test
    fun `getNews should update state with error when exception occurs`() = runTest {
        val exception = Exception("Something went wrong")
        `when`(newsUseCase(anyList())).thenThrow(exception)

        newsViewModel.getNews()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = newsViewModel.state.value
        assertNull(state.newsItems)
        assertEquals("Something went wrong", state.error)
    }


}
