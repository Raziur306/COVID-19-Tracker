package com.corona.covid_19tracker.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corona.covid_19tracker.Api.DistrictDataService
import com.corona.covid_19tracker.Model.DistrictDataModel
import retrofit2.Retrofit

class BDdataRepository(private val districtDataService: DistrictDataService) {
    private val districtLiveData = MutableLiveData<Response<DistrictDataModel>>()
    val liveData: LiveData<Response<DistrictDataModel>> = districtLiveData
    suspend fun getResult() {
        try {
            val result = districtDataService.getDistrictData()
            if (result.body() != null) {
                districtLiveData.postValue(Response.Success(result.body()))
            }
        } catch (e: Exception) {
            districtLiveData.postValue(Response.Error("Server Communication Failed"))
        }
    }
}