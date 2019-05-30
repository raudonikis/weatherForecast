package com.raudonikiss.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import kotlinx.android.synthetic.main.city_list_item.view.*
import kotlin.math.roundToInt

class CitiesAdapter(private var dataSet : List<WeatherForecast>, private val tempUnits : String?) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataSet[position])
    }
    fun updateList(list : List<WeatherForecast>){
        this.dataSet = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){

        fun bindView(item: WeatherForecast){
            view.country_name.text = item.country
            view.city_name.text = item.city_name
            view.temperature.text = "${getTemperature(item.temp)} $tempUnits"
            view.min_max_temperatures.text = "${getTemperature(item.temp_max)} / ${getTemperature(item.temp_min)} $tempUnits"
        }
    }

    private fun getTemperature(temp: Double): Int{
        return when(tempUnits){
            "°C" -> convertToCelsius(temp)
            "°F" -> convertToFahrenheit(temp)
            else -> temp.roundToInt()
        }
    }

    private fun convertToCelsius(temp: Double): Int{
        return (temp - 273.15).roundToInt()
    }
    private fun convertToFahrenheit(temp: Double): Int{
        return ((temp - 273.15) * 9/5 + 32).roundToInt()
    }
}