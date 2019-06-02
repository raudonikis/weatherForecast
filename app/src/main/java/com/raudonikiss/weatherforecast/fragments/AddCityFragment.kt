package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.viewModels.AddCityViewModel
import kotlinx.android.synthetic.main.fragment_add_city.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddCityFragment : Fragment(){

    //UI
    private lateinit var mAutoCompleteFragment: AutocompleteSupportFragment
    private lateinit var mRootView: View
    //Variables
    private val viewModel : AddCityViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_add_city, container, false)
        setUpListeners()

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
            navigateToCities()
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
                displayError(ERROR_SEARCH)
            }

        })
    }

    private fun displayError(id: Int) {
        when (id) {
            ERROR_SEARCH -> Snackbar.make(mRootView, R.string.search_error, Snackbar.LENGTH_SHORT).show()
            ERROR_DUPLICATE -> Snackbar.make(mRootView, R.string.error_duplicate_city, Snackbar.LENGTH_SHORT).show()
            ERROR_NO_DATA -> Snackbar.make(mRootView, R.string.no_city_error, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun displaySuccess() {
        Snackbar.make(mRootView, getString(R.string.city_add_success), Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToCities() {
        findNavController().navigate(R.id.citiesFragment)
    }

    companion object {
        const val ERROR_SEARCH = 1
        const val ERROR_DUPLICATE = 2
        const val ERROR_NO_DATA = 3
    }

}