package com.raudonikiss.weatherforecast.presenters

import com.raudonikiss.weatherforecast.contracts.AddCityContract
import com.raudonikiss.weatherforecast.data.CityDao
import com.raudonikiss.weatherforecast.objects.City
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddCityPresenter(val view: AddCityContract.View, private val cityDao : CityDao) : AddCityContract.Presenter {

    private var cityId = ""
    private var cityName = ""
    private var countryId = ""
    private var countryName = ""

    private val disposables = CompositeDisposable()

    override fun setPlaceData(cityId: String?, cityName: String?, countryId: String?, countryName: String?) {
        if(cityId != null && cityName != null && countryId != null && countryName != null){
            this.cityId = cityId
            this.cityName = cityName
            this.countryId = countryId
            this.countryName = countryName
        }else{
            view.displaySearchError()
        }
    }

    override fun clearPlaceData() {
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

            disposables.add(Completable.fromAction {
               cityDao.insertCity(city)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.navigateToCities()
                    view.displaySuccess()
                }, {

                } ))
        }else{
            view.displayNoCityError()
        }
    }

    private fun isCityDataEmpty(): Boolean{
        return cityId.isBlank() || cityName.isBlank() || countryId.isBlank() || countryName.isBlank()
    }
}