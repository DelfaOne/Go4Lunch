package com.fadel.go4lunch.ui.workmates

import com.fadel.go4lunch.R
import com.fadel.go4lunch.ui.workmates.entity.UserEntity
import com.fadel.go4lunch.utils.ResourceUtil
import javax.inject.Inject

class WorkmateUiModelConverter @Inject constructor(
    private val resourceUtils: ResourceUtil
){

    fun convertWorkmate(userEntity: UserEntity) = WorkmatesUiModel(
        userEntity.uid,
        userEntity.userName,
        userEntity.avatarUrl
            ?: resourceUtils.getResourceUri(R.drawable.call_48px)?.toString()
            ?: ""
    )


}
