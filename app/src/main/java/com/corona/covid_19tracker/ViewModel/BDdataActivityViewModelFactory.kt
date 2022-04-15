package com.corona.covid_19tracker.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corona.covid_19tracker.Repository.BDdataRepository

@Suppress("UNCHECKED_CAST")
class BDdataActivityViewModelFactory(private val repository: BDdataRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BDdataActivityViewModel(repository) as T
    }
}