package com.raudonikiss.weatherforecast.network.response

data class Main(
    val humidity: Double,
    val pressure: Double,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)