package com.corona.covid_19tracker.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.Model.CountryDataModelItem
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.databinding.BottomSheetLayoutBinding
import com.corona.covid_19tracker.databinding.LatestUpdateRecyclerBgBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.Int

class LatestUpdateAdapter(private val data: CountryDataModel) :
    RecyclerView.Adapter<LatestUpdateAdapter.ViewHolder>() {
    private var filteredData = ArrayList<CountryDataModelItem>()

    init {
        (0 until data.size).forEach {
            if (data[it].todayCases > 0) {
                filteredData.add(data[it])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LatestUpdateRecyclerBgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (filteredData[position].todayCases > 0) {

            holder.binding.latestNewsNewCase.text = filteredData[position].todayCases.toString()
            holder.binding.latestNewsCountryName.text = filteredData[position].country
            if (filteredData[position].todayDeaths > 0) {
                holder.binding.latestNewsDeathCase.text =
                    filteredData[position].todayDeaths.toString()
            } else {
                holder.binding.latestNewsDeathCase.visibility = View.GONE
                holder.binding.temp1.visibility = View.GONE
                holder.binding.temp2.visibility = View.GONE
            }
        }

//on item clicked
        holder.itemView.setOnClickListener { v: View ->
            val bottomSheetBinding =
                BottomSheetLayoutBinding.inflate(LayoutInflater.from(v.context))
            val bottomSheetDialog = BottomSheetDialog(v.context, R.style.bottomSheetTheme)
            bottomSheetDialog.setContentView(bottomSheetBinding.root)
            bottomSheetBinding.bottomSheetCountryName.text = filteredData[position].country
            Glide.with(v.context).load(filteredData[position].countryInfo.flag)
                .into(bottomSheetBinding.bottomSheetCountryFlag)
            bottomSheetBinding.bottomSheetCountryAffected.text =
                filteredData[position].cases.toString()
            bottomSheetBinding.bottomSheetCountryNewAffected.text =
                filteredData[position].todayCases.toString()
            bottomSheetBinding.bottomSheetCountryDeath.text =
                filteredData[position].deaths.toString()
            bottomSheetBinding.bottomSheetCountryNewDeath.text =
                filteredData[position].todayDeaths.toString()
            bottomSheetBinding.bottomSheetCountryRecovered.text =
                filteredData[position].recovered.toString()
            bottomSheetBinding.bottomSheetCountryNewRecovered.text =
                filteredData[position].todayRecovered.toString()
            bottomSheetBinding.bottomSheetTests.text = filteredData[position].tests.toString()
            bottomSheetBinding.bottomSheetPopulation.text =
                filteredData[position].population.toString()
            bottomSheetDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    class ViewHolder(val binding: LatestUpdateRecyclerBgBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}