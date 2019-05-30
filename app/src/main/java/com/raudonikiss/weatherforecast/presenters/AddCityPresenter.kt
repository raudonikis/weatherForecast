package com.raudonikiss.weatherforecast.presenters

import com.raudonikiss.weatherforecast.contracts.AddCityContract

class AddCityPresenter(val view: AddCityContract.View) : AddCityContract.Presenter {

    private var city = ""
    private var country = ""
    private var countryCode = ""

    override fun setPlaceData(city: String?, countryCode: String?, country: String?) {
        if(city != null && country != null && countryCode != null){
            this.city = city
            this.country = country
            this.countryCode = countryCode
        }else{
            view.displaySearchError()
        }
    }

    override fun clearPlaceData() {
        city = ""
        country = ""
        countryCode = ""
    }

    override fun onConfirmClicked() {
        if(city.isNotBlank() && country.isNotBlank()){
            //Add city to database
        }else{
            view.displayNoCityError()
        }
    }
}