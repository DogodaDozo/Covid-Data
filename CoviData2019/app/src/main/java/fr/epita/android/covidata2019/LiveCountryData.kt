package fr.epita.android.covidata2019

data class LiveCountryData(val Country : String, val CountryCode : String, val Province : String,
                           val City : String, val CityCode : String, val Lat : String,
                           val Lon : String, val Confirmed : Int, val Deaths : Int,
                           val Recovered : Int, val Active : Int, val Date : String)