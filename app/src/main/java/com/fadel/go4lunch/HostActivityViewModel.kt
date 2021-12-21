package com.fadel.go4lunch

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HostActivityViewModel @Inject constructor(
    private val permissionRepository: PermissionRepository
){

    fun onResume() {

    }
}