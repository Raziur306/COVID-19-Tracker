package com.corona.covid_19tracker.Api

import com.corona.covid_19tracker.Model.DistrictDataModel
import retrofit2.Response
import retrofit2.http.GET

interface DistrictDataService {
    @GET("/api/district")
    suspend fun getDistrictData(): Response<DistrictDataModel>
}