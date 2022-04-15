package com.corona.covid_19tracker.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.corona.covid_19tracker.Model.DistrictDataModel
import com.corona.covid_19tracker.Repository.BDdataRepository
import com.corona.covid_19tracker.Repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BDdataActivityViewModel(private val repository: BDdataRepository) : ViewModel() {
    init {
        getResult()
    }

    fun getResult() {
        GlobalScope.launch(Dispatchers.IO) {
            repository.getResult()
        }
    }

    val result: LiveData<Response<DistrictDataModel>> get() = repository.liveData
}