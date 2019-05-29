package com.raudonikiss.weatherforecast.contracts

interface CitiesContract {

    interface View{
        fun navigateToAddCity()
    }
    interface Presenter{
        fun onFloatingButtonClicked()
    }
}