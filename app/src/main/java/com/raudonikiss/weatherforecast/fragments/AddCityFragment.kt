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
import com.raudonikiss.weatherforecast.contracts.AddCityContract
import kotlinx.android.synthetic.main.fragment_add_city.*

class AddCityFragment : Fragment(), AddCityContract.View {

    //UI
    private lateinit var mAutoCompleteFragment : AutocompleteSupportFragment
    private lateinit var mRootView : View
    //Variables
    private lateinit var mPresenter : AddCityContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_add_city, container, false)
        return mRootView
    }

    override fun onStart() {
        super.onStart()
//        mPresenter = AddCityPresenter(this, activity!!.dependencyRetriever.db.cityDao())
        setUpListeners()
    }

    override fun onDetach() {
        mPresenter.onDetach()
        super.onDetach()
    }

    private fun setUpListeners(){
        setUpSearch()
        button_confirm.setOnClickListener {
            mPresenter.onConfirmClicked()
        }
        mAutoCompleteFragment.view?.findViewById<View>(R.id.places_autocomplete_clear_button)?.setOnClickListener {
            mAutoCompleteFragment.setText("")
            mPresenter.clearCityData()
        }
    }

    private fun setUpSearch(){
        mAutoCompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        mAutoCompleteFragment.setTypeFilter(TypeFilter.CITIES)
        mAutoCompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS))

        mAutoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {
                if(place.addressComponents != null){
                    for(component in place.addressComponents!!.asList()){
                        if(component.types[0] == "country"){
                            //City id, name, country code, country name
                            mPresenter.setCityData(place.id, place.name, component.shortName, component.name)
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

    override fun displayError(id: Int) {
        when(id){
            ERROR_SEARCH -> Snackbar.make(mRootView, R.string.search_error, Snackbar.LENGTH_SHORT).show()
            ERROR_DUPLICATE -> Snackbar.make(mRootView, R.string.error_duplicate_city, Snackbar.LENGTH_SHORT).show()
            ERROR_NO_DATA -> Snackbar.make(mRootView, R.string.no_city_error, Snackbar.LENGTH_SHORT).show()
        }
    }
    override fun displaySuccess() {
        Snackbar.make(mRootView, getString(R.string.city_add_success), Snackbar.LENGTH_SHORT).show()
    }

    override fun navigateToCities() {
        findNavController().navigate(R.id.citiesFragment)
    }

    companion object{
        const val ERROR_SEARCH = 1
        const val ERROR_DUPLICATE = 2
        const val ERROR_NO_DATA = 3
    }

}