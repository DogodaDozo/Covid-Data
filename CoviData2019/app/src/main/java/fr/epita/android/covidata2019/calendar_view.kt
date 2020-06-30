package fr.epita.android.covidata2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_calendar_view.*
import kotlinx.android.synthetic.main.activity_totals_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class calendar_view : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_view)

        val countryName = intent.getStringExtra("countryName")
        calendar_screen_country.text = countryName

        var date : String = ""

        var baseUrl = "https://api.covid19api.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverter)
            .build()

        val service = retrofitClient.create(WSInterface::class.java)
        val callback : Callback<List<LiveCountryData>> = object : Callback<List<LiveCountryData>> {
            override fun onFailure(call: Call<List<LiveCountryData>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<LiveCountryData>>, response: Response<List<LiveCountryData>>) {
                if (response.code() == 200)
                {
                    if (response.body() != null)
                    {
                        val data : List<LiveCountryData> = response.body()!!
                        calendar_screen_calendar.setOnDateChangeListener{view, year, month, dayOfMonth ->
                            date = "$year-"
                            val m = month + 1
                            if (m < 10)
                            {
                                date += "0$m-"
                            }
                            else
                            {
                                date += "$m-"
                            }

                            if (dayOfMonth < 10)
                            {
                                date += "0$dayOfMonth"
                            }
                            else
                            {
                                date += "$dayOfMonth"
                            }

                            date += "T00:00:00Z"

                            var is_valid = 1
                            for (d in data)
                            {
                                if (d.Date == date)
                                {
                                    calendar_screen_confirmed_nb.text = d.Confirmed.toString()
                                    calendar_screen_deaths_nb.text = d.Deaths.toString()
                                    calendar_screen_recovered_nb.text = d.Recovered.toString()
                                    is_valid = 0
                                }
                            }

                            if (is_valid == 1)
                            {
                                calendar_screen_confirmed_nb.text = "0"
                                calendar_screen_deaths_nb.text = "0"
                                calendar_screen_recovered_nb.text = "0"
                                Toast.makeText(this@calendar_view, "Invalid date", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(this@calendar_view, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(this@calendar_view, "Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }

        service.getLiveData(countryName).enqueue(callback)
    }
}