package com.raudonikiss.weatherforecast.contracts

interface AddCityContract {

    interface View{
        fun displaySearchError()
        fun displayNoCityError()
    }
    interface Presenter{
        fun setPlaceData(city : String?, countryCode : String?, country : String?)
        fun onConfirmClicked()
        fun clearPlaceData()
    }
}
