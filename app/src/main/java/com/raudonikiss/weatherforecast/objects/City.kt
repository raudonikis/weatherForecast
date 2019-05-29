package com.raudonikiss.weatherforecast.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    /*val lon: Long,
    val lat: Long,*/
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val country: String
)