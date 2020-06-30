package fr.epita.android.covidata2019

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(val context : Activity, val countryData : List<CountryData>, val onItemClickListener: View.OnClickListener) : RecyclerView.Adapter<CountryAdapter.Viewholder>() {
    class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val countryName : TextView = itemView.findViewById(R.id.list_item_country_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val rowView : View = LayoutInflater.from(context)
            .inflate(R.layout.totals_screen_list_item, parent, false)

        rowView.setOnClickListener(onItemClickListener)
        return Viewholder(rowView)
    }

    override fun getItemCount(): Int {
        return countryData.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val country : CountryData = countryData[position]
        holder.countryName.text = country.Country

        holder.itemView.tag = position
    }
}