package com.raudonikiss.weatherforecast.network.response

import com.raudonikiss.weatherforecast.objects.WeatherForecast

data class WeatherForecastResponseBody(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Long,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val visibility: Double,
    val weather: List<Weather>,
    val wind: Wind
) {

    /* fun toWeatherForecast(): WeatherForecast {
         return (WeatherForecast(
             0,
             base,
             clouds.all,
             cod,
             coord.lon,
             coord.lat,
             dt,
             id,
             main.humidity,
             main.pressure,
             main.temp,
             main.temp_max,
             main.temp_min,
             name,
             sys.country,
             sys.id,
             sys.message,
             sys.sunrise,
             sys.sunset,
             sys.type,
             visibility,
             weather[0].description,
             weather[0].icon,
             weather[0].id,
             weather[0].main,
             wind.deg,
             wind.speed
         ))
     }*/

    fun toWeatherForecast(): WeatherForecast {
        return (WeatherForecast(
            id,
            main.temp,
            main.temp_max,
            main.temp_min,
            name,
            sys.country,
            weather[0].icon
        ))
    }
}