package com.fadel.go4lunch.ui.main

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadel.go4lunch.data.PermissionRepository
import com.fadel.go4lunch.domain.usecase.GetLoggedUserUseCase
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
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val pendingTransactionCount: LiveData<User> =
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
}