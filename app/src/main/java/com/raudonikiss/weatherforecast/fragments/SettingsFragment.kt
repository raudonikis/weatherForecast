package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.contracts.SettingsContract

class SettingsFragment : PreferenceFragmentCompat(), SettingsContract.View {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }

    override fun onStart() {
        super.onStart()
        setListeners()

    }

    private fun setListeners(){
        findPreference("button_add").setOnPreferenceClickListener {
            findNavController().navigate(R.id.addCityFragment)
            true
        }
    }
}