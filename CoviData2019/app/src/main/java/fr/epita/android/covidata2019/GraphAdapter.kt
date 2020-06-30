package fr.epita.android.covidata2019

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GraphAdapter(val context : Activity, val countryData : List<LiveCountryData>, val max : Int, val category : Int) : RecyclerView.Adapter<GraphAdapter.Viewholder>() {
    class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val date : TextView = itemView.findViewById(R.id.dateBar)
        val progressBar : ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val rowView : View = LayoutInflater.from(context)
            .inflate(R.layout.graph_bars, parent, false)
        return Viewholder(rowView)
    }

    override fun getItemCount(): Int {
        return countryData.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val country : LiveCountryData = countryData[position]
        var tmp = country.Date.split("-", "T")

        //holder.date.text = country.Date
        holder.date.text = tmp[0]
        if (tmp[1] == "01")
        {
            holder.date.text = tmp[2].plus(" January ".plus(tmp[0]))
        }
        else if (tmp[1] == "02")
        {
            holder.date.text = tmp[2].plus(" February ".plus(tmp[0]))
        }
        else if (tmp[1] == "03")
        {
            holder.date.text = tmp[2].plus(" March ".plus(tmp[0]))
        }
        else if (tmp[1] == "04")
        {
            holder.date.text = tmp[2].plus(" April ".plus(tmp[0]))
        }
        else if (tmp[1] == "05")
        {
            holder.date.text = tmp[2].plus(" May ".plus(tmp[0]))
        }
        else if (tmp[1] == "06")
        {
            holder.date.text = tmp[2].plus(" June ".plus(tmp[0]))
        }
        else if (tmp[1] == "07")
        {
            holder.date.text = tmp[2].plus(" July ".plus(tmp[0]))
        }
        else if (tmp[1] == "08")
        {
            holder.date.text = tmp[2].plus(" August ".plus(tmp[0]))
        }
        else if (tmp[1] == "09")
        {
            holder.date.text = tmp[2].plus(" September ".plus(tmp[0]))
        }
        else if (tmp[1] == "10")
        {
            holder.date.text = tmp[2].plus(" October ".plus(tmp[0]))
        }
        else if (tmp[1] == "11")
        {
            holder.date.text = tmp[2].plus(" November ".plus(tmp[0]))
        }
        else
        {
            holder.date.text = tmp[2].plus(" December ".plus(tmp[0]))
        }
        holder.progressBar.max = max

        if (category == 0)
            holder.progressBar.progress = country.Confirmed
        else if (category == 1)
            holder.progressBar.progress = country.Deaths
        else if (category == 2)
            holder.progressBar.progress = country.Recovered

        holder.itemView.tag = position
    }
}