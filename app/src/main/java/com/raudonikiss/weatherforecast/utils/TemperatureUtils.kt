package com.raudonikiss.weatherforecast.utils

import kotlin.math.roundToInt

fun getTemperature(tempUnits: String?, temp: Double): Int{
    return when(tempUnits){
        "°C" -> convertToCelsius(temp)
        "°F" -> convertToFahrenheit(temp)
        else -> temp.roundToInt()
    }
}

private fun convertToCelsius(temp: Double): Int{
    return (temp - 273.15).roundToInt()
}
private fun convertToFahrenheit(temp: Double): Int{
    return ((temp - 273.15) * 9/5 + 32).roundToInt()
}