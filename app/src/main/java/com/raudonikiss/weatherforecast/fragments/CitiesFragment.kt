package com.raudonikiss.weatherforecast.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.adapters.CitiesAdapter
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import com.raudonikiss.weatherforecast.viewModels.CitiesViewModel
import kotlinx.android.synthetic.main.fragment_cities.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CitiesFragment : Fragment() {

    //UI
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mViewManager: RecyclerView.LayoutManager
    private lateinit var mViewAdapter: CitiesAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    //Variables
    private val mSharedPreferences: SharedPreferences by inject()
    private lateinit var mRootView: View
    private var mTempUnits: String = ""
    private val viewModel: CitiesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_cities, container, false)
        setUpRecyclerView()
        setUpListeners()

        return mRootView
    }

    override fun onDetach() {
        viewModel.dispose()
        super.onDetach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
    }

    private fun setUpListeners() {
        mRootView.fb_add_city.setOnClickListener {
            navigateToAddCity()
        }
        mSwipeRefreshLayout = mRootView.swipe_to_refresh
        mSwipeRefreshLayout.setOnRefreshListener {
            //FIXME temporary fix
            if(viewModel.getAllForecasts().value!!.isNotEmpty())
                viewModel.updateAllForecasts()
            else{
                mSwipeRefreshLayout.isRefreshing = false
            }
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
            hasFixedSize()
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerView)

    }

    private fun setUpObservers() {
        viewModel.getAllForecasts().observe(this,
            Observer { t ->
                updateList(t)
                Log.v("CitiesFragment", t.toString())
                mSwipeRefreshLayout.isRefreshing = false
            })
    }

    private fun navigateToAddCity() {
        findNavController().navigate(R.id.addCityFragment)
    }

    private fun updateList(list: List<WeatherForecast>) {
        mViewAdapter.updateList(list)
        if (list.isEmpty()) {
            mRootView.label_no_cities.visibility = View.VISIBLE
        } else {
            mRootView.label_no_cities.visibility = View.GONE
        }
    }

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.removeListItem(viewHolder.adapterPosition)
        }

    }

}