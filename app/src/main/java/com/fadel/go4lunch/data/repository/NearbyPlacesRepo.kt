package com.fadel.go4lunch.data.repository

import com.fadel.go4lunch.data.datasource.NearbyPlacesDataSource
import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponse
import com.fadel.go4lunch.data.pojo.nearbyplace.details.DetailResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NearbyPlacesRepo @Inject constructor(
    private val nearbyPlaceDataSource: NearbyPlacesDataSource

) {

    suspend fun getNearbyResults(
        location: String,
        radius: String,
        type: String,
        key: String
    ): List<NearbyResponse>? {
        val result = try {
            nearbyPlaceDataSource.getNearbyPlaces(location, radius, type, key)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return result?.results?.filterNotNull()
    }

    suspend fun getDetailResult(
        placeId: String,
        key: String
    ): DetailResponse? = try {
        nearbyPlaceDataSource.getDetailPlaces(placeId, key)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    //Test nominalcase / exception -> null / result is null / result with null
}