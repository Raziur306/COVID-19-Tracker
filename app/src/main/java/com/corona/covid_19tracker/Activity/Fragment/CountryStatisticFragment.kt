package com.corona.covid_19tracker.Activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.databinding.FragmentCountryStatisticBinding


class CountryStatisticFragment : Fragment() {
    private lateinit var binding: FragmentCountryStatisticBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryStatisticBinding.inflate(layoutInflater)

        return binding.root
    }


}