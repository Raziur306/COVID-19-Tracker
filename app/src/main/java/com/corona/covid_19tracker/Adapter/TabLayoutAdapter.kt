package com.corona.covid_19tracker.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corona.covid_19tracker.Activity.Fragment.CountryStatisticFragment
import com.corona.covid_19tracker.Activity.Fragment.GlobalStatisticFragment

class TabLayoutAdapter(
    activity: FragmentActivity,
    val arg: Bundle
) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return GlobalStatisticFragment().apply {
                    arguments = arg
                }
            }
            1 -> {
                return CountryStatisticFragment().apply {
                    arguments = arg
                }
            }
        }
        return GlobalStatisticFragment().apply {
            arguments = arg
        }
    }
}