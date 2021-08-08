package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;
import com.unity3d.ads.UnityAds;

import org.w3c.dom.Text;

public class AdsActivity extends Fragment {
    private final String GAME_ID ="4218577";
    private final String INTERSTITIAL_ID="Interstitial_Android";
    Dialog unitDialog;
    private boolean testsMode=true;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ads,container,false);
        unitDialog = LoadingDialog.getUnitDialog(getContext());
       TextView textView =  unitDialog.findViewById(R.id.ads_dialog_text);
       unitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ((TextView)unitDialog.findViewById(R.id.ads_dialog_text)).setText("Ads seen successfully");

       unitDialog.findViewById(R.id.ads_dialog_cancel_btn).setOnClickListener(v->{
           unitDialog.dismiss();
       });


        UnityAds.initialize(getActivity(),GAME_ID,testsMode);
        Button showAds = view.findViewById(R.id.server_1_btn);
        showAds.setOnClickListener(v->{




            unitDialog.show();


            //showingAds();
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