package com.raudonikiss.weatherforecast.presenters

import com.raudonikiss.weatherforecast.contracts.CitiesContract

class CitiesPresenter(val view: CitiesContract.View) : CitiesContract.Presenter {

    override fun onFloatingButtonClicked() {
        view.navigateToAddCity()
    }
}