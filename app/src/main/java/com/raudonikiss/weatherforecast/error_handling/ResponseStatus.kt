package com.raudonikiss.weatherforecast.error_handling

import androidx.annotation.StringRes
import com.raudonikiss.weatherforecast.R

enum class ResponseStatus(@StringRes private val resourceId: Int) :
    StatusEvent {
    NONE(0),
    EMPTY(R.string.error_no_city_selected),
    DUPLICATE(R.string.error_duplicate_city),
    SEARCH_ERROR(R.string.error_search),
    RESPONSE_ERROR(R.string.error_response),
    NETWORK_ERROR(R.string.error_network),
    NO_CITY_FOUND(R.string.error_no_city_found),
    SUCCESS(R.string.city_add_success),
    LOADING(0);

    override fun getErrorResource() = resourceId
}