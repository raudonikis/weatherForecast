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
import com.raudonikiss.weatherforecast.base.dependencyRetriever
import com.raudonikiss.weatherforecast.contracts.MainContract
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.presenters.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    //UI
    private lateinit var mBottomNavigation : BottomNavigationView
    private lateinit var mNavController : NavController
    //Variables
    private lateinit var mPresenter : MainContract.Presenter
    private lateinit var mWebservice : Webservice
    private lateinit var mDatabase : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)

        mWebservice = dependencyRetriever.webservice
        mDatabase = dependencyRetriever.db

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
            R.id.settingsFragment)
            .build()
        // Set up the ActionBar to stay in sync with the NavController
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
