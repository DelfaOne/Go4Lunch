package com.fadel.go4lunch.domain.autocomplete

import com.fadel.go4lunch.data.repository.AutocompleteRepository
import com.fadel.go4lunch.data.repository.location.LocationRepository
import com.fadel.go4lunch.domain.location.GetDefaultLocationUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class GetAutocompleteUseCase @Inject constructor(
    private val getDefaultLocationUseCase: GetDefaultLocationUseCase,
    private val autocompleteRepository: AutocompleteRepository,
    private val locationRepository: LocationRepository,
) {
    suspend fun invoke(userInput: String): List<AutocompleteEntity> {
        val location = withTimeoutOrNull(2.seconds) {
            locationRepository.getLocationFlow().first()
        } ?: getDefaultLocationUseCase.invoke()

        return autocompleteRepository.getAutocomplete(userInput, "$location") ?: emptyList()
    }
}