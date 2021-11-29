package com.corona.covid_19tracker.Activitys.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import com.corona.covid_19tracker.R;

public class PreventionActivity extends Fragment {

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_prevention,container,false);
        return view;
    }
}