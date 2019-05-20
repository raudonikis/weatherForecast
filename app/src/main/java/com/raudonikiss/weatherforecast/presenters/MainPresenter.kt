package com.raudonikiss.weatherforecast.presenters

import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.contracts.MainContract

class MainPresenter(private val view : MainContract.View) : MainContract.Presenter {

    override fun onNavigationItemClicked(id: Int) {
        when(id){
            R.id.action_cities -> {
                view.navigateToCities()
                view.changeTitle("Cities")
            }
            R.id.action_settings -> {
                view.navigateToSettings()
                view.changeTitle("Settings")
            }
        }
    }
}