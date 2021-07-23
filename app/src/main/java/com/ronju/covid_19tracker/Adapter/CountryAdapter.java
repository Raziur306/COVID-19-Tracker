package com.ronju.covid_19tracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ronju.covid_19tracker.Activitys.MainActivity;
import com.ronju.covid_19tracker.R;
import com.ronju.covid_19tracker.Model.WorldDataItem;
import java.util.ArrayList;
import java.util.Collection;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.allCountryData;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {
    private ArrayList<WorldDataItem> allCountry = new ArrayList<>(allCountryData);
    private ArrayList<WorldDataItem> countryList = new ArrayList<>(allCountryData);
    private Context context;

    public CountryAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_view_bg, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {
        holder.slNo.setText(String.valueOf(position + 1));
        holder.countryName.setText(countryList.get(position).getCountryName());
        Glide.with(context).load(countryList.get(position).getFlagUrl()).into(holder.cFlag);

        holder.itemView.setOnClickListener(v -> {

            //shared preference
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("covid-19_shp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("country_id", String.valueOf(countryList.get(position).getID()));
            editor.apply();
            editor.commit();


            //start home activity
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);

//            HomeActivity fragment = new HomeActivity();
//            MainActivity mainActivity = (MainActivity)v.getContext();
//            mainActivity.getFragmentManager().beginTransaction().replace(R.id.fragmentViewer, fragment).commit();


            ((Activity) context).finish();
        });
    }


    @Override
    public int getItemCount() {
        return countryList.size();
    }


    //filtering
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<WorldDataItem> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(allCountry);
            } else {
                for (WorldDataItem c : allCountry) {
                    if (c.getCountryName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(c);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countryList.clear();
            countryList.addAll((Collection<? extends WorldDataItem>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView slNo, countryName;
        ImageView cFlag;

        public ViewHolder(View itemView) {
            super(itemView);
            slNo = itemView.findViewById(R.id.countrySL);
            countryName = itemView.findViewById(R.id.recyclerCountryName);
            cFlag = itemView.findViewById(R.id.recyclerFlag);
        }
    }
}
