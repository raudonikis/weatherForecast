package com.raudonikiss.weatherforecast.presenters

import android.util.Log
import com.raudonikiss.weatherforecast.contracts.CitiesContract
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.network.response.WeatherForecastResponseBody
import com.raudonikiss.weatherforecast.objects.City
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesPresenter(val view: CitiesContract.View, val mDatabase : AppDatabase, val mWebservice : Webservice) : CitiesContract.Presenter {

    var disposable = CompositeDisposable()

    init {
        observeAllCities()
        observeAllWeatherData()
    }

    private fun observeAllWeatherData()
    {
        disposable.add(
            mDatabase.weatherForecastDao().getAllWeatherForecasts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    view.updateList(it)
                    Log.d("response", it.toString())

                }, {
                    Log.d("response error", it.message)

                })) // ON DETACH CLEAR
    }
    private fun observeAllCities()
    {
        disposable.add(
            mDatabase.cityDao().getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    for (city in it)
                    {
                        getWeatherData(city)
                    }
                    Log.d("response", it.toString())

                }, {
                    Log.d("response error", it.message)

                }))
    }

    private fun getWeatherData(city:City)
    {
        val call = mWebservice.getWeatherData(city.name + "," + city.countryCode)
    call.enqueue(object : Callback<WeatherForecastResponseBody> {
        override fun onFailure(call: Call<WeatherForecastResponseBody>, t: Throwable) {
        }

        override fun onResponse(call: Call<WeatherForecastResponseBody>, response: Response<WeatherForecastResponseBody>) {
            val body = response.body()
            if(body != null){

                Log.d("response", body.toString())

                val weatherForecast = body.toWeatherForecast()
                disposable.add(
                    Completable.fromAction{
                        mDatabase.weatherForecastDao().insertWeatherForecast(weatherForecast)
                    }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())
                    }
            }
        })
    }


    override fun onFloatingButtonClicked() {
        view.navigateToAddCity()
    }

    override fun onDetach() {
        disposable.dispose()
    }
}