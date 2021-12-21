package com.fadel.go4lunch

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HostActivityViewModel @Inject constructor(
    private val context: Context,
    private val permissionRepository: PermissionRepository
){

    fun onResume() {
        val grantedPermissions = getPermissionList().filter { permission ->
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        permissionRepository.updatePermission(grantedPermissions)
    }

    private fun getPermissionList() = listOf(PERMISSION1, PERMISSION2)
}