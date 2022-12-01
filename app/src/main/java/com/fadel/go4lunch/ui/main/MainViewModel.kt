package com.fadel.go4lunch.ui.main

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.fadel.go4lunch.data.PermissionRepository
import com.fadel.go4lunch.data.usecase.GetLoggedUserUseCase
import com.fadel.go4lunch.domain.autocomplete.GetAutocompleteUseCase
import com.fadel.go4lunch.ui.main.model.MainAutocompleteViewState
import com.fadel.go4lunch.ui.main.model.User
import com.fadel.go4lunch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val context: Application,
    private val permissionRepository: PermissionRepository,
    getLoggedUserUseCase: GetLoggedUserUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val getAutocompleteUseCase: GetAutocompleteUseCase,
) : ViewModel() {

    val userInfo: LiveData<User> =
        getLoggedUserUseCase.invoke()
            .map {
                User(
                    it?.displayName ?: "Error get name",
                    it?.email ?: "Error get mail",
                    it?.photoUrl
                )
            }
            .distinctUntilChanged()
            .flowOn(dispatcherProvider.ioDispatcher)
            .asLiveData()

    private val userSearch = MutableLiveData<String>()
    val searchResults: LiveData<List<MainAutocompleteViewState>> =
        userSearch.switchMap { searchText ->
            Log.d(
                "Nino",
                "MainViewModel.userSearch.switchMap() called with: searchText = $searchText"
            )
            liveData(dispatcherProvider.ioDispatcher) {
                emit(
                    getAutocompleteUseCase.invoke(searchText).map { entity ->
                        MainAutocompleteViewState(
                            placeId = entity.placeId,
                        )
                    }.also {
                        Log.d("Nino", "MainViewModel.map.emitted() $it")
                    }
                )
            }
        }

    fun onResume() {
        val grantedPermissions = getPermissionList().filter { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        permissionRepository.updatePermission(grantedPermissions)
    }

    private fun getPermissionList() =
        listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    fun onEditSearchChange(searchText: String) {
        Log.d("Nino", "MainViewModel.onEditSearchChange() called with: searchText = $searchText")
        if (searchText.isNotBlank()) {
            userSearch.value = searchText
        }
    }
}