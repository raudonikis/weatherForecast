package com.raudonikiss.weatherforecast.fragments

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.raudonikiss.weatherforecast.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }

    override fun onStart() {
        super.onStart()
        setListeners()

    }

    private fun setListeners(){
        findPreference<Preference>("button_add")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.addCityFragment)
            true
        }
    }
}