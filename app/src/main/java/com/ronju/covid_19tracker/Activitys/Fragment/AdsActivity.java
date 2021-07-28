package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ronju.covid_19tracker.R;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.UnityServices;
import com.unity3d.services.monetization.UnityMonetization;

public class AdsActivity extends Fragment {
    private final String GAME_ID ="4218577";
    private final String INTERSTITIAL_ID="Interstitial_Android";
    private boolean testsMode=true;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ads,container,false);


        UnityAds.initialize(getActivity(),GAME_ID,testsMode);
        Button showAds = view.findViewById(R.id.showAdsBtn);
        showAds.setOnClickListener(v->{
            showingAds();
        });

        return view;
    }

    private void showingAds() {


        if(UnityAds.isReady())
        {
            UnityAds.show(getActivity());
        }else
        {
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    UnityAds.initialize(getActivity(),GAME_ID,testsMode);
                }
            },5000);
        }
    }

}