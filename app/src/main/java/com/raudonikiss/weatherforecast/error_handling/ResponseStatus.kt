package com.raudonikiss.weatherforecast.error_handling

import androidx.annotation.StringRes
import com.raudonikiss.weatherforecast.R

enum class ResponseStatus(@StringRes private val resourceId: Int) :
    StatusEvent {
    NONE(0),
    LOADING(0),
    SUCCESS(0),
    EMPTY(R.string.error_no_city_selected),
    DUPLICATE(R.string.error_duplicate_city),
    SEARCH_ERROR(R.string.error_search),
    RESPONSE_ERROR(R.string.error_response),
    NETWORK_ERROR(R.string.error_network),
    NO_CITY_FOUND(R.string.error_no_city_found),
    ADD_SUCCESS(R.string.city_add_success);

    override fun getErrorResource() = resourceId
}