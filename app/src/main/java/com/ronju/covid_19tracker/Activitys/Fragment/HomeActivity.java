package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.ronju.covid_19tracker.Activitys.CountryViewActivity;
import com.ronju.covid_19tracker.Activitys.MainActivity;
import com.ronju.covid_19tracker.DoInBackground.doInBackground;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.PieChartClass;
import com.ronju.covid_19tracker.R;
import com.ronju.covid_19tracker.Model.WorldDataItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends Fragment {
    private boolean flag = true;
    private int i = 0;
    private LoadingDialog loadingDialog;
    private String apiUrl = "https://corona.lmao.ninja/v2/countries";
    public static ArrayList<WorldDataItem> worldData;
    private ArrayList<PieEntry> entries;
    private PieChart pieChart;
    private long ID;
    private View view = null;
    private PieChartClass pieChartClass;
    private TextView population, totalAffected, todayAffected, totalRecovered, todayRecovered, totalActiveCase, todayActiveCase, totalDeaths, todayDeaths, totalTest, updated, countryName;

    public  HomeActivity()
    {}

    public HomeActivity(long ID) {
        this.ID = ID;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_home, container, false);



        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();


        //view initializing views
        initView(view);
        //getting data from api
        service();

        //home pie chart
        pieChart = view.findViewById(R.id.pieChart);
        pieChartClass = new PieChartClass(getContext());
        pieChartClass.setupDataChart(pieChart);


        //click country
        countryName.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CountryViewActivity.class);
            startActivity(intent);

        });


        return view;
    }

    private void clickedItem() {
        entries = new ArrayList<>();

        for (WorldDataItem cItem : worldData) {
            if (cItem.getID() == ID) {
                entries.add(new PieEntry(cItem.getTotalCases(), "Affected"));
                entries.add(new PieEntry(cItem.getTotalRecovered(), "Recovered"));
                entries.add(new PieEntry(cItem.getActive(), "Active Case"));
                entries.add(new PieEntry(cItem.getTotalDeaths(), "Death"));

                countryName.setText(cItem.getCountryName());

                totalAffected.setText(String.valueOf(cItem.getTotalCases()));
                todayAffected.setText("(+" + cItem.getTodayCases() + ")");

                totalRecovered.setText(String.valueOf(cItem.getTotalRecovered()));
                todayRecovered.setText("(+" + cItem.getTodayRecovered() + ")");


                totalDeaths.setText(String.valueOf(cItem.getTotalDeaths()));
                todayDeaths.setText("(+" + cItem.getTodayDeaths() + ")");


                totalTest.setText(String.valueOf(cItem.getTests()));

                totalActiveCase.setText(String.valueOf(cItem.getActive()));
                todayActiveCase.setText("(+" + cItem.getTodayCases() + ")");


                population.setText(String.valueOf(cItem.getTotalPopulation()));

                setUpdatedTime(cItem.getUpdateTime());

                break;
            }
        }

        pieChartClass.loadPieChartData(pieChart, entries);

    }

    private void setUpdatedTime(long updateTime) {
        DateFormat dateFormat = new SimpleDateFormat("dd  MMM, yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(updateTime);
        updated.setText("Last updated at " + dateFormat.format(calendar.getTime()));


    }

    //getting world data
    private void service() {
        doInBackground countryData = new doInBackground(getContext());
        worldData = new ArrayList<>();
        countryData.CountryDataService(new doInBackground.VolleyResponseListener() {
            @Override
            public void onResponse(ArrayList<WorldDataItem> wData) {

                worldData = wData;

                loadingDialog.dismiss();
                flag = true;
                clickedItem();
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


    //initializing the views
    private void initView(View view) {

        //show data
        totalAffected = view.findViewById(R.id.totalAffected);
        todayAffected = view.findViewById(R.id.todayAffeced);
        totalRecovered = view.findViewById(R.id.totalRecovered);
        todayRecovered = view.findViewById(R.id.todayRecovered);
        totalDeaths = view.findViewById(R.id.totalDeaths);
        todayDeaths = view.findViewById(R.id.todayDeaths);
        totalTest = view.findViewById(R.id.totalTests);
        totalActiveCase = view.findViewById(R.id.activeCase);
        todayActiveCase = view.findViewById(R.id.todayNewActiveCase);
        population = view.findViewById(R.id.population);
        updated = view.findViewById(R.id.updated);
        countryName = view.findViewById(R.id.countryName);
    }


}
