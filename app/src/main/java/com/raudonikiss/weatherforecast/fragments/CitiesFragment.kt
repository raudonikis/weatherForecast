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
import com.raudonikiss.weatherforecast.base.dependencyRetriever
import com.raudonikiss.weatherforecast.contracts.CitiesContract
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import com.raudonikiss.weatherforecast.presenters.CitiesPresenter
import kotlinx.android.synthetic.main.fragment_cities.*

class CitiesFragment : Fragment(), CitiesContract.View {

    //UI
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mViewManager : RecyclerView.LayoutManager
    private lateinit var mViewAdapter : CitiesAdapter
    //Variables
    private lateinit var mPresenter : CitiesContract.Presenter
    private lateinit var mDatabase : AppDatabase
    private lateinit var mWebservice : Webservice

    private var mTempUnits : String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onStart() {
        super.onStart()
        mDatabase = activity!!.dependencyRetriever.db
        mWebservice = activity!!.dependencyRetriever.webservice
        mPresenter = CitiesPresenter(this, mDatabase, mWebservice)
        mTempUnits = activity!!.dependencyRetriever.sharedPreferences.getString("units", "°K")
        setUpRecyclerView()
        setUpListeners()
    }

    override fun onDetach() {
        mPresenter.onDetach()
        super.onDetach()
    }

    private fun setUpListeners(){
        fb_add_city.setOnClickListener {
            mPresenter.onFloatingButtonClicked()
        }
    }

    private fun setUpRecyclerView(){
        mViewManager = LinearLayoutManager(activity)
        mViewAdapter = CitiesAdapter(listOf(), mTempUnits)

        mRecyclerView = cities_recycler_view.apply {
            layoutManager = mViewManager
            adapter = mViewAdapter
        }

    }

    override fun navigateToAddCity() {
        findNavController().navigate(R.id.addCityFragment)
    }

    override fun updateList(list: List<WeatherForecast>) {
        mViewAdapter.updateList(list)
        if(list.isEmpty()){
            label_no_cities.visibility = View.VISIBLE
        }else{
            label_no_cities.visibility = View.GONE
        }
    }

}