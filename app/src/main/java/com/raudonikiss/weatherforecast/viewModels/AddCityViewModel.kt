package com.raudonikiss.weatherforecast.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.network.response.WeatherForecastResponseBody
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCityViewModel(private val database : AppDatabase, private val webservice: Webservice) : ViewModel(){

    private var cityName = ""
    private var countryId = ""

    private val disposable = CompositeDisposable()

    fun setCityData(cityName: String?, countryId: String?) {
        if( cityName != null && countryId != null){
            this.cityName = cityName
            this.countryId = countryId
        }else{
//            view.displayError(ERROR_SEARCH)
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
        if(!isCityDataEmpty()){
            updateForecast()
        }else{
//            view.displayError(ERROR_NO_DATA)
        }
    }

    private fun isCityDataEmpty(): Boolean{
        return cityName.isBlank() || countryId.isBlank()
    }

    private fun updateForecast() {
        val call = webservice.getWeatherData("$cityName,$countryId")
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
                            database.weatherForecastDao().insertWeatherForecast(weatherForecast)
                        }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    )
                }
            }
        })
    }
}