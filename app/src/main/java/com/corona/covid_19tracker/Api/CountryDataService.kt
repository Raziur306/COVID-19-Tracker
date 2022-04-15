package com.corona.covid_19tracker.Api

import com.corona.covid_19tracker.Model.CountryDataModel
import retrofit2.Response
import retrofit2.http.GET

interface CountryDataService {
        @GET("/v2/countries")
        suspend fun getCountryData(): Response<CountryDataModel>


}