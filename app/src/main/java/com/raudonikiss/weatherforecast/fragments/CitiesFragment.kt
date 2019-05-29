package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.contracts.CitiesContract
import com.raudonikiss.weatherforecast.presenters.CitiesPresenter
import kotlinx.android.synthetic.main.fragment_cities.*

class CitiesFragment : Fragment(), CitiesContract.View {

    private lateinit var mPresenter : CitiesContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cities, container, false)
        mPresenter = CitiesPresenter(this)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        setUpListeners()
    }

    private fun setUpListeners(){
        fb_add_city.setOnClickListener {
            mPresenter.onFloatingButtonClicked()
        }
    }

    override fun navigateToAddCity() {
        findNavController().navigate(R.id.addCityFragment)
    }

}