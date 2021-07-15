package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ronju.covid_19tracker.Adapter.LatestUpdateAdapter;
import com.ronju.covid_19tracker.DoInBackground.doInBackground;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.Model.WorldDataItem;
import com.ronju.covid_19tracker.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static com.ronju.covid_19tracker.DoInBackground.doInBackground.allCountryData;
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
    private TextView globalAffected, globalNewAffected, globalDeath, globalNewDeath, globalRecovered, globalNewRecovered, countryAffected, countryNewAffected, countryName, countryDeath, countryNewDeath, countryNewRecovered, countryRecovered, changeCountry, date1, date2, date3;
    private SharedPreferences sharedPreferences;
    LoadingDialog loadingDialog;
    RecyclerView latestNewsRecycler;
    LatestUpdateAdapter mAdapter;
    ImageView countryFlag;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        //showing toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();



        //loading dialog
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.setCancelable(false);
        loadingDialog.setTitle("Loading....");
        loadingDialog.show();

        //initialize view
        initView(view);

//        ElasticityHelper.setUpOverScroll(scrollView);


        //shared preference
        sharedPreferences = getActivity().getSharedPreferences("covid-19_shp", Context.MODE_PRIVATE);


        changeCountry.setOnClickListener(v -> {
           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer,new CountryViewActivity()).commit();
        });

        service();
        if (!responseFlag1) {
            setValue();

        }
        return view;
    }


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

//latest news adapter
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            latestNewsRecycler.setLayoutManager(llm);
            latestNewsRecycler.setHasFixedSize(true);
            mAdapter = new LatestUpdateAdapter();
            latestNewsRecycler.setAdapter(mAdapter);

            //global item
            globalAffected.setText(String.valueOf(gAffected));
            globalNewAffected.setText(String.valueOf(gNewAffected));
            globalDeath.setText(String.valueOf(gDeath));
            globalNewDeath.setText(String.valueOf(gNewDeath));
            globalNewRecovered.setText(String.valueOf(gNewRecovered));
            globalRecovered.setText(String.valueOf(gRecovered));






        //selected country item

        String countryId = sharedPreferences.getString("country_id", "50");
        for (WorldDataItem cItem : allCountryData) {
            if (cItem.getID() == Integer.parseInt(countryId)) {
                countryName.setText(cItem.getCountryName());
                countryAffected.setText(String.valueOf(cItem.getTotalCases()));
                countryNewAffected.setText(String.valueOf(cItem.getTodayCases()));
                countryRecovered.setText(String.valueOf(cItem.getTotalRecovered()));
                countryNewRecovered.setText(String.valueOf(cItem.getTodayRecovered()));
                countryDeath.setText(String.valueOf(cItem.getTotalDeaths()));
                countryNewDeath.setText(String.valueOf(cItem.getTodayDeaths()));
                Glide.with(getContext()).load(cItem.getFlagUrl()).into(countryFlag);
                Calendar calendar = setUpdatedTime(cItem.getUpdateTime());

                date1.setText(new SimpleDateFormat("dd MMM, yyyy, hh:mm a").format(calendar.getTime()));
                date2.setText(new SimpleDateFormat("dd MMM, yyyy, hh:mm a").format(calendar.getTime()));
                date3.setText(new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime()));

                loadingDialog.dismiss();
                break;
            }
        }
    }
    private Calendar setUpdatedTime(long updateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(updateTime);
        return calendar;

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
        countryRecovered = view.findViewById(R.id.country_recovered);
        countryNewRecovered = view.findViewById(R.id.country_new_recovered);
        countryFlag = view.findViewById(R.id.country_flag_img);

        //others
        date1 = view.findViewById(R.id.update_date1);
        date2 = view.findViewById(R.id.update_date2);
        date3 = view.findViewById(R.id.update_date3);
        changeCountry = view.findViewById(R.id.country_chooser);
        latestNewsRecycler = view.findViewById(R.id.latestNewsRecycler);
    }


}
