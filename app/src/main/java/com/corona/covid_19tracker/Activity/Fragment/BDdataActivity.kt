package com.corona.covid_19tracker.Activity.Fragment

import android.app.Dialog
import androidx.recyclerview.widget.RecyclerView
import com.corona.covid_19tracker.Adapter.BD_DisRecyclerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.corona.covid_19tracker.Api.BdDataRetrofitHelper
import com.corona.covid_19tracker.Api.DistrictDataService
import com.corona.covid_19tracker.LoadingDialog
import com.corona.covid_19tracker.Model.DistrictDataModel
import com.corona.covid_19tracker.Repository.BDdataRepository
import com.corona.covid_19tracker.Repository.Response
import com.corona.covid_19tracker.ViewModel.BDdataActivityViewModel
import com.corona.covid_19tracker.ViewModel.BDdataActivityViewModelFactory
import com.corona.covid_19tracker.databinding.ActivityBddataBinding

class BDdataActivity : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var loadingDialog: Dialog? = null
    private var mAdapter: BD_DisRecyclerAdapter? = null
    private val flag = true
    private lateinit var binding: ActivityBddataBinding
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var counter = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityBddataBinding.inflate(layoutInflater)
        binding.shimmerViewContainer.stopShimmer()

        val BdStateDataService =
            BdDataRetrofitHelper.getInstance().create(DistrictDataService::class.java)
        val repository = BDdataRepository(BdStateDataService)
        val viewModel =
            ViewModelProvider(requireActivity(), BDdataActivityViewModelFactory(repository)).get(
                BDdataActivityViewModel::class.java
            )
        viewModel.result.observe(requireActivity(), Observer {
            it.let {
                when (it) {
                    is Response.Loading -> {

                    }
                    is Response.Success -> {
                        setData(it.data!!)
                    }
                    is Response.Error -> {
                        viewModel.getResult()
                        if (counter == 1)
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        viewModel.getResult()
                        ++counter
                    }
                }
            }
        })

        return binding.root
    }

    private fun setData(districtDataModel: DistrictDataModel) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.bdRecycler.layoutManager = llm
        binding.bdRecycler.setHasFixedSize(true)
        binding.bdRecycler.adapter = BD_DisRecyclerAdapter(districtDataModel)
        binding.bdRecycler.scheduleLayoutAnimation()
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.INVISIBLE
    }


}