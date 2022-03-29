package com.fadel.go4lunch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.fadel.go4lunch.R
import com.fadel.go4lunch.databinding.MainActivityBinding
import com.fadel.go4lunch.ui.list.ListFragment
import com.fadel.go4lunch.ui.map.MapFragment
import com.fadel.go4lunch.ui.workmates.WorkmatesFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val vm by viewModels<MainViewModel>()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()

        val toggle = ActionBarDrawerToggle(
            this,
            binding.mainDrawerLayout,
            binding.mainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.mainDrawerNavigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("Nino", "onNavigationItemSelected() called with: item = $item")
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        return true
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
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }

    private fun setupView() {
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, MapFragment()).commit()

        binding.mainBottomNavigationView.setOnItemSelectedListener  { item ->
            switchScreen(item.itemId)
            true
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