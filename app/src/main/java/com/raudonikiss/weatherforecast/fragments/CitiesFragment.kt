package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.adapters.CitiesAdapter
import com.raudonikiss.weatherforecast.contracts.CitiesContract
import com.raudonikiss.weatherforecast.presenters.CitiesPresenter
import kotlinx.android.synthetic.main.fragment_cities.*

class CitiesFragment : Fragment(), CitiesContract.View {

    //UI
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mViewManager : RecyclerView.LayoutManager
    private lateinit var mViewAdapter : RecyclerView.Adapter<*>
    //Variables
    private lateinit var mPresenter : CitiesContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter = CitiesPresenter(this)
        setUpRecyclerView()
        setUpListeners()
    }

    private fun setUpListeners(){
        fb_add_city.setOnClickListener {
            mPresenter.onFloatingButtonClicked()
        }
    }

    private fun setUpRecyclerView(){
        mViewManager = LinearLayoutManager(activity)
        mViewAdapter = CitiesAdapter(listOf())

        mRecyclerView = cities_recycler_view.apply {
            layoutManager = mViewManager
            adapter = mViewAdapter
        }

    }

    override fun navigateToAddCity() {
        findNavController().navigate(R.id.addCityFragment)
    }

}