package com.ronju.covid_19tracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ronju.covid_19tracker.Model.WorldDataItem;
import com.ronju.covid_19tracker.R;
import java.util.ArrayList;
import static android.view.View.GONE;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.allCountryData;


public class LatestUpdateAdapter extends RecyclerView.Adapter<LatestUpdateAdapter.ViewHolder> {
    private View view;
    private ArrayList<WorldDataItem> latestData = new ArrayList<>();
    public LatestUpdateAdapter()
    {
        for(WorldDataItem item: allCountryData)
        {
            if(item.getTodayCases()> 0)
            {
                latestData.add(item);
            }
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_update_recycler_bg,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LatestUpdateAdapter.ViewHolder holder, int position) {
        if(latestData.get(position).getTodayCases()>0)
        {
            holder.latestNewsNewCase.setText(String.valueOf(latestData.get(position).getTodayCases()));
            holder.latestNewsCountryName.setText(latestData.get(position).getCountryName());
            if(latestData.get(position).getTodayDeaths()>0)
            {
                holder.latestNewsDeath.setText(String.valueOf(latestData.get(position).getTodayDeaths()));
            }
            else
            {
                holder.latestNewsDeath.setVisibility(GONE);
                holder.temp_1.setVisibility(GONE);
                holder.temp_2.setVisibility(GONE);
            }
        }
    }

    @Override
    public int getItemCount() {

        return latestData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView latestNewsNewCase, temp_1, temp_2, latestNewsDeath, latestNewsCountryName;

        public ViewHolder(View itemView) {
            super(itemView);

            latestNewsNewCase = itemView.findViewById(R.id.latestNews_newCase);
            temp_1 = itemView.findViewById(R.id.temp_1);
            temp_2 = itemView.findViewById(R.id.temp_2);
            latestNewsDeath = itemView.findViewById(R.id.latestNews_deathCase);
            latestNewsCountryName = itemView.findViewById(R.id.latestNews_countryName);
        }
    }
}
