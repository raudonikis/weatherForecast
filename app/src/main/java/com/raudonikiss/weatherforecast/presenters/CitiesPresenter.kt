package com.raudonikiss.weatherforecast.presenters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raudonikiss.weatherforecast.contracts.CitiesContract
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

class CitiesPresenter(mDatabase: AppDatabase) : ViewModel() {

    private val cities: LiveData<List<City>> by lazy {
        mDatabase.cityDao().getCities()
    }

    private val weatherForecastList: LiveData<List<WeatherForecast>> by lazy{
        mDatabase.weatherForecastDao().getWeatherForecasts()
    }

        var disposable = CompositeDisposable()

        /*init {
            observeAllCities()
            observeAllWeatherData()
        }*/

        fun getAllCities(): LiveData<List<City>>{
            return cities
        }
    fun getAllForecasts(): LiveData<List<WeatherForecast>>{
        return weatherForecastList
    }

        private fun loadCities(){
        }

        /*private fun observeAllWeatherData() {
            disposable.add(
                mDatabase.weatherForecastDao().getAllWeatherForecasts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({

                        view.updateList(it)
                        Log.d("response", it.toString())

                    }, {
                        Log.d("response error", it.message)

                    })
            )
        }

        private fun observeAllCities() {
            disposable.add(
                mDatabase.cityDao().getAllCities()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({
                        for (city in it) {
                            getWeatherData(city)
                        }
                        Log.d("response", it.toString())

                    }, {
                        Log.d("response error", it.message)

                    })
            )
        }

        private fun getWeatherData(city: City) {
            val call = mWebservice.getWeatherData(city.name + "," + city.countryCode)
            call.enqueue(object : Callback<WeatherForecastResponseBody> {
                override fun onFailure(call: Call<WeatherForecastResponseBody>, t: Throwable) {
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
        }*/


        /*override fun onFloatingButtonClicked() {
            view.navigateToAddCity()
        }

        override fun onDetach() {
            disposable.dispose()
        }*/
    }