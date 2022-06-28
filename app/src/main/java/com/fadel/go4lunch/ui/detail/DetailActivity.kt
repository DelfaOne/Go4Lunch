package com.fadel.go4lunch.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val vm by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.getViewStateLiveData().observe(this) {
        }
    }

    companion object {
        const val KEY_PLACE_ID = "KEY_PLACE_ID"

        fun navigate(context: Context, placeId: String): Intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(KEY_PLACE_ID, placeId)
        }
    }
}