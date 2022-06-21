package com.corona.covid_19tracker.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corona.covid_19tracker.Activity.Fragment.CountryStatisticFragment
import com.corona.covid_19tracker.Activity.Fragment.GlobalStatisticFragment

class TabLayoutAdapter(
    activity: FragmentActivity
) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> {
                return GlobalStatisticFragment()
            }
            2 -> {
                return CountryStatisticFragment()
            }
        }
        return GlobalStatisticFragment()
    }
}