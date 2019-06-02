package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.error_handling.ResponseStatus
import com.raudonikiss.weatherforecast.error_handling.StatusEvent
import com.raudonikiss.weatherforecast.viewModels.AddCityViewModel
import kotlinx.android.synthetic.main.fragment_add_city.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddCityFragment : Fragment() {

    //UI
    private lateinit var mAutoCompleteFragment: AutocompleteSupportFragment
    private lateinit var mRootView: View
    //Variables
    private val viewModel: AddCityViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_add_city, container, false)
        setUpListeners()
        setUpObservers()
        return mRootView
    }

    override fun onDetach() {
        viewModel.dispose()
        super.onDetach()
    }

    private fun setUpListeners() {
        setUpSearch()
        mRootView.button_confirm.setOnClickListener {
            viewModel.saveCity()
        }
        mAutoCompleteFragment.view?.findViewById<View>(R.id.places_autocomplete_clear_button)?.setOnClickListener {
            mAutoCompleteFragment.setText("")
            viewModel.clearCityData()
        }
    }

    private fun setUpSearch() {
        mAutoCompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        mAutoCompleteFragment.setTypeFilter(TypeFilter.CITIES)
        mAutoCompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS))

        mAutoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                if (place.addressComponents != null) {
                    for (component in place.addressComponents!!.asList()) {
                        if (component.types[0] == "country") {
                            //City id, name, country code, country name
                            viewModel.setCityData(place.name, component.shortName)
                            break
                        }
                    }
                }
            }

            override fun onError(p0: Status) {
                viewModel.status.postValue(ResponseStatus.SEARCH_ERROR)
            }

        })
    }

    private fun setUpObservers() {
        viewModel.status.observe(viewLifecycleOwner, Observer {
            val status = getStatusMessage(it)
            if (status != null) {
                Snackbar.make(mRootView, status, Snackbar.LENGTH_SHORT).show()
                if(viewModel.status.value == ResponseStatus.SUCCESS){
                    navigateToCities()
                }
                viewModel.status.value = ResponseStatus.NONE
            }
        })
    }

    private fun navigateToCities() {
        findNavController().navigate(R.id.citiesFragment)
    }

    private fun getStatusMessage(statusEvent: StatusEvent) = if (statusEvent.getErrorResource() == 0) {
        null
    } else {
        getString(statusEvent.getErrorResource())
    }
}