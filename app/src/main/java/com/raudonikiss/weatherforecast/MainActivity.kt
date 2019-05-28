package com.raudonikiss.weatherforecast

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raudonikiss.weatherforecast.base.dependencyRetriever
import com.raudonikiss.weatherforecast.contracts.MainContract
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.fragments.CitiesFragment
import com.raudonikiss.weatherforecast.fragments.SettingsFragment
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.presenters.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), MainContract.View {

    //UI
    private lateinit var mBottomNavigation : BottomNavigationView
    //Variables
    private lateinit var mPresenter : MainContract.Presenter
    private lateinit var mWebservice : Webservice
    private lateinit var mDatabase : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNavigation = bottom_navigation
        mPresenter = MainPresenter(this)

        mWebservice = dependencyRetriever.webservice
        mDatabase = dependencyRetriever.db

        setupNavigation()
        if(savedInstanceState == null){ //Default page
            mBottomNavigation.selectedItemId = R.id.action_cities
        }

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

    private fun setupNavigation(){
        mBottomNavigation.setOnNavigationItemSelectedListener{
            mPresenter.onNavigationItemClicked(it.itemId)
            true
        }
    }

    private fun changeFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.host_fragment, fragment)
        transaction.commit()
    }

    override fun navigateToCities() {
        changeFragment(CitiesFragment())
    }

    override fun navigateToSettings() {
        changeFragment(SettingsFragment())
    }

    override fun changeTitle(title: String) {
        this.title = title
    }
}
