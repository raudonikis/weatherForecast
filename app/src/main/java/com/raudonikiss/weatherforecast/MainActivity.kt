package com.raudonikiss.weatherforecast

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    //UI
    private lateinit var mBottomNavigation: BottomNavigationView
    private lateinit var mNavController: NavController
    //Variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        Places.initialize(this, BuildConfig.PlacesApiKey)
    }

    private fun setupNavigation() {
        mBottomNavigation = bottom_navigation
        mNavController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(mBottomNavigation, mNavController)
        //Define a bar configuration for main bottom navigation screens
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.citiesFragment,
            R.id.settingsFragment
        )
            .build()
        // Set up the ActionBar to stay in sync with the NavController
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
