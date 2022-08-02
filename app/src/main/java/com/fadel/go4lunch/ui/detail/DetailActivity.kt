package com.fadel.go4lunch.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.fadel.go4lunch.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var vb: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(vb.root)
        supportFragmentManager.commit {
            replace(
                vb.root.id, DetailFragment.newInstance(
                    intent.getStringExtra(KEY_PLACE_ID)!!
                )
            )
        }
    }

    companion object {

        private const val KEY_PLACE_ID = "KEY_PLACE_ID"

        fun navigate(context: Context, placeId: String): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_PLACE_ID, placeId)
            }
    }


}