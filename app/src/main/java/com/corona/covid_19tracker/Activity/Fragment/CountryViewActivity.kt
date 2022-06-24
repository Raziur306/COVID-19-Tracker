package com.corona.covid_19tracker.Activity.Fragment

import com.corona.covid_19tracker.Adapter.CountryAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.corona.covid_19tracker.Api.CountryDataRetrofitHelper
import com.corona.covid_19tracker.Api.CountryDataService
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.Repository.CountryRepository
import com.corona.covid_19tracker.Repository.Response
import com.corona.covid_19tracker.ViewModel.CountryViewModel
import com.corona.covid_19tracker.ViewModel.CountryViewModelFactory
import com.corona.covid_19tracker.databinding.ActivityCountryViewBinding
import com.corona.covid_19tracker.utils.AdsTask

class CountryViewActivity : Fragment() {

    private lateinit var binding: ActivityCountryViewBinding
    private var mAdapter: CountryAdapter? = null
    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCountryViewBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Choose your country"
        val countryDataService =
            CountryDataRetrofitHelper.getInstance().create(CountryDataService::class.java)
        val repository = CountryRepository(countryDataService)
        val viewModel = ViewModelProvider(
            requireActivity(),
            CountryViewModelFactory(repository)
        ).get(CountryViewModel::class.java)

        viewModel.result.observe(requireActivity(), Observer {
            when (it) {
                is Response.Loading -> {
                    binding.countryShimmer.visibility = View.VISIBLE
                    binding.countryShimmer.startShimmer()
                }
                is Response.Success -> {
                    binding.countryShimmer.startShimmer()
                    binding.countryShimmer.visibility = View.INVISIBLE
                    it.data?.let {
                        if (it.size != 0) {
                            setAdapter(it)
                        }
                    }
                }
                is Response.Error -> {
                    viewModel.getResult()
                    if (counter == 0)
                        Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()

                }
            }

        })



        binding.cSearch.setOnQueryTextListener(
            object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    mAdapter?.filter?.filter(newText)
                    return false
                }

            })
        AdsTask(requireContext()).setBannerAds(binding.adView)
        return binding.root
    }

    private fun setAdapter(countryDataModel: CountryDataModel) {
        binding.countryItemView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapter = CountryAdapter(countryDataModel)
        binding.countryItemView.adapter = mAdapter
        binding.countryItemView.scheduleLayoutAnimation()
    }
}