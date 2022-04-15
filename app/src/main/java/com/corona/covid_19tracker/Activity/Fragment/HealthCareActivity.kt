package com.corona.covid_19tracker.Activity.Fragment

import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.corona.covid_19tracker.R
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment

class HealthCareActivity : Fragment() {
    var who1: ImageView? = null
    var viewAll: TextView? = null
    private val HOTLINES_NUMBER = "https://corona.gov.bd/call-center-lists"
    private val TEST_BY_OWN = "https://corona.gov.bd/corona-symptom"
    private val APPLY_VACCINE = "https://surokkha.gov.bd/enroll"
    private val BRAC_TEST_APPLY = "https://coronatest.brac.net"
    var callNow: Button? = null
    var hotLines: Button? = null
    var ownTestBtn: Button? = null
    var vaccineBtn: Button? = null
    var testBtn: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_health_care, container, false)
        initView(view)
        callNow!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:33313")
            startActivity(intent)
        }
        hotLines!!.setOnClickListener {  openBrowserTab() }
        ownTestBtn!!.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireActivity(), Uri.parse(TEST_BY_OWN))
        }
        vaccineBtn!!.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireActivity(), Uri.parse(APPLY_VACCINE))
        }
        testBtn!!.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireActivity(), Uri.parse(BRAC_TEST_APPLY))
        }
        return view
    }

    private fun openBrowserTab() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(HOTLINES_NUMBER))
    }

    private fun initView(view: View) {
        callNow = view.findViewById(R.id.callNow)
        hotLines = view.findViewById(R.id.hotLines)
        ownTestBtn = view.findViewById(R.id.ownTestBtn)
        vaccineBtn = view.findViewById(R.id.applyVaccine)
        testBtn = view.findViewById(R.id.applyForTest)
    }
}