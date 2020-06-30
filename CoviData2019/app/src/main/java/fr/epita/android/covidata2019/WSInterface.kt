package fr.epita.android.covidata2019

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WSInterface {
    @GET("summary")
    fun getSummary() : Call<WorldData>

    @GET("country/{name}")
    fun getLiveData(@Path("name") name : String) : Call<List<LiveCountryData>>
}