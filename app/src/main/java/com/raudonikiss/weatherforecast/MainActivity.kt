package com.raudonikiss.weatherforecast

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raudonikiss.weatherforecast.base.dependencyRetriever
import com.raudonikiss.weatherforecast.contracts.MainContract
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.presenters.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

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

        thread{
            Log.v("tag", "forecast:" + mDatabase.weatherForecastDao().getWeatherForecast("Kaunas", "LT"))
        }




        /*val call = mWebservice.getWeatherData("Kaunas,lt")
        call.enqueue(object : Callback<WeatherForecastResponseBody> {
            override fun onFailure(call: Call<WeatherForecastResponseBody>, t: Throwable) {
                Toast.makeText(this@MainActivity, "FAIL", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<WeatherForecastResponseBody>, response: Response<WeatherForecastResponseBody>) {
                Toast.makeText(this@MainActivity, "SUCCESS", Toast.LENGTH_LONG).show()
                val body = response.body()
                if(body != null){
                    thread{
                        val weatherForecast = body.toWeatherForecast()
                        mDatabase.weatherForecastDao().insertWeatherForecast(weatherForecast)
                    }
                }
                Log.v("tag", "response body:"+ response.body())
            }
        })*/

    }

    private fun setupNavigation() {
        mBottomNavigation = bottom_navigation
        mNavController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(mBottomNavigation, mNavController)
        // Set up the ActionBar to stay in sync with the NavController
        /*supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)*/
        NavigationUI.setupActionBarWithNavController(this, mNavController)
    }
}
