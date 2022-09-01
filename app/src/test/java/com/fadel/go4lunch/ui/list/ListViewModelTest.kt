package com.fadel.go4lunch.ui.list

import com.fadel.go4lunch.data.repository.location.LocationRepository
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.utils.CoroutineTestRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {

    private lateinit var listViewModel: ListViewModel

    private val nearbyPlacesRepo: NearbyPlacesRepo = mockk()

    private val locationRepository: LocationRepository = mockk()

    @Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        listViewModel = ListViewModel(
            nearbyPlacesRepo,
            locationRepository,
            coroutineTestRule.testDispatcherProvider
        )
    }

    @Test
    fun toto() = coroutineTestRule.scope.runTest {

    }
}