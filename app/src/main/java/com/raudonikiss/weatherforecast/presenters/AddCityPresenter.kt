package com.raudonikiss.weatherforecast.presenters

import com.raudonikiss.weatherforecast.contracts.AddCityContract

class AddCityPresenter(val view: AddCityContract.View) : AddCityContract.Presenter {

    private var city = ""
    private var country = ""

    override fun onPlaceSelected(city: String?, country: String?) {
        if(city != null && country != null){
            this.city = city
            this.country = country
        }else{
            view.displayError()
        }
    }
}