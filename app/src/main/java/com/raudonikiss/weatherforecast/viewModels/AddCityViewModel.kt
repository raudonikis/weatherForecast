package com.raudonikiss.weatherforecast.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.error_handling.ResponseStatus
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.network.response.WeatherForecastResponseBody
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val call = webservice.getWeatherData("$cityName,$countryId")
        call.enqueue(object : Callback<WeatherForecastResponseBody> {
            override fun onFailure(call: Call<WeatherForecastResponseBody>, t: Throwable) {
                status.value = ResponseStatus.RESPONSE_ERROR
            }

            override fun onResponse(
                call: Call<WeatherForecastResponseBody>,
                response: Response<WeatherForecastResponseBody>
            ) {
                if (response.errorBody() != null) {
                    status.value = ResponseStatus.NO_CITY_FOUND
                } else {
                    val body = response.body()
                    if (body != null) {

                        Log.d("response", body.toString())

                        val weatherForecast = body.toWeatherForecast()
                        disposable.add(
                            Completable.fromAction {
                                database.weatherForecastDao().insertWeatherForecast(weatherForecast)
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    status.value = ResponseStatus.SUCCESS
                                },{
                                    status.value = ResponseStatus.RESPONSE_ERROR
                                })
                        )
                    }
                }
            }
        })
    }
}