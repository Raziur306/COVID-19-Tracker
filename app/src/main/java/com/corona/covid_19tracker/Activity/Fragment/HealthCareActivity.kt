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
import com.corona.covid_19tracker.Activity.WebViewActivity
import com.corona.covid_19tracker.Units.Unit
import com.corona.covid_19tracker.databinding.ActivityHealthCareBinding

class HealthCareActivity : Fragment() {
    private lateinit var binding: ActivityHealthCareBinding
    private lateinit var intent: Intent
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityHealthCareBinding.inflate(inflater, container, false)
        intent = Intent(context, WebViewActivity::class.java)
        binding.callNow.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:33313")
            startActivity(intent)
        }
        binding.hotLines.setOnClickListener {
            intent.putExtra("url", Unit.HOTLINES_NUMBER)
            startActivity(intent)
        }
        binding.ownTestBtn.setOnClickListener {
            intent.putExtra("url", Unit.TEST_BY_OWN)
            startActivity(intent)
        }
        binding.applyVaccine.setOnClickListener {
            intent.putExtra("url", Unit.APPLY_VACCINE)
            startActivity(intent)

        }
        binding.applyForTest.setOnClickListener {
            intent.putExtra("url", Unit.BRAC_TEST_APPLY)
            startActivity(intent)

        }
        return binding.root
    }
}
