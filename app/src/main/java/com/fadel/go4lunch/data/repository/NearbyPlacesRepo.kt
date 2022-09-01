package com.fadel.go4lunch.data.repository

import androidx.collection.LruCache
import com.fadel.go4lunch.data.datasource.NearbyPlacesWebDataSource
import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponse
import com.fadel.go4lunch.data.pojo.nearbyplace.NearbyResponses
import com.fadel.go4lunch.data.pojo.nearbyplace.details.DetailResponse
import com.fadel.go4lunch.data.repository.location.LocationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NearbyPlacesRepo @Inject constructor(
    private val nearbyPlaceDataSource: NearbyPlacesWebDataSource,
    private val locationUtils: LocationUtils
) {

    private val nearbyPlaceCache = LruCache<NearbyPlaceKey, NearbyResponses>(20)
    private val placeDetailCache = LruCache<String, DetailResponse>(400)

    suspend fun getNearbyResults(
        location: LocationEntity,
        radius: String,
        type: String,
        apiKey: String
    ): List<NearbyResponse>? {
        val result = try {
            val key = NearbyPlaceKey(location, radius, type)

            nearbyPlaceCache[key] ?: nearbyPlaceDataSource.getNearbyPlaces(
                location = "${location.lat},${location.long}",
                radius = radius,
                type = type,
                key = apiKey
            ).also { nearbyResponses ->
                nearbyPlaceCache.put(key, nearbyResponses)
            }
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
        placeDetailCache[placeId] ?: nearbyPlaceDataSource.getDetailPlaces(placeId, key)
            .also { response ->
                placeDetailCache.put(placeId, response)
            }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    //Test nominalcase / exception -> null / result is null / result with null


    private inner class NearbyPlaceKey(
        private val location: LocationEntity,
        private val radius: String,
        private val type: String,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NearbyPlaceKey

            if (radius != other.radius) return false
            if (type != other.type) return false

            if (locationUtils.distanceBetween(this.location, other.location) > 50) return false

            return true
        }

        override fun hashCode(): Int {
            var result = location.hashCode()
            result = 31 * result + radius.hashCode()
            result = 31 * result + type.hashCode()
            return result
        }
    }
}