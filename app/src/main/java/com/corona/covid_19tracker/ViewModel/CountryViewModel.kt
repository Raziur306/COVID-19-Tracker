package com.corona.covid_19tracker.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.Repository.CountryRepository
import com.corona.covid_19tracker.Repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {
    init {
        getResult()
    }

    fun getResult() {
        GlobalScope.launch(Dispatchers.IO) {
            repository.getResult()
        }
    }
    val result: LiveData<Response<CountryDataModel>> get() = repository.liveData

}