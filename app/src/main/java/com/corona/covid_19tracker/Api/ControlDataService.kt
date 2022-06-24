package com.corona.covid_19tracker.Api


import com.corona.covid_19tracker.Model.ControllerModel
import retrofit2.Response

import retrofit2.http.GET

interface ControlDataService {
    @GET("/Api/covid_19_tracker/controller.html")
    suspend fun getControlData(): Response<ControllerModel>
}