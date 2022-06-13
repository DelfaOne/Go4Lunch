package com.fadel.go4lunch.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionRepository @Inject constructor() {

    private val permissionsListMutableStateFlow = MutableSharedFlow<List<String>>(replay = 1).apply {
        tryEmit(emptyList())
    }
    val permissionListFlow: Flow<List<String>> = permissionsListMutableStateFlow.asSharedFlow()

    fun updatePermission(permissionList: List<String>) {
        permissionsListMutableStateFlow.tryEmit(permissionList)
    }
}
