package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.ronju.covid_19tracker.Activitys.CountryViewActivity;
import com.ronju.covid_19tracker.DoInBackground.doInBackground;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.PieChartClass;
import com.ronju.covid_19tracker.R;


import java.util.ArrayList;

import static com.ronju.covid_19tracker.DoInBackground.doInBackground.gAffected;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.gDeath;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.gNewAffected;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.gNewDeath;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.gNewRecovered;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.gRecovered;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.responseFlag1;

public class HomeActivity extends Fragment {
    private boolean flag = true;
    private View view = null;
    private TextView globalAffected, globalNewAffected, globalDeath, globalNewDeath, globalRecovered, globalNewRecovered, countryAffected, countryNewAffected, countryName, countryDeath, countryNewDeath, changeCountry, date1, date2, date3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        initView(view);


        changeCountry.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CountryViewActivity.class);
            startActivity(intent);
        });

        service();
        if (!responseFlag1) {
            setValue();
        }
        return view;
    }
//
//    private void clickedItem() {
//        entries = new ArrayList<>();

//        for (WorldDataItem cItem : worldData) {
//            if (cItem.getID() == ID) {
//                entries.add(new PieEntry(cItem.getTotalCases(), "Affected"));
//                entries.add(new PieEntry(cItem.getTotalRecovered(), "Recovered"));
//                entries.add(new PieEntry(cItem.getActive(), "Active Case"));
//                entries.add(new PieEntry(cItem.getTotalDeaths(), "Death"));
//
//                countryName.setText(cItem.getCountryName());
//
//                totalAffected.setText(String.valueOf(cItem.getTotalCases()));
//                todayAffected.setText("(+" + cItem.getTodayCases() + ")");
//
//                totalRecovered.setText(String.valueOf(cItem.getTotalRecovered()));
//                todayRecovered.setText("(+" + cItem.getTodayRecovered() + ")");
//
//
//                totalDeaths.setText(String.valueOf(cItem.getTotalDeaths()));
//                todayDeaths.setText("(+" + cItem.getTodayDeaths() + ")");
//
//
//                totalTest.setText(String.valueOf(cItem.getTests()));
//
//                totalActiveCase.setText(String.valueOf(cItem.getActive()));
//                todayActiveCase.setText("(+" + cItem.getTodayCases() + ")");
//
//
//                population.setText(String.valueOf(cItem.getTotalPopulation()));
//
//                setUpdatedTime(cItem.getUpdateTime());
//
//                break;
//            }
//        }

//        pieChartClass.loadPieChartData(pieChart, entries);

    // }

//    private void setUpdatedTime(long updateTime) {
//        DateFormat dateFormat = new SimpleDateFormat("dd  MMM, yyyy");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(updateTime);
//        updated.setText("Last updated at " + dateFormat.format(calendar.getTime()));


    //   }

    //getting world data
    private void service() {
        if (responseFlag1) {
            doInBackground countryData = new doInBackground(getContext());
            countryData.CountryDataService(new doInBackground.VolleyResponseListener() {
                @Override
                public void onResponse() {
                    responseFlag1 = false;
                    flag = true;
                    setValue();
                }

                @Override
                public void onErrorResponse() {
                    if (flag) {
                        Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                    service();
                }
            });
        }
    }

    public void setValue() {
        globalAffected.setText(String.valueOf(gAffected));
        globalNewAffected.setText(String.valueOf(gNewAffected));
        globalDeath.setText(String.valueOf(gDeath));
        globalNewDeath.setText(String.valueOf(gNewDeath));
        globalNewRecovered.setText(String.valueOf(gNewRecovered));
        globalRecovered.setText(String.valueOf(gRecovered));
    }


    //initializing the views
    private void initView(View view) {
        //global section
        globalAffected = view.findViewById(R.id.global_affected);
        globalNewAffected = view.findViewById(R.id.global_new_affected);
        globalDeath = view.findViewById(R.id.global_death);
        globalNewDeath = view.findViewById(R.id.global_new_death);
        globalRecovered = view.findViewById(R.id.global_recovered);
        globalNewRecovered = view.findViewById(R.id.global_new_recovered);

        //country section
        countryName = view.findViewById(R.id.country_name);
        countryAffected = view.findViewById(R.id.country_affected);
        countryNewAffected = view.findViewById(R.id.country_new_affected);
        countryDeath = view.findViewById(R.id.country_death);
        countryNewDeath = view.findViewById(R.id.country_new_death);


        //others
        date1 = view.findViewById(R.id.update_date1);
        date2 = view.findViewById(R.id.update_date2);
        date3 = view.findViewById(R.id.update_date3);
        changeCountry = view.findViewById(R.id.country_chooser);
    }


}
