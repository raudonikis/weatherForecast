package com.raudonikiss.weatherforecast.contracts

interface AddCityContract {

    interface View{
        fun displayError()
    }
    interface Presenter{
        fun onPlaceSelected(city : String?, country : String?)
    }
}
