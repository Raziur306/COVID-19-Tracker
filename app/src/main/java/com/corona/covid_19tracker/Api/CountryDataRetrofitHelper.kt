package com.corona.covid_19tracker.Api

import com.corona.covid_19tracker.Units.Unit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object CountryDataRetrofitHelper {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Unit.COUNTRY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object BdDataRetrofitHelper {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Unit.BD_STATE_DATA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}