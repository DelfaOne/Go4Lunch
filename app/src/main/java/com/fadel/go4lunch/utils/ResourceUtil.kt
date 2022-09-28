package com.fadel.go4lunch.utils

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.DrawableRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getResourceUri(@DrawableRes resourceId: Int): Uri? = try {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.resources.getResourcePackageName(resourceId))
            .appendPath(context.resources.getResourceTypeName(resourceId))
            .appendPath(context.resources.getResourceEntryName(resourceId))
            .build()
    } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
        null
    }
}