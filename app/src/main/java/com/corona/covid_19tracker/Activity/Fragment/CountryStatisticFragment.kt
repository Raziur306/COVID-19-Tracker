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
import com.github.mikephil.charting.data.*


class CountryStatisticFragment() : Fragment() {
    private lateinit var binding: FragmentCountryStatisticBinding
    private lateinit var bundle: Bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments.let {
            if (it != null) {
                bundle = it
            }
        }

        binding = FragmentCountryStatisticBinding.inflate(layoutInflater)
        binding.affected.text = bundle.getInt("CnewCase").toString()
        binding.recovered.text = bundle.getInt("CnewRecover").toString()
        binding.death.text = bundle.getInt("CnewDeath").toString()
        binding.active.text = bundle.getInt("Cactive").toString()
        binding.serious.text = bundle.getInt("Cserious").toString()
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
        arrayList.add(BarEntry(1f, bundle.getInt("CnewCase").toFloat()))
        arrayList.add(BarEntry(2f, bundle.getInt("CnewDeath").toFloat()))
        arrayList.add(BarEntry(3f, bundle.getInt("CnewRecover").toFloat()))
        arrayList.add(BarEntry(4f, bundle.getInt("Cactive").toFloat()))
        arrayList.add(BarEntry(5f, bundle.getInt("Cserious").toFloat()))
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