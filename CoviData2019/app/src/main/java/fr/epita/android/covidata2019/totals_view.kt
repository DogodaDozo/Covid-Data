package fr.epita.android.covidata2019

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_totals_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class totals_view : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totals_view)

        var countryData : List<CountryData> = arrayListOf()

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
                        total_screen_confirmed_nb.text = data.Global.TotalConfirmed.toString()
                        total_screen_deaths_nb.text = data.Global.TotalDeaths.toString()
                        total_screen_recovered_nb.text = data.Global.TotalRecovered.toString()
                        countryData = data.Countries

                        //List of countries
                        val onItemClickListener = View.OnClickListener { clickedRow ->
                            val clickedCountry : CountryData = countryData[clickedRow.tag as Int]

                            //Passing the name of the country to the calendar_view
                            val intent = Intent(this@totals_view, calendar_view::class.java)
                            intent.putExtra("countryName", clickedCountry.Country)
                            startActivity(intent)
                        }

                        total_screen_country_list.adapter =
                            CountryAdapter(this@totals_view, countryData, onItemClickListener)
                        total_screen_country_list.hasFixedSize()
                        total_screen_country_list.layoutManager = LinearLayoutManager(this@totals_view)
                        total_screen_country_list.addItemDecoration(DividerItemDecoration(this@totals_view, LinearLayoutManager.VERTICAL))
                    }
                    else
                    {
                        Toast.makeText(this@totals_view, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(this@totals_view, "Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }

        service.getSummary().enqueue(callback)
    }
}