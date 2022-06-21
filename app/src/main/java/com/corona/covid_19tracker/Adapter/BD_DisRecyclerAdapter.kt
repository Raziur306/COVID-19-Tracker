package com.corona.covid_19tracker.Adapter

import android.content.res.Configuration
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.corona.covid_19tracker.Model.DistrictDataModel
import com.corona.covid_19tracker.databinding.BdRecylerItemBinding

class BD_DisRecyclerAdapter(val districtDataModel: DistrictDataModel) :
    RecyclerView.Adapter<BD_DisRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding =
            BdRecylerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.counter.text = (position + 1).toString()
        holder.binding.disNameBn.text = districtDataModel.features[position].properties.bnName
        holder.binding.disNameEng.text = districtDataModel.features[position].properties.name
        holder.binding.confirmedCase.text =
            districtDataModel.features[position].properties.confirmed.toString()

        if (holder.binding.root.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO) {
            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"))
            }
        }
    }

    override fun getItemCount(): Int {
        return districtDataModel.features.size
    }

    class ViewHolder(val binding: BdRecylerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}