package com.raudonikiss.weatherforecast.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var country: String,
    var countryCode: String
)