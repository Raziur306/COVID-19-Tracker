package com.corona.covid_19tracker.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.corona.covid_19tracker.databinding.ActivityWebViewBinding

class WebViewActivity : Fragment() {
    private lateinit var binding :ActivityWebViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = ActivityWebViewBinding.inflate(layoutInflater)





        return binding.root
    }
}