package com.raudonikiss.weatherforecast.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raudonikiss.weatherforecast.objects.City
import io.reactivex.Flowable

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(item: City): Long

    @Delete
    fun removeCity(item: City)

    @Query("SELECT * FROM cities")
    fun getAllCities(): Flowable<List<City>>

    @Query("SELECT * FROM cities")
    fun getCities(): LiveData<List<City>>
}