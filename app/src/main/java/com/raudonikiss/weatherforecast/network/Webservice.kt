package com.raudonikiss.weatherforecast.network

import com.raudonikiss.weatherforecast.network.response.WeatherForecastResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {

    @GET("weather")
    fun getWeatherData( @Query("id") cityId : Long) : Call<WeatherForecastResponseBody>

    @GET("weather")
    fun getWeatherData( @Query("q") city_and_country : String) : Call<WeatherForecastResponseBody>// for example Kaunas,lt
}