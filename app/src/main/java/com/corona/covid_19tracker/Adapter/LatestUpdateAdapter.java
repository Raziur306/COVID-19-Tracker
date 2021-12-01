package com.corona.covid_19tracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.corona.covid_19tracker.Model.WorldDataItem;
import com.corona.covid_19tracker.R;

import java.util.ArrayList;
import static android.view.View.GONE;
import static com.corona.covid_19tracker.DoInBackground.doInBackground.allCountryData;


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

        //on item clicked
        holder.itemView.setOnClickListener(v->{
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(),R.style.bottomSheetTheme);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);



            TextView countryName = bottomSheetDialog.findViewById(R.id.bottomSheet_countryName);
            ImageView countryFlag = bottomSheetDialog.findViewById(R.id.bottomSheetCountry_flag);
            TextView affected = bottomSheetDialog.findViewById(R.id.bottomSheet_country_affected);
            TextView newAffected = bottomSheetDialog.findViewById(R.id.bottomSheet_country_new_affected);
            TextView death = bottomSheetDialog.findViewById(R.id.bottomSheet_country_death);
            TextView newDeath = bottomSheetDialog.findViewById(R.id.bottomSheet_country_new_death);
            TextView recovered = bottomSheetDialog.findViewById(R.id.bottomSheet_country_recovered);
            TextView newRecovered = bottomSheetDialog.findViewById(R.id.bottomSheet_country_new_recovered);
            TextView tests = bottomSheetDialog.findViewById(R.id.bottomSheet_tests);
            TextView population = bottomSheetDialog.findViewById(R.id.bottomSheet_population);

            countryName.setText(latestData.get(position).getCountryName());
            Glide.with(v.getContext()).load(latestData.get(position).getFlagUrl()).into(countryFlag);
            affected.setText(String.valueOf(latestData.get(position).getTotalCases()));
            newAffected.setText(String.valueOf(latestData.get(position).getTodayCases()));
            death.setText(String.valueOf(latestData.get(position).getTotalDeaths()));
            newDeath.setText(String.valueOf(latestData.get(position).getTodayDeaths()));
            recovered.setText(String.valueOf(latestData.get(position).getTotalRecovered()));
            newRecovered.setText(String.valueOf(latestData.get(position).getTodayRecovered()));
            tests.setText(String.valueOf(latestData.get(position).getTests()));
            population.setText(String.valueOf(latestData.get(position).getTotalPopulation()));

            bottomSheetDialog.show();

        });
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
