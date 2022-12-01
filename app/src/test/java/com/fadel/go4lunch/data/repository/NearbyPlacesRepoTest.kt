package com.fadel.go4lunch.data.repository

import com.fadel.go4lunch.data.datasource.NearbyPlacesWebDataSource
import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponse
import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponses
import com.fadel.go4lunch.data.repository.location.LocationEntity
import com.fadel.go4lunch.data.repository.location.LocationUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NearbyPlacesRepoTest {

    private val nearbyPlacesWebDataSource: NearbyPlacesWebDataSource = mockk()
    private val locationUtils: LocationUtils = mockk()

    private lateinit var nearbyPlacesRepo: NearbyPlacesRepo

    @Before
    fun setup() {
        nearbyPlacesRepo = NearbyPlacesRepo(nearbyPlacesWebDataSource, locationUtils)
    }

    @Test
    fun `nominal case`() = runTest {
        //GIVEN
        val location = LocationEntity(0.0, 0.0)
        val radius = "radius"
        val type = "type"
        val key = "key"

        val nearbyResponses = listOf<NearbyResponse>(
            mockk(),
            mockk(),
            mockk(),
        )

        coEvery { nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key) } returns NearbyResponses(nearbyResponses)

        //WHEN
        val result = nearbyPlacesRepo.getNearbyResults(location, radius, type, key)

        //THEN
        assertEquals(nearbyResponses, result)
        coVerify(exactly = 1) {
            nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key)
        }
        confirmVerified(nearbyPlacesWebDataSource)
    }

    @Test
    fun `edge case - some null children`() = runTest {
        //GIVEN
        val location = LocationEntity(0.0, 0.0)
        val radius = "radius"
        val type = "type"
        val key = "key"

        val mock1 = mockk<NearbyResponse>()
        val mock2 = mockk<NearbyResponse>()

        val nearbyResponses = listOf(
            mock1,
            null,
            mock2,
        )

        coEvery { nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key) } returns NearbyResponses(nearbyResponses)

        //WHEN
        val result = nearbyPlacesRepo.getNearbyResults(location, radius, type, key)

        //THEN
        assertEquals(
            listOf(
                mock1,
                mock2
            ),
            result
        )
        coVerify(exactly = 1) {
            nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key)
        }
        confirmVerified(nearbyPlacesWebDataSource)
    }


    @Test
    fun `error case - all children are null`() = runTest {
        //GIVEN
        val location = LocationEntity(0.0, 0.0)
        val radius = "radius"
        val type = "type"
        val key = "key"

        val nearbyResponses = listOf(null)

        coEvery { nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key) } returns NearbyResponses(nearbyResponses)

        //WHEN
        val result = nearbyPlacesRepo.getNearbyResults(location, radius, type, key)

        //THEN
        assertNotNull(result)
        assertTrue(result!!.isEmpty())
        coVerify(exactly = 1) {
            nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key)
        }
        confirmVerified(nearbyPlacesWebDataSource)
    }

    @Test
    fun `error case - results are null`() = runTest {
        //GIVEN
        val location = LocationEntity(0.0, 0.0)
        val radius = "radius"
        val type = "type"
        val key = "key"

        coEvery { nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key) } returns NearbyResponses(null)

        //WHEN
        val result = nearbyPlacesRepo.getNearbyResults(location, radius, type, key)

        //THEN
        assertNull(result)
        coVerify(exactly = 1) {
            nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key)
        }
        confirmVerified(nearbyPlacesWebDataSource)
    }

    @Test
    fun `error case - call raises exception`() = runTest {
        //GIVEN
        val location = LocationEntity(0.0, 0.0)
        val radius = "radius"
        val type = "type"
        val key = "key"

        coEvery { nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key) } throws Exception()

        //WHEN
        val result = nearbyPlacesRepo.getNearbyResults(location, radius, type, key)

        //THEN
        assertNull(result)
        coVerify(exactly = 1) {
            nearbyPlacesWebDataSource.getNearbyPlaces("0.0,0.0", radius, type, key)
        }
        confirmVerified(nearbyPlacesWebDataSource)
    }

}