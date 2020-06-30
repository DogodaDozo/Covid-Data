package fr.epita.android.covidata2019

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GraphCountryAdapter(val context : Activity, val countryData : List<CountryData>, val onItemClickListener: View.OnClickListener) : RecyclerView.Adapter<GraphCountryAdapter.Viewholder>() {

    class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val countryName : TextView = itemView.findViewById(R.id.list_item_country)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphCountryAdapter.Viewholder {
        val rowView : View = LayoutInflater.from(context)
            .inflate(R.layout.activity_graph_country_list_item, parent, false)

        rowView.setOnClickListener(onItemClickListener)
        return GraphCountryAdapter.Viewholder(rowView)
    }

    override fun getItemCount(): Int {
        return countryData.size
    }

    override fun onBindViewHolder(holder: GraphCountryAdapter.Viewholder, position: Int) {
        val country : CountryData = countryData[position]
        holder.countryName.text = country.Country

        holder.itemView.tag = position
    }
}