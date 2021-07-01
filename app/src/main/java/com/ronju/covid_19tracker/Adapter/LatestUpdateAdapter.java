package com.ronju.covid_19tracker.Adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ronju.covid_19tracker.R;


public class LatestUpdateAdapter extends RecyclerView.Adapter<LatestUpdateAdapter.ViewHolder> {
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_update_recycler_bg,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LatestUpdateAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
