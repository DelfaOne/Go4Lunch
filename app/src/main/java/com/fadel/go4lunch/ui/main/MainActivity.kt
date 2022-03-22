package com.fadel.go4lunch.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fadel.go4lunch.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainViewModel>()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onResume() {
        super.onResume()
        vm.onResume()
    }

    /* private fun initializeUI() {
         binding.logoutButton.setOnClickListener {
             logout()
         }
     }*/

    /*private fun logout() {
        FirebaseAuth.getInstance().signOut();
    }*/

}