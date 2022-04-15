package com.corona.covid_19tracker.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corona.covid_19tracker.Api.CountryDataService
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.utils.NetworkUtils

class CountryRepository(private val countryDataService: CountryDataService) {
    private val countryLiveData = MutableLiveData<Response<CountryDataModel>>()
    val liveData: LiveData<Response<CountryDataModel>> = countryLiveData
    suspend fun getResult() {
        try {
            val result = countryDataService.getCountryData()
            if (result.body() != null) {
                countryLiveData.postValue(Response.Success(result.body()))
            } else
                countryLiveData.postValue(Response.Error("Server Communication Failed"))
        } catch (e: Exception) {
            countryLiveData.postValue(Response.Error("Check Your Internet Connection"))
        }
    }
}