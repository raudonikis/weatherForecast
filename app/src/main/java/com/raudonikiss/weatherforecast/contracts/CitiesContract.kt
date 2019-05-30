package com.raudonikiss.weatherforecast.contracts

import com.raudonikiss.weatherforecast.objects.WeatherForecast

interface CitiesContract {

    interface View{
        fun navigateToAddCity()
        fun updateList(list: List<WeatherForecast>)
    }
    interface Presenter{
        fun onFloatingButtonClicked()
        fun onDetach()
    }
}