package com.raudonikiss.weatherforecast.fragments

import android.content.SharedPreferences
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
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import com.raudonikiss.weatherforecast.presenters.CitiesPresenter
import kotlinx.android.synthetic.main.fragment_cities.view.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class CitiesFragment : Fragment(), CitiesContract.View {

    //UI
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mViewManager: RecyclerView.LayoutManager
    private lateinit var mViewAdapter: CitiesAdapter
    //Variables
    private lateinit var mPresenter: CitiesContract.Presenter
    private val mSharedPreferences: SharedPreferences by inject()
    private lateinit var mRootView: View
    private var mTempUnits: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_cities, container, false)
        mPresenter = CitiesPresenter(this, get(), get())
        setUpRecyclerView()
        setUpListeners()
        return mRootView
    }

    override fun onDetach() {
        mPresenter.onDetach()
        super.onDetach()
    }

    private fun setUpListeners() {
        mRootView.fb_add_city.setOnClickListener {
            mPresenter.onFloatingButtonClicked()
        }
    }

    private fun setUpRecyclerView() {
        mViewManager = LinearLayoutManager(activity)

        val units = mSharedPreferences.getString("units", "°K")
        if (units != null) mTempUnits = units

        mViewAdapter = CitiesAdapter(listOf(), mTempUnits)

        mRecyclerView = mRootView.cities_recycler_view.apply {
            layoutManager = mViewManager
            adapter = mViewAdapter
        }

    }

    override fun navigateToAddCity() {
        findNavController().navigate(R.id.addCityFragment)
    }

    override fun updateList(list: List<WeatherForecast>) {
        mViewAdapter.updateList(list)
        if (list.isEmpty()) {
            mRootView.label_no_cities.visibility = View.VISIBLE
        } else {
            mRootView.label_no_cities.visibility = View.GONE
        }
    }

}