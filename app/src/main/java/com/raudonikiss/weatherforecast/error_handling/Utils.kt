package com.raudonikiss.weatherforecast.error_handling

import android.content.Context

class Utils {
    companion object {
        fun getStatusMessage(context: Context?, statusEvent: StatusEvent): String? {
            return if (statusEvent.getErrorResource() == 0) {
                null
            } else {
                context?.getString(statusEvent.getErrorResource())
            }
        }
    }
}