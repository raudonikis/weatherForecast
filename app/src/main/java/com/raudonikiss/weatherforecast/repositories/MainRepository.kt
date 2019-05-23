package com.raudonikiss.weatherforecast.repositories

import com.raudonikiss.weatherforecast.data.CityDao
import com.raudonikiss.weatherforecast.data.WeatherForecastDao
import com.raudonikiss.weatherforecast.network.Webservice

class MainRepository(
    private val webservice : Webservice,
    private val cityDao : CityDao,
    private val weatherDao : WeatherForecastDao
) {

    fun getCurrentWeatherForecast(cityId : Long){

    }

    fun addWeatherForecast(cityId : Long){
    }
}