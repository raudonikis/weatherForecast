package com.raudonikiss.weatherforecast.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.City
import io.reactivex.Flowable

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(item: City): Long

    @Query("DELETE FROM cities")
    fun deleteAllCities()

    @Query("SELECT * FROM cities")
    fun getAllCities(): Flowable<List<City>>

    @Query("SELECT * FROM cities")
    fun getCities(): LiveData<List<City>>
}