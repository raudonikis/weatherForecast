package com.raudonikiss.weatherforecast.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.error_handling.ResponseStatus
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.concurrent.thread

class CitiesViewModel(private val mDatabase: AppDatabase, private val mWebservice: Webservice) : ViewModel() {

    private val weatherForecastList: LiveData<List<WeatherForecast>> by lazy {
        mDatabase.weatherForecastDao().getWeatherForecasts()
    }

    private val disposable = CompositeDisposable()
    val status = MutableLiveData<ResponseStatus>()

    fun getAllForecasts(): LiveData<List<WeatherForecast>> {
        return weatherForecastList
    }

    fun updateAllForecasts() {
        if(weatherForecastList.value?.size == 0) status.value = ResponseStatus.NONE
        else{
            weatherForecastList.value?.forEach {
                updateForecast(it)
            }
        }
    }

    fun removeListItem(position: Int) {
        val forecast = weatherForecastList.value?.get(position)
        if (forecast != null) {
            thread {
                mDatabase.weatherForecastDao().deleteForecast(forecast)
            }
        }
    }

    private fun updateForecast(forecast: WeatherForecast) {
        status.value = ResponseStatus.LOADING
        disposable.add(mWebservice.getWeatherData(forecast.city_name + "," + forecast.country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it != null){
                    status.value = ResponseStatus.SUCCESS
                    Log.d("response", it.toString())
                    val weatherForecast = it.toWeatherForecast()
                    thread{
                        mDatabase.weatherForecastDao().insertWeatherForecast(weatherForecast)
                    }
                }else{
                    status.value = ResponseStatus.NO_CITY_FOUND
                }

            },{
                status.value = ResponseStatus.RESPONSE_ERROR
            }))
    }

    fun dispose() {
        disposable.dispose()
    }
}