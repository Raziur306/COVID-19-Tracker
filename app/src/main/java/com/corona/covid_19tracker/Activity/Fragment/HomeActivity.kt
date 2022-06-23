package com.corona.covid_19tracker.Activity.Fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.corona.covid_19tracker.Adapter.LatestUpdateAdapter
import com.corona.covid_19tracker.Api.CountryDataRetrofitHelper
import com.corona.covid_19tracker.Api.CountryDataService
import com.corona.covid_19tracker.Encryption.Encrypter
import com.corona.covid_19tracker.Model.CountryDataModel
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.Repository.CountryRepository
import com.corona.covid_19tracker.Repository.Response
import com.corona.covid_19tracker.ViewModel.HomeFragmentViewModel
import com.corona.covid_19tracker.ViewModel.HomeFragmentViewModelFactory
import com.corona.covid_19tracker.databinding.ActivityHomeBinding
import com.corona.covid_19tracker.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : Fragment() {
    private var counter = 0
    private val statisticBundle = Bundle()
    private lateinit var binding: ActivityHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        //showing toolbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding.countryChooser.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentViewer, CountryViewActivity(), "country_view_fragment")
                ?.addToBackStack(null)?.commit();
        };


        val countryDataService =
            CountryDataRetrofitHelper.getInstance().create(CountryDataService::class.java)
        val repository = CountryRepository(countryDataService)
        val viewModel =
            ViewModelProvider(requireActivity(), HomeFragmentViewModelFactory(repository)).get(
                HomeFragmentViewModel::class.java
            )
        viewModel.result.observe(requireActivity(), androidx.lifecycle.Observer {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.let {
                        if (it.size != 0) {
                            setValue(it)
                            CoroutineScope(Dispatchers.IO).launch()
                            {
                                totalValue(it)
                            }
                            setLatestNewsAdapter(it)
                        }
                    }
                }
                is Response.Error -> {
                    viewModel.getData()
                    if (counter == 0)
                        Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                    counter++
                }
            }
        })



        binding.countryDBoard.setOnClickListener {
            statisticBundle.putInt("value", 1)
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragmentViewer, StatisticsFragment(statisticBundle), "statistics"
            )?.addToBackStack(null)?.commit()
        }
        binding.globalDBoard.setOnClickListener {
            statisticBundle.putInt("value", 0)
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragmentViewer, StatisticsFragment(statisticBundle), "statistics"
            )?.addToBackStack(null)?.commit()
        }
        return binding.root
    }

    private fun setValue(value: CountryDataModel) {
        var countryId: Int = 50
        try {
            countryId = (requireActivity().application as Encrypter).getDefaultCountry()!!
        } catch (e: Exception) {
        }
        (0 until value.size).forEach {
            if (value[it].countryInfo._id == countryId) {
                context?.let { it1 ->
                    Glide.with(it1.applicationContext).load(value[it].countryInfo.flag)
                        .into(binding.countryFlagImg)
                }

                statisticBundle.putInt("CnewCase", value[it].todayCases)
                statisticBundle.putInt("CnewDeath", value[it].todayDeaths)
                statisticBundle.putInt("CnewRecover", value[it].todayRecovered)
                statisticBundle.putInt("Cactive", value[it].active)
                statisticBundle.putInt("Cserious", value[it].critical)

                binding.countryName.text = value[it].country
                binding.countryAffected.text = value[it].cases.toString()
                binding.countryRecovered.text = value[it].recovered.toString()
                binding.countryDeath.text = value[it].deaths.toString()
                binding.countryNewAffected.text = value[it].todayCases.toString()
                binding.countryNewDeath.text = value[it].todayDeaths.toString()
                binding.countryNewRecovered.text = value[it].todayRecovered.toString()
                val calendar = setUpdatedTime(value[it].updated)
                binding.updateDate1.text =
                    SimpleDateFormat("dd MMM, yyyy, hh:mm a").format(calendar.time).toString()
                statisticBundle.putString(
                    "Date",
                    SimpleDateFormat("dd MMM, yyyy, hh:mm a").format(calendar.time).toString()
                )
                binding.updateDate2.text =
                    SimpleDateFormat("dd MMM, yyyy, hh:mm a").format(calendar.time).toString()
                binding.updateDate3.text =
                    SimpleDateFormat("dd MMM, yyyy").format(calendar.time).toString()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.INVISIBLE
                binding.homeScrollView.visibility = View.VISIBLE


                return
            }
        }
    }

    private fun totalValue(value: CountryDataModel) {
        var totalNewCase = 0
        var totalNewDeath = 0
        var totalNewRecover = 0
        var totalCase = 0;
        var totalDeath = 0
        var totalRecover = 0
        var critical = 0
        var active = 0
        (0 until value.size).forEach {
            totalNewCase += value[it].todayCases
            totalNewDeath += value[it].todayDeaths
            totalNewRecover += value[it].todayRecovered
            totalCase += value[it].cases
            totalDeath += value[it].deaths
            totalRecover += value[it].recovered
            critical += value[it].critical
            active += value[it].active
        }
        statisticBundle.putInt("GnewCase", totalNewCase)
        statisticBundle.putInt("GnewDeath", totalNewDeath)
        statisticBundle.putInt("GnewRecover", totalNewRecover)
        statisticBundle.putInt("Gactive", active)
        statisticBundle.putInt("Gserious", critical)
        setGlobal(totalNewCase, totalNewDeath, totalNewRecover, totalCase, totalDeath, totalRecover)
    }

    private fun setGlobal(
        totalNewCase: Int,
        totalNewDeath: Int,
        totalNewRecover: Int,
        totalCase: Int,
        totalDeath: Int,
        totalRecover: Int
    ) {
        binding.globalAffected.text = totalCase.toString()
        binding.globalNewAffected.text = totalNewCase.toString()
        binding.globalDeath.text = totalDeath.toString()
        binding.globalNewDeath.text = totalNewDeath.toString()
        binding.globalRecovered.text = totalRecover.toString()
        binding.globalNewRecovered.text = totalNewRecover.toString()
    }

    private fun setLatestNewsAdapter(it: CountryDataModel) {
        binding.latestNewsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.latestNewsRecycler.setHasFixedSize(true)
        binding.latestNewsRecycler.adapter = LatestUpdateAdapter(it)
    }

    private fun setUpdatedTime(updateTime: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = updateTime
        return calendar
    }

}