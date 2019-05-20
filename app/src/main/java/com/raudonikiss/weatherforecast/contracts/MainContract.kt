package com.raudonikiss.weatherforecast.contracts

interface MainContract {

    interface View{
        fun navigateToCities()
        fun navigateToSettings()
        fun changeTitle(title: String)
    }
    interface Presenter{
        fun onNavigationItemClicked(id: Int)
    }
}