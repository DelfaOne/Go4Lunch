package com.fadel.go4lunch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionRepository @Inject constructor(){

    private val permissionsListMutableStateFlow = MutableStateFlow(mutableListOf<String>())
    val permissionListFlow: Flow<List<String>> = permissionsListMutableStateFlow.asStateFlow()

    fun updatePermission(permissionList: List<String>) {
        permissionsListMutableStateFlow.value = permissionsListMutableStateFlow.value.apply {
            clear()
            addAll(permissionList)
        }
    }
}
