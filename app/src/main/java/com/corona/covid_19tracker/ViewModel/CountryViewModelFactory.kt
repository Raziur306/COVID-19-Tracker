package com.corona.covid_19tracker.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corona.covid_19tracker.Repository.CountryRepository

@Suppress("UNCHECKED_CAST")
class CountryViewModelFactory(private val repository: CountryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountryViewModel(repository) as T
    }
}