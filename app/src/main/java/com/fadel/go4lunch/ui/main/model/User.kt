package com.fadel.go4lunch.ui.main.model

import android.net.Uri

data class User(
    val id: String,
    val name: String,
    val email: String?,
    val uriPhotoUrl: Uri?
)
