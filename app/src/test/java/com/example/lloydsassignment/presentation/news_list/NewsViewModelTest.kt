import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.example.lloydsassignment.data.remote.model.NewsItems
import com.example.lloydsassignment.data.remote.model.Source
import com.example.lloydsassignment.domain.usecases.NewsUseCase
import com.example.lloydsassignment.presentation.news_list.NewsState
import com.example.lloydsassignment.presentation.news_list.NewsViewModel
import com.example.lloydsassignment.presentation.news_list.TestPagingDataDiffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val newsUseCase: NewsUseCase = mock(NewsUseCase::class.java)
    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(newsUseCase)
    }


    @Test
    fun `getNews should update state with PagingData`() = runTest {
        val testNewsItems = listOf(
            NewsItems(
                publishedAt = "2024-08-14T07:50:46Z",
                author = "David Brennan",
                urlToImage = "https://s.abcnews.com/images/US/police-gty-er-210120_1611194059014_hpMain_13_16x9_992.jpg?w=1600",
                description = "Two people were flown to a Utah hospital after being struck by lightning while visiting Horseshoe Bend in the Glen Canyon National Recreation Area.",
                source = Source(name = "ABC News", id = "abc-news"),
                title = "Lightning strike injures two at national park",
                url = "https://abcnews.go.com/US/lightning-strike-injures-horseshoe-bend-arizona-national-park/story?id=112823266",
                content = "Two people were flown to George Regional Hospital in Utah on Monday after being struck by lightning while visiting the Glen Canyon National Recreation Area, according to the National Park Service. I…[+1126 chars]"
            )
        )
        val testPagingData = PagingData.from(testNewsItems)

        `when`(newsUseCase(listOf("bbc-news", "abc-news", "al-jazeera-english")))
            .thenReturn(flowOf(testPagingData))

        val observer = mock(Observer::class.java) as Observer<NewsState>
        newsViewModel.state.observeForever(observer)

        newsViewModel.getNews()

        // Use TestPagingDataDiffer to collect the data
        val differ = TestPagingDataDiffer<NewsItems>()
        differ.collectFrom(testPagingData)

        val collectedItems = differ.getSnapshot().items
        assertEquals(testNewsItems, collectedItems)
        newsViewModel.state.removeObserver(observer)

    }
    @Test
    fun `getNews should update state with error when exception occurs`() = runTest {
        val exception = RuntimeException("Something went wrong")
        `when`(newsUseCase(listOf("bbc-news", "abc-news", "al-jazeera-english"))).thenThrow(
            exception
        )

        val observer = mock(Observer::class.java) as Observer<NewsState>
        newsViewModel.state.observeForever(observer)

        newsViewModel.getNews()

        val expectedState = NewsState(error = "Something went wrong")
        assertEquals(expectedState, newsViewModel.state.value)
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}


