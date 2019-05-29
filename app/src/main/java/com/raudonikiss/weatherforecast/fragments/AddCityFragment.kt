package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.contracts.AddCityContract
import com.raudonikiss.weatherforecast.contracts.CitiesContract

class AddCityFragment : Fragment(), AddCityContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_city, container, false)
    }

}