package com.fadel.go4lunch.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.fadel.go4lunch.R
import com.fadel.go4lunch.databinding.MainActivityBinding
import com.fadel.go4lunch.ui.map.MapFragment
import com.fadel.go4lunch.ui.workmates.WorkmatesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainViewModel>()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
    }

    override fun onResume() {
        super.onResume()
        vm.onResume()
    }

    private fun switchScreen(screenNumber: Int) {
        when (screenNumber) {
            R.id.navigation_map -> navigateTo(MapFragment())
            R.id.navigation_list -> navigateTo(ListFragment())
            R.id.navigation_workmates -> navigateTo(WorkmatesFragment())
        }
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment)
            .commit()
    }

    private fun setupView() {
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, MapFragment()).commit()

        binding.mainBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            switchScreen(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
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