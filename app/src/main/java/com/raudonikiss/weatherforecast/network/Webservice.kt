package com.raudonikiss.weatherforecast.network

import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {

    @GET("weather")
    fun getWeatherData( @Query("city") city : String, @Query("country") country : String )

    @GET("weather")
    fun getWeatherData( @Query("cityId") cityId : Long )
}