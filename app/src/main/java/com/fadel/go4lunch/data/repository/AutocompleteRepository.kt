package com.fadel.go4lunch.data.repository

import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.data.datasource.AutocompleteWebDataSource
import com.fadel.go4lunch.domain.autocomplete.AutocompleteEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AutocompleteRepository @Inject constructor(
    private val autocompleteWebDataSource: AutocompleteWebDataSource,
) {
    companion object {
        private const val AVAILABLE_TYPES = "restaurant"
        private const val RADIUS = "2000"
    }

    suspend fun getAutocomplete(
        input: String,
        location: String?,
    ): List<AutocompleteEntity>? = try {
        val response = autocompleteWebDataSource.getAutocomplete(
            input = input,
            types = AVAILABLE_TYPES,
            location = location,
            radius = RADIUS,
            key = BuildConfig.PLACES_KEY
        )

        response.predictions.mapNotNull {
            AutocompleteEntity(
                label = it.description ?: return@mapNotNull null,
                placeId = it.placeId,
            )
        }.takeIf {
            it.isNotEmpty()
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}