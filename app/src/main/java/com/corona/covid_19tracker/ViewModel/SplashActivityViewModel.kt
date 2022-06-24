package com.corona.covid_19tracker.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.corona.covid_19tracker.Model.ControllerModel
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.Repository.Response
import com.corona.covid_19tracker.Repository.SplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SplashActivityViewModel(private val repository: SplashRepository) : ViewModel() {
    init {
        getData()
    }

    private fun getData() {
        GlobalScope.launch(Dispatchers.IO) {
            repository.getResult()
        }
    }

    val result: LiveData<Response<ControllerModel>> get() = repository.liveData
}