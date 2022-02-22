package com.fadel.go4lunch.data.repository

import com.fadel.go4lunch.data.datasource.NearbyPlacesDataSource
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class NearbyPlacesRepoTest {

    @Mock
    lateinit var nearbyPlacesDataSource: NearbyPlacesDataSource

    lateinit var nearbyPlacesRepo: NearbyPlacesRepo

    @Before
    fun setup() {
        nearbyPlacesRepo = NearbyPlacesRepo(nearbyPlacesDataSource)
    }

    @Test
    fun `nominal case`() = runBlocking {
        //GIVEN
        val location = "location"
        val radius = "radius"
        val type = "type"
        val key = "key"

        //WHEN
        nearbyPlacesRepo.getNearbyResults(location, radius, type, key)

        //THEN
        verify {
            nearbyPlacesDataSource.
        }
    }

}