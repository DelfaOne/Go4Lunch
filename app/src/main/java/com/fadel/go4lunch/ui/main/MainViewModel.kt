package com.fadel.go4lunch.ui.main

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.fadel.go4lunch.data.PermissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val context: Application,
    private val permissionRepository: PermissionRepository
) : ViewModel() {

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