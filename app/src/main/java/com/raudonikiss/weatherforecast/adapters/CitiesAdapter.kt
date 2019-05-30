package com.raudonikiss.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import kotlinx.android.synthetic.main.city_list_item.view.*

class CitiesAdapter(private var dataSet : List<WeatherForecast>) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>(){

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
            view.temperature.text = item.temp.toString()
            view.min_max_temperatures.text = "${item.temp_max} / ${item.temp_min}"
        }
    }
}