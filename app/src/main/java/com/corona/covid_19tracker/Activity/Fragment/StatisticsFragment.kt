package com.corona.covid_19tracker.Activity.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.corona.covid_19tracker.Adapter.TabLayoutAdapter
import com.corona.covid_19tracker.databinding.FragmentStatisticsBinding
import com.google.android.material.tabs.TabLayout

class StatisticsFragment() : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title = "Statistics"

        binding.viewPager2.adapter = arguments?.let { TabLayoutAdapter(requireActivity(), it) }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
        binding.viewPager2.currentItem = arguments?.getInt("value", 0) ?: 0
        return binding.root
    }

}