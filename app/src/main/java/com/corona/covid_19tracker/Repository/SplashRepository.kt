package com.corona.covid_19tracker.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corona.covid_19tracker.Api.ControlDataService
import com.corona.covid_19tracker.Model.ControllerModel


class SplashRepository(private val controlDataService: ControlDataService) {
    private val controllerLiveData = MutableLiveData<Response<ControllerModel>>()
    val liveData: LiveData<Response<ControllerModel>> = controllerLiveData

    suspend fun getResult() {
        val result = controlDataService.getControlData()
        try {
            if (result.body() != null)
                controllerLiveData.postValue(Response.Success(result.body()))
            else
                controllerLiveData.postValue(Response.Error("Error"))
        } catch (e: Exception) {
            controllerLiveData.postValue(Response.Error("Error"))
        }


    }

}