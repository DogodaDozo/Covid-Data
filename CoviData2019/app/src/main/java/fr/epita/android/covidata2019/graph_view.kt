package fr.epita.android.covidata2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_graph_view.*
import kotlinx.android.synthetic.main.activity_totals_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class graph_view : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_view)

        var countryData : List<CountryData> = arrayListOf()
        var countryName : String = ""
        var totalDeaths : Int = 0
        var totalConfirmed : Int = 0
        var totalRecovered : Int = 0

        //Webservice
        val baseUrl = "https://api.covid19api.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverter)
            .build()

        val service = retrofitClient.create(WSInterface::class.java)
        val callback : Callback<WorldData> = object : Callback<WorldData> {
            override fun onFailure(call: Call<WorldData>, t: Throwable) {
            }

            override fun onResponse(call: Call<WorldData>, response: Response<WorldData>) {
                if (response.code() == 200)
                {
                    if (response.body() != null)
                    {
                        val data : WorldData = response.body()!!
                        countryData = data.Countries

                        //List of countries
                        val onItemClickListener = View.OnClickListener { clickedRow ->
                            val clickedCountry : CountryData = countryData[clickedRow.tag as Int]
                            countryName = clickedCountry.Country
                            Toast.makeText(this@graph_view, "Country selected : " + countryName, Toast.LENGTH_SHORT).show()
                            totalDeaths = clickedCountry.TotalDeaths
                            totalConfirmed = clickedCountry.TotalConfirmed
                            totalRecovered = clickedCountry.TotalRecovered
                        }
                        countryList.adapter =
                            GraphCountryAdapter(this@graph_view, countryData, onItemClickListener)
                        countryList.hasFixedSize()
                        countryList.layoutManager = LinearLayoutManager(this@graph_view, LinearLayoutManager.HORIZONTAL, false)
                        countryList.addItemDecoration(DividerItemDecoration(this@graph_view, LinearLayoutManager.HORIZONTAL))
                    }
                    else
                    {
                        Toast.makeText(this@graph_view, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        service.getSummary().enqueue(callback)

        val confirmedButton = findViewById<Button>(R.id.confirmed)
        confirmedButton.setOnClickListener{
            val callback : Callback<List<LiveCountryData>> = object : Callback<List<LiveCountryData>> {
                override fun onFailure(call: Call<List<LiveCountryData>>, t: Throwable) {
                }

                override fun onResponse(call: Call<List<LiveCountryData>>, response: Response<List<LiveCountryData>>) {
                    if (response.code() == 200)
                    {
                        if (response.body() != null)
                        {
                            val data : List<LiveCountryData> = response.body()!!

                            graph.adapter =
                                GraphAdapter(this@graph_view, data, totalConfirmed, 0)
                            graph.hasFixedSize()
                            graph.layoutManager = LinearLayoutManager(this@graph_view)
                            graph.addItemDecoration(DividerItemDecoration(this@graph_view, LinearLayoutManager.VERTICAL))
                        }
                        else
                        {
                            Toast.makeText(this@graph_view, "Fail", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@graph_view, "Select a country!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            service.getLiveData(countryName).enqueue(callback)
        }

        val deathsButton = findViewById<Button>(R.id.deaths)
        deathsButton.setOnClickListener{
            val callback : Callback<List<LiveCountryData>> = object : Callback<List<LiveCountryData>> {
                override fun onFailure(call: Call<List<LiveCountryData>>, t: Throwable) {
                }

                override fun onResponse(call: Call<List<LiveCountryData>>, response: Response<List<LiveCountryData>>) {
                    if (response.code() == 200)
                    {
                        if (response.body() != null)
                        {
                            val data : List<LiveCountryData> = response.body()!!

                            graph.adapter =
                                GraphAdapter(this@graph_view, data, totalDeaths, 1)
                            graph.hasFixedSize()
                            graph.layoutManager = LinearLayoutManager(this@graph_view)
                            graph.addItemDecoration(DividerItemDecoration(this@graph_view, LinearLayoutManager.VERTICAL))
                        }
                        else
                        {
                            Toast.makeText(this@graph_view, "Fail", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@graph_view, "Select a country!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            service.getLiveData(countryName).enqueue(callback)

        }

        val recoveredButton = findViewById<Button>(R.id.recovered)
        recoveredButton.setOnClickListener{
            val callback : Callback<List<LiveCountryData>> = object : Callback<List<LiveCountryData>> {
                override fun onFailure(call: Call<List<LiveCountryData>>, t: Throwable) {
                }

                override fun onResponse(call: Call<List<LiveCountryData>>, response: Response<List<LiveCountryData>>) {
                    if (response.code() == 200)
                    {
                        if (response.body() != null)
                        {
                            val data : List<LiveCountryData> = response.body()!!

                            graph.adapter =
                                GraphAdapter(this@graph_view, data, totalRecovered, 2)
                            graph.hasFixedSize()
                            graph.layoutManager = LinearLayoutManager(this@graph_view)
                            graph.addItemDecoration(DividerItemDecoration(this@graph_view, LinearLayoutManager.VERTICAL))
                        }
                        else
                        {
                            Toast.makeText(this@graph_view, "Fail", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@graph_view, "Select a country!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            service.getLiveData(countryName).enqueue(callback)
        }
    }
}