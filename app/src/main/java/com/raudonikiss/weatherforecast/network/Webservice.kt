package com.raudonikiss.weatherforecast.network

import com.raudonikiss.weatherforecast.network.response.WeatherForecastResponseBody
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {

    @GET("weather")
    fun getWeatherData( @Query("q") city_and_country : String) : Single<WeatherForecastResponseBody>// for example Kaunas,lt
}