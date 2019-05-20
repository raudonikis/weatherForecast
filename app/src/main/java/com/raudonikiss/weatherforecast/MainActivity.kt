package com.raudonikiss.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raudonikiss.weatherforecast.contracts.MainContract
import com.raudonikiss.weatherforecast.fragments.CitiesFragment
import com.raudonikiss.weatherforecast.fragments.SettingsFragment
import com.raudonikiss.weatherforecast.presenters.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    //UI
    private lateinit var mBottomNavigation : BottomNavigationView
    //Variables
    private lateinit var mPresenter : MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNavigation = bottom_navigation
        mPresenter = MainPresenter(this)

        setupNavigation()

        if(savedInstanceState == null){ //Default page
            mBottomNavigation.selectedItemId = R.id.action_cities
        }
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
