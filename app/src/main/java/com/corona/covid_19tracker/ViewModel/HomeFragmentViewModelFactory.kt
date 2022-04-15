package com.corona.covid_19tracker.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.corona.covid_19tracker.Repository.CountryRepository

@Suppress("UNCHECKED_CAST")
class HomeFragmentViewModelFactory(private val repository: CountryRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(repository) as T
    }
}