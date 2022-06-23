package com.corona.covid_19tracker.Activity.Fragment

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.databinding.FragmentCountryStatisticBinding
import com.corona.covid_19tracker.databinding.FragmentGlobalStatisticBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class GlobalStatisticFragment(val bundle: Bundle) : Fragment() {
    private lateinit var binding: FragmentGlobalStatisticBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGlobalStatisticBinding.inflate(layoutInflater)
        binding.affected.text = bundle.getInt("GnewCase").toString()
        binding.recovered.text = bundle.getInt("GnewRecover").toString()
        binding.death.text = bundle.getInt("GnewDeath").toString()
        binding.active.text = bundle.getInt("Gactive").toString()
        binding.serious.text = bundle.getInt("Gserious").toString()
        showBar()
        return binding.root
    }

    private fun showBar() {
        val typeValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(R.attr.text_color, typeValue, true)
        binding.chart.isDragEnabled = true
        binding.chart.setScaleEnabled(false)
        val arrayList: ArrayList<BarEntry> = ArrayList()
        arrayList.add(BarEntry(1f, bundle.getInt("GnewCase").toFloat()))
        arrayList.add(BarEntry(2f, bundle.getInt("GnewDeath").toFloat()))
        arrayList.add(BarEntry(3f, bundle.getInt("GnewRecover").toFloat()))
        arrayList.add(BarEntry(4f, bundle.getInt("Gactive").toFloat()))
        arrayList.add(BarEntry(5f, bundle.getInt("Gserious").toFloat()))
        val dataSet = BarDataSet(arrayList, "Overview")
        val colorList = mutableListOf<Int>()
        colorList.add(0, Color.parseColor("#F3B252"))
        colorList.add(1, Color.RED)
        colorList.add(2, Color.parseColor("#4CAF50"))
        colorList.add(3, Color.parseColor("#2196F3"))
        colorList.add(4, Color.parseColor("#697CE6"))
        dataSet.colors = colorList
        dataSet.valueTextColor = typeValue.data
        dataSet.valueTextSize = 16f
        val barData = BarData(dataSet)
        binding.chart.data = barData
        binding.chart.description.text = bundle.getString("Date")
        binding.chart.axisLeft.textColor = typeValue.data
        binding.chart.axisRight.textColor = typeValue.data
        binding.chart.legend.textColor = typeValue.data
        binding.chart.xAxis.textColor = typeValue.data
        binding.chart.description.textColor = typeValue.data
        binding.chart.animateY(2000)
    }


}