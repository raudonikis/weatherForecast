package com.raudonikiss.weatherforecast.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.network.response.WeatherForecastResponseBody
import com.raudonikiss.weatherforecast.objects.City
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesViewModel(private val mDatabase: AppDatabase, private val mWebservice: Webservice) : ViewModel() {

    private val cityList: LiveData<List<City>> by lazy {
        mDatabase.cityDao().getCities()
    }

    private val weatherForecastList: LiveData<List<WeatherForecast>> by lazy {
        mDatabase.weatherForecastDao().getWeatherForecasts()
    }

    private val disposable = CompositeDisposable()

    fun getAllCities(): LiveData<List<City>> {
        return cityList
    }

    fun getAllForecasts(): LiveData<List<WeatherForecast>> {
        return weatherForecastList
    }

    fun updateAllForecasts() {
        cityList.value?.forEach {
            updateForecast(it)
        }
    }

    fun removeListItem(position: Int) {
        val city = cityList.value?.get(position)
        if (city != null) {
            disposable.add(
                Completable.fromAction {
                    mDatabase.weatherForecastDao().removeForecast(city.name, city.countryCode)
                    mDatabase.cityDao().removeCity(city)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )

        }
    }

    private fun updateForecast(city: City) {
        val call = mWebservice.getWeatherData(city.name + "," + city.countryCode)
        call.enqueue(object : Callback<WeatherForecastResponseBody> {
            override fun onFailure(call: Call<WeatherForecastResponseBody>, t: Throwable) {
                Log.v("CitiesFragment", "Failure")
            }

            override fun onResponse(
                call: Call<WeatherForecastResponseBody>,
                response: Response<WeatherForecastResponseBody>
            ) {
                val body = response.body()
                if (body != null) {

                    Log.d("response", body.toString())

                    val weatherForecast = body.toWeatherForecast()
                    disposable.add(
                        Completable.fromAction {
                            mDatabase.weatherForecastDao().insertWeatherForecast(weatherForecast)
                        }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    )
                }
            }
        })
    }

    fun dispose() {
        disposable.dispose()
    }
}