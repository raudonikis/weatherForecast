package com.raudonikiss.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raudonikiss.weatherforecast.contracts.MainContract

class MainActivity : AppCompatActivity(), MainContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
