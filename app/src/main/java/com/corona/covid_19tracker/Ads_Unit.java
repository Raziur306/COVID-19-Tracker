package com.corona.covid_19tracker;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import com.corona.covid_19tracker.Activitys.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Ads_Unit {
    private Context context;
    private int adsShowCounter =0;
    public Ads_Unit(Context context) {
        this.context = context;
    }

    private final String adsUnitId = "ca-app-pub-1283407186797080/3578025695";

    public void showAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, adsUnitId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                if (interstitialAd != null && adsShowCounter < 3) {
                    interstitialAd.show((Activity) context);
                    ++adsShowCounter;
                }
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
//                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
//                    @Override
              //      public void run() {
                        showAds();
           //         }
             //   },30000);

            }
        });
    }

}
