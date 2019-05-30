package com.raudonikiss.weatherforecast.contracts

interface AddCityContract {

    interface View{
        fun displaySearchError()
        fun displayNoCityError()
        fun displaySuccess()
        fun navigateToCities()
    }
    interface Presenter{
        fun setPlaceData(cityId: String?, cityName : String?, countryId : String?, countryName : String?)
        fun onConfirmClicked()
        fun clearPlaceData()
        fun onDetach()
    }
}
