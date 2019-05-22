package com.raudonikiss.weatherforecast.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(item: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(all: List<City>)

    @Query("DELETE FROM cities")
    fun deleteAllCities()

    @Query("SELECT * FROM cities")
    fun getAllCities(): List<City>
}