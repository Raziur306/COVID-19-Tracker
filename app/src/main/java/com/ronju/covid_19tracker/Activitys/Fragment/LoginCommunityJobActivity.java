package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ronju.covid_19tracker.R;

public class LoginCommunityJobActivity extends Fragment {
    private View view = null;
    private Button adsShowingButton;
    private RewardedAd mRewardedAd;
    int i = 1;
    AdView adView;
    AdRequest adRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_community_job_login, container, false);
        adsShowingButton = view.findViewById(R.id.view_ads);






        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = view.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());








        adsShowingButton.setText("Preparing");
        adsShowingButton.setClickable(false);


        //loading ads unit
        adRequest = new AdRequest.Builder().build();
        loadingAds();


        adsShowingButton.setOnClickListener(v -> {
            showAds();
        });


        return view;
    }


    private void loadingAds() {
        RewardedAd.load(getContext(), "ca-app-pub-1283407186797080/9979032147", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                mRewardedAd = null;
                loadingAds();
                Toast.makeText(getContext(), "Fail to load ads" + i, Toast.LENGTH_SHORT).show();
                i++;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
                adsShowingButton.setClickable(true);
                adsShowingButton.setText("Click to show");
            }
        });
    }


    private void showAds() {
        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Toast.makeText(getContext(), "Ads Seen Successfully", Toast.LENGTH_SHORT).show();
                mRewardedAd = null;
            }
        });


        mRewardedAd.show(getActivity(), new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                // Handle the reward.
                // int rewardAmount = rewardItem.getAmount();
                //String rewardType = rewardItem.getType();
            }
        });


    }

}