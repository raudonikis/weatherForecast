package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.contracts.AddCityContract
import com.raudonikiss.weatherforecast.presenters.AddCityPresenter

class AddCityFragment : Fragment(), AddCityContract.View {

    //UI
    private lateinit var mAutoCompleteFragment : AutocompleteSupportFragment
    private lateinit var mRootView : View
    //Variables
    private lateinit var mPresenter : AddCityContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = AddCityPresenter(this)
        mRootView = inflater.inflate(R.layout.fragment_add_city, container, false)
        return mRootView
    }

    override fun onStart() {
        super.onStart()
        setUpSearch()
    }

    private fun setUpSearch(){
        mAutoCompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        mAutoCompleteFragment.setTypeFilter(TypeFilter.CITIES)
        mAutoCompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS))
        mAutoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {

                if(place.addressComponents != null){
                    for(component in place.addressComponents!!.asList()){
                        if(component.types[0] == "country"){
                            mPresenter.onPlaceSelected(place.name, component.shortName)
                            break
                        }
                    }
                }
            }

            override fun onError(p0: Status) {
                displayError()
            }

        })
    }

    override fun displayError(){
        Snackbar.make(mRootView, R.string.search_error, Snackbar.LENGTH_SHORT).show()
    }

}