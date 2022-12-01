package com.fadel.go4lunch.domain.autocomplete

import android.location.Location
import com.fadel.go4lunch.data.repository.AutocompleteRepository
import com.fadel.go4lunch.data.repository.location.LocationRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAutocompleteUseCase @Inject constructor(
    private val autocompleteRepository: AutocompleteRepository,
    private val locationRepository: LocationRepository,
) {
    suspend fun invoke(userInput: String): List<AutocompleteEntity>? {
        val location = locationRepository.getLocationFlow().first()
        return autocompleteRepository.getAutocomplete(userInput, "$location")

    }

    companion object {
        val defaultLocation = Location("default").apply {
            latitude = 50.63676868035896
            longitude = 3.0700331845533104
        }
    }
}