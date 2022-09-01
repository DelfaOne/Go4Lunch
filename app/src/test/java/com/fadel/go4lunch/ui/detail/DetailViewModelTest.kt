package com.fadel.go4lunch.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.data.pojo.nearbyplace.details.DetailResponse
import com.fadel.go4lunch.data.pojo.nearbyplace.details.Result
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.utils.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    companion object {
        private const val PLACE_ID = "PLACE_ID"
    }

    private lateinit var detailViewModel: DetailViewModel

    private val nearbyPlacesRepo: NearbyPlacesRepo = mockk()

    private val savedStateHandle: SavedStateHandle = mockk()

    @Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        every { savedStateHandle.get<String>(DetailFragment.KEY_PLACE_ID) } returns PLACE_ID
        coEvery {
            nearbyPlacesRepo.getDetailResult(
                PLACE_ID,
                BuildConfig.GMP_KEY
            )
        } returns getDefaultDetailResponse()
        detailViewModel = DetailViewModel(
            savedStateHandle,
            coroutineTestRule.testDispatcherProvider,
            nearbyPlacesRepo
        )
    }

    @Test
    fun `should init view state`() = coroutineTestRule.runTest {
        //WHEN
        detailViewModel.getViewStateLiveData().observeForever { }
        runCurrent()
        val result = detailViewModel.getViewStateLiveData().value

        //THEN
        assertEquals(getDefaultRestaurantDetailUiModel(), result)
    }

    private fun getDefaultDetailResponse() = DetailResponse(
        result = Result(),
        htmlAttributions = null,
        status = null
    )

    private fun getDefaultRestaurantDetailUiModel() = RestaurantDetailUiModel(
        id = "21515654",
        name = "",
        address = "",
        imageUrl = "",
        rating = 3F,
        phoneNumber = "",
        website = "",
    )
}