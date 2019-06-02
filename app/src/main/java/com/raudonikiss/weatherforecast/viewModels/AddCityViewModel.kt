package com.raudonikiss.weatherforecast.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.error_handling.ResponseStatus
import com.raudonikiss.weatherforecast.network.Webservice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.concurrent.thread

class AddCityViewModel(private val database: AppDatabase, private val webservice: Webservice) : ViewModel() {

    private var cityName = ""
    private var countryId = ""

    val status = MutableLiveData<ResponseStatus>()

    private val disposable = CompositeDisposable()

    fun setCityData(cityName: String?, countryId: String?) {
        if (cityName != null && countryId != null) {
            this.cityName = cityName
            this.countryId = countryId
        } else {
            status.value = ResponseStatus.SEARCH_ERROR
        }
    }

    fun clearCityData() {
        cityName = ""
        countryId = ""
    }

    fun dispose() {
        disposable.dispose()
    }

    fun saveCity() {
        if (!isCityDataEmpty()) {
//            if(database.weatherForecastDao().getWeatherForecast(cityName, countryId) == null)
            updateForecast()
        } else {
            status.value = ResponseStatus.EMPTY
        }
    }

    private fun isCityDataEmpty(): Boolean {
        return cityName.isBlank() || countryId.isBlank()
    }

    private fun updateForecast() {
        status.value = ResponseStatus.LOADING
        disposable.add(webservice.getWeatherData("$cityName,$countryId")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    status.value = ResponseStatus.SUCCESS
                    Log.d("response", it.toString())
                    val weatherForecast = it.toWeatherForecast()
                    thread {
                        database.weatherForecastDao().insertWeatherForecast(weatherForecast)
                    }
                } else {
                    status.value = ResponseStatus.NO_CITY_FOUND
                }

            }, {
                status.value = ResponseStatus.RESPONSE_ERROR
            })
        )
    }
}