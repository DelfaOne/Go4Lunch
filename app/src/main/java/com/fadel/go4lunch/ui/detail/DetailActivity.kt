package com.fadel.go4lunch.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import com.fadel.go4lunch.ui.list.RestaurantsItemUiModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val vm by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    DetailScreenHandler(vm)
                }
            }
        )
    }

    companion object {
        const val KEY_PLACE_ID = "KEY_PLACE_ID"

        fun navigate(context: Context, placeId: String): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_PLACE_ID, placeId)
            }

    }


}