package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ronju.covid_19tracker.R;

public class DashboardActivity extends Fragment {
    private Button clickHereForWork;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard,container,false);
//        clickHereForWork = view.findViewById(R.id.clickHereWork);
//        clickHereForWork.setOnClickListener(v->{
//
//            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragmentViewer,new AdsActivity()).commit();
//
//        });



        return view;
    }
}