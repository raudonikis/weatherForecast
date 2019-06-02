package com.raudonikiss.weatherforecast.error_handling

import androidx.annotation.StringRes

interface StatusEvent {
    @StringRes
    fun getErrorResource(): Int
}