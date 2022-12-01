package com.fadel.go4lunch.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fadel.go4lunch.R
import com.fadel.go4lunch.databinding.MainActivityBinding
import com.fadel.go4lunch.databinding.MainNavigationHeaderBinding
import com.fadel.go4lunch.ui.list.ListFragment
import com.fadel.go4lunch.ui.map.MapFragment
import com.fadel.go4lunch.ui.workmates.WorkmatesFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val vm by viewModels<MainViewModel>()
    private lateinit var binding: MainActivityBinding
    private lateinit var headerBinding: MainNavigationHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView(savedInstanceState)
        setupObservers()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.apply {
            maxWidth = Int.MAX_VALUE // TODO FADEL Necessary ?
            searchView.setBackgroundColor(Color.TRANSPARENT) // TODO FADEL Necessary ?
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        vm.onEditSearchChange(newText)
                    }
                    return false
                }

            })
        }
        return true
    }

    override fun onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
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
        supportFragmentManager.commit {
            replace(R.id.main_fragment_container, fragment)
        }
    }

    private fun setupView(savedInstanceState: Bundle?) {
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        headerBinding =
            MainNavigationHeaderBinding.bind(binding.mainDrawerNavigationView.getHeaderView(0))
        setSupportActionBar(binding.mainToolbar)

        if (savedInstanceState == null) {
            navigateTo(MapFragment())
        }

        binding.mainBottomNavigationView.setOnItemSelectedListener { item ->
            switchScreen(item.itemId)
            true
        }
    }

    private fun setupObservers() {
        vm.userInfo.observe(this) {
            with(headerBinding) {
                userName.text = it.name
                userEmail.text = it.email
                avatar.setImageURI(it.uriPhotoUrl)
            }
        }
        vm.searchResults.observe(this) {
            Log.d("Nino", "MainActivity.searchResults.observe() called with $it")
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

}