package com.raudonikiss.weatherforecast.viewModels

import androidx.lifecycle.ViewModel
import com.raudonikiss.weatherforecast.contracts.AddCityContract
import com.raudonikiss.weatherforecast.data.CityDao
import com.raudonikiss.weatherforecast.objects.City
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddCityPresenter(val view: AddCityContract.View, private val cityDao : CityDao) : ViewModel(), AddCityContract.Presenter {

    private var cityId = ""
    private var cityName = ""
    private var countryId = ""
    private var countryName = ""

    private val disposables = CompositeDisposable()

    override fun setCityData(cityId: String?, cityName: String?, countryId: String?, countryName: String?) {
        if(cityId != null && cityName != null && countryId != null && countryName != null){
            this.cityId = cityId
            this.cityName = cityName
            this.countryId = countryId
            this.countryName = countryName
        }else{
            view.displayError(ERROR_SEARCH)
        }
    }

    override fun clearCityData() {
        cityId = ""
        cityName = ""
        countryId = ""
        countryName = ""
    }

    override fun onDetach() {
        disposables.dispose()
    }

    override fun onConfirmClicked() {
        if(!isCityDataEmpty()){

            val city = City(cityId, cityName, countryId, countryName)
            addCity(city)

        }else{
            view.displayError(ERROR_NO_DATA)
        }
    }

    private fun isCityDataEmpty(): Boolean{
        return cityId.isBlank() || cityName.isBlank() || countryId.isBlank() || countryName.isBlank()
    }

    private fun addCity(city: City){
        var result = 0L

        disposables.add(Completable.fromAction {
            result = cityDao.insertCity(city)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(result == -1L){
                    view.displayError(ERROR_DUPLICATE)
                }else{
                    view.navigateToCities()
                    view.displaySuccess()
                }
            })
    }

    companion object{
        const val ERROR_SEARCH = 1
        const val ERROR_DUPLICATE = 2
        const val ERROR_NO_DATA = 3
    }
}