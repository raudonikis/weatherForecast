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

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun saveCity() {
        if (!isCityDataEmpty()) {
            disposable.add(database.weatherForecastDao().countItems(cityName, countryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    if (it > 0) {
                        status.value = ResponseStatus.DUPLICATE
                    } else {
                        updateForecast()
                    }
                }
                .subscribe())
        } else {
            status.value = ResponseStatus.EMPTY
        }
    }

    private fun isCityDataEmpty(): Boolean {
        return cityName.isBlank() || countryId.isBlank()
    }

    private fun updateForecast() {
        status.value = ResponseStatus.LOADING
        disposable.add(
            webservice.getWeatherData("$cityName,$countryId")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    status.value = ResponseStatus.ADD_SUCCESS
                    Log.d("response", it.toString())
                    val weatherForecast = it.toWeatherForecast()
                    thread {
                        database.weatherForecastDao().insertWeatherForecast(weatherForecast)
                    }
                }, {
                    status.value = ResponseStatus.NO_CITY_FOUND
                })
        )
    }
}