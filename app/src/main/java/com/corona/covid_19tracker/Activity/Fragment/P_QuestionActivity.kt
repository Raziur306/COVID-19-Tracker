package com.corona.covid_19tracker.Activity.Fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.corona.covid_19tracker.databinding.ActivityPquestionBinding

class P_QuestionActivity : Fragment() {
    private lateinit var binding: ActivityPquestionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityPquestionBinding.inflate(layoutInflater)




        return binding.root
    }
}