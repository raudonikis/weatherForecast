package com.raudonikiss.weatherforecast.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.City

interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(item: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(all: List<City>)

    @Query("DELETE FROM City")
    fun deleteAllCities()

    @Query("SELECT * FROM City")
    fun getAllCities(): List<City>
}