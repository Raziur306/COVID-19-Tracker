package com.corona.covid_19tracker.Activity.Fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.corona.covid_19tracker.databinding.ActivityPreventionBinding

class PreventionActivity : Fragment() {
    private lateinit var binding:ActivityPreventionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityPreventionBinding.inflate(layoutInflater)






        return binding.root
    }
}