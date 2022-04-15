package com.corona.covid_19tracker.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.Repository.CountryRepository
import com.corona.covid_19tracker.Repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeFragmentViewModel internal constructor(private val repository: CountryRepository) :
    ViewModel() {
    init {
        getData();
    }

    fun getData() {
        GlobalScope.launch(Dispatchers.IO) {
            repository.getResult()

        }
    }

    val result: LiveData<Response<CountryDataModel>> get() = repository.liveData

}