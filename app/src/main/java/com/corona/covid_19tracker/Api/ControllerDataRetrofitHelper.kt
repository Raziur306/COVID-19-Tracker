package com.corona.covid_19tracker.Api

import com.corona.covid_19tracker.Units.Unit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ControllerDataRetrofitHelper {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Unit.CONTROL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}