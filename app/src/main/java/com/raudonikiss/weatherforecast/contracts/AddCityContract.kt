package com.raudonikiss.weatherforecast.contracts

interface AddCityContract {

    interface View{
        fun displayError(id : Int)
        fun displaySuccess()
        fun navigateToCities()
    }
    interface Presenter{
        fun setCityData(cityId: String?, cityName : String?, countryId : String?, countryName : String?)
        fun onConfirmClicked()
        fun clearCityData()
        fun onDetach()
    }
}
