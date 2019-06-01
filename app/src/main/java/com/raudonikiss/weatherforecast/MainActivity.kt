package com.raudonikiss.weatherforecast

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raudonikiss.weatherforecast.viewModels.CitiesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    //UI
    private lateinit var mBottomNavigation: BottomNavigationView
    private lateinit var mNavController: NavController
    //Variables
    private val viewModel: CitiesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
        Places.initialize(this, BuildConfig.PlacesApiKey)
        setUpObservers()
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

    private fun setUpObservers() {
        viewModel.getAllCities().observe(this,
            Observer { t ->
                Log.v(TAG, t.toString())
                viewModel.updateAllForecasts()
            })
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

    companion object {
        const val TAG = "MainActivity"
    }
}
