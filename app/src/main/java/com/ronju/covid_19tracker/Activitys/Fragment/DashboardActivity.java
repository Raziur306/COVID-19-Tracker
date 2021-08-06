package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ronju.covid_19tracker.R;

public class DashboardActivity extends Fragment {
    private Button clickHereForWork;
    FloatingActionButton fab;
    LinearLayout helpDesk, feedBack,watchVideo;
    boolean isFabOpen = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        initView(view);
        fab.setOnClickListener(v -> {
            if (isFabOpen) {
                closeFab();
            } else {
                openFab();
            }
        });


        watchVideo.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragmentViewer,new AdsActivity()).commit();
        });

        return view;
    }

    private void openFab() {
        helpDesk.setVisibility(View.VISIBLE);
        feedBack.setVisibility(View.VISIBLE);
        isFabOpen = true;
        ViewCompat.animate(fab).rotation(45.F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        helpDesk.animate().translationY(-getResources().getDimension(R.dimen.standard_21));
        feedBack.animate().translationY(-getResources().getDimension(R.dimen.standard_30));


    }

    private void closeFab() {
        isFabOpen = false;
        ViewCompat.animate(fab).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        helpDesk.animate().translationY(getResources().getDimension(R.dimen.standard_21));
        feedBack.animate().translationY(getResources().getDimension(R.dimen.standard_30));
        helpDesk.setVisibility(View.GONE);
        feedBack.setVisibility(View.GONE);
    }

    private void initView(View view) {
        fab = view.findViewById(R.id.main_fab);
        helpDesk = view.findViewById(R.id.helpDesk_fab);
        feedBack = view.findViewById(R.id.feedback_fab);
        watchVideo = view.findViewById(R.id.watch_video);
    }
}