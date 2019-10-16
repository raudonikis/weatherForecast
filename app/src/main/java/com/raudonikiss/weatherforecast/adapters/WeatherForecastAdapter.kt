package com.raudonikiss.weatherforecast.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.raudonikiss.weatherforecast.R
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import com.raudonikiss.weatherforecast.utils.getTemperature
import kotlinx.android.synthetic.main.city_list_item.view.*

class WeatherForecastAdapter(private val tempUnits : String?, private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherForecast>() {

        override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherForecast,
            newItem: WeatherForecast
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return WeatherForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.city_list_item,
                parent,
                false
            ),
            interaction,
            tempUnits
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherForecastViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<WeatherForecast>) {
        differ.submitList(list)
    }

    class WeatherForecastViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?,
        private val tempUnits: String?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WeatherForecast) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.country_name.text = item.country
            itemView.city_name.text = item.city_name
            itemView.temperature.text = "${getTemperature(tempUnits, item.temp)} $tempUnits"
            itemView.min_max_temperatures.text = "${getTemperature(tempUnits, item.temp_max)} / ${getTemperature(tempUnits, item.temp_min)} $tempUnits"
            val resource = itemView.context.resources.getIdentifier("ic_"+item.icon, "drawable", itemView.context.packageName)
            itemView.weather_icon.setImageResource(resource)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: WeatherForecast)
    }
}
