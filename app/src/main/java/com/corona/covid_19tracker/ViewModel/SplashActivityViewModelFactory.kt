package com.corona.covid_19tracker.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corona.covid_19tracker.Repository.SplashRepository

@Suppress("UNCHECKED_CAST")
class SplashActivityViewModelFactory(private val repository: SplashRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashActivityViewModel(repository) as T
    }
}