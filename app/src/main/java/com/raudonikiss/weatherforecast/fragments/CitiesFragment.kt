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
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import com.raudonikiss.weatherforecast.presenters.CitiesPresenter
import kotlinx.android.synthetic.main.fragment_cities.view.*
import org.koin.android.ext.android.inject

class CitiesFragment : Fragment(), CitiesContract.View {

    //UI
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mViewManager : RecyclerView.LayoutManager
    private lateinit var mViewAdapter : CitiesAdapter
    //Variables
    private lateinit var mPresenter : CitiesContract.Presenter

    private lateinit var rootView: View
    private var mTempUnits : String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_cities, container, false)
//        mPresenter = CitiesPresenter(this, mDatabase, mWebservice)
//        mTempUnits = activity!!.dependencyRetriever.sharedPreferences.getString("units", "°K")*/
        setUpRecyclerView()
        setUpListeners()
        return rootView
    }

    override fun onDetach() {
        mPresenter.onDetach()
        super.onDetach()
    }

    private fun setUpListeners(){
        rootView.fb_add_city.setOnClickListener {
            mPresenter.onFloatingButtonClicked()
        }
    }

    private fun setUpRecyclerView(){
        mViewManager = LinearLayoutManager(activity)
        mViewAdapter = CitiesAdapter(listOf(), mTempUnits)

        mRecyclerView = rootView.cities_recycler_view.apply {
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
            rootView.label_no_cities.visibility = View.VISIBLE
        }else{
            rootView.label_no_cities.visibility = View.GONE
        }
    }

}