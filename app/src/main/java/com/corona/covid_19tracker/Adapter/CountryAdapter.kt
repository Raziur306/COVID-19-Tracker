package com.corona.covid_19tracker.Adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import android.content.Intent
import android.app.Activity
import android.annotation.SuppressLint
import android.view.View
import android.widget.Filter
import com.corona.covid_19tracker.Activity.MainActivity
import com.corona.covid_19tracker.Encryption.Encrypter
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.Model.CountryDataModelItem
import com.corona.covid_19tracker.databinding.CountryViewBgBinding
import java.util.*
import kotlin.collections.ArrayList

class CountryAdapter(private val allCountry: CountryDataModel) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private val countryList: ArrayList<CountryDataModelItem> = ArrayList(allCountry)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CountryViewBgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.countrySL.text = (position + 1).toString() + "."
        holder.binding.recyclerCountryName.text = countryList[position].country
        Glide.with(holder.binding.root.context).load(countryList[position].countryInfo.flag)
            .into(holder.binding.recyclerFlag)
        holder.itemView.setOnClickListener { v: View ->
            (v.context.applicationContext as Encrypter).setDefaultCountry(countryList[position].countryInfo._id)

//start home activity
            val intent = Intent(v.context, MainActivity::class.java)
            v.context.startActivity(intent)
            (v.context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }


    class ViewHolder(val binding: CountryViewBgBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

 //filtering
    var filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: ArrayList<CountryDataModelItem> = ArrayList()
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(allCountry)
            } else {
                for (c in allCountry) {
                    if (c.country.lowercase(Locale.getDefault())
                            .contains(constraint.toString().lowercase(Locale.getDefault()))
                    ) {
                        filteredList.add(c)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        @SuppressLint("NotifyDataSetChanged")
        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            countryList.clear()
            countryList.addAll(results.values as Collection<CountryDataModelItem>)
            notifyDataSetChanged()
        }
    }

}