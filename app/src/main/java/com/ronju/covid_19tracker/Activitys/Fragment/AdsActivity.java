package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AdsActivity extends Fragment {
    TextView totalView, remainingView;
    private final String GAME_ID = "4218577";
    private final String INTERSTITIAL_ID = "Interstitial_Android";
    Dialog unitDialog;
    Button server_1, server_2;
    private boolean testsMode = false;
    UnityAdsListener unityAdsListener;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String profile_id;
    FirebaseRemoteConfig mConfig;
    DatabaseReference firebaseReference;
    int adsLimit;
    boolean adsLimitFlag = true;
    int adsCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ads, container, false);
        initView(view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Click Here For Work");
        unitDialog = LoadingDialog.getUnitDialog(getContext());
        unitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        unitDialog.findViewById(R.id.ads_dialog_cancel_btn).setOnClickListener(v -> {
            unitDialog.dismiss();
        });

        updateData();
        unityAdsListener = new UnityAdsListener();
        initializeAdsUnit();
        server_1.setOnClickListener(v -> {

            if (UnityAds.isReady() && !limitOver())
                UnityAds.show(getActivity(), INTERSTITIAL_ID);
        });


        server_2.setOnClickListener(v -> {
            ((TextView) unitDialog.findViewById(R.id.ads_dialog_text)).setText("Coming Soon..");
            unitDialog.show();
        });


        return view;
    }

    private void updateData() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseReference = firebaseDatabase.getReference("users");
        firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                profile_id = documentSnapshot.getString("profile_id");
                firebaseReference.child(profile_id).child("today_status").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            if (!snapshot.child("date").getValue(String.class).equals(currentDate())) {
                                firebaseReference.child(profile_id).child("today_status").child("date").setValue(currentDate());
                                firebaseReference.child(profile_id).child("today_status").child("count").setValue(0);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

//ads limit set
        mConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mConfig.setConfigSettingsAsync(configSettings);
        mConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    adsLimit = (int) mConfig.getLong("ads_limit");
                    totalView.setText(String.valueOf(adsLimit));
                }
            }
        });


    }


    private void initializeAdsUnit() {
        if (!UnityAds.isInitialized())
            server_1.setText("Preparing");
        UnityAds.initialize(getActivity(), GAME_ID, testsMode);
        UnityAds.addListener(unityAdsListener);
    }


    //unity ads listener
    public class UnityAdsListener implements IUnityAdsListener {
        @Override
        public void onUnityAdsReady(String s) {
            server_1.setText("ভিডিও দেখুন");
        }

        @Override
        public void onUnityAdsStart(String s) {
            initializeAdsUnit();
        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
            server_1.setText("Preparing");
            if (!limitOver()) {
                firebaseReference.child(profile_id).child("today_status").child("count").setValue(1+adsCounter).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        ((TextView) unitDialog.findViewById(R.id.ads_dialog_text)).setText("Ad seen successfully");
                        unitDialog.show();
                    }
                });
            }
            initializeAdsUnit();
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
            initializeAdsUnit();
        }
    }

    private boolean limitOver() {
        firebaseReference.child(profile_id).child("today_status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    adsCounter = snapshot.child("count").getValue(Integer.class);
                    if (adsCounter == adsLimit) {
                        ((TextView) unitDialog.findViewById(R.id.ads_dialog_text)).setText("বিজ্ঞাপন সিমা শেষ");
                        unitDialog.show();
                        adsLimitFlag = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return adsLimitFlag;
    }


    private String currentDate() {
        TimeZone tz = TimeZone.getTimeZone("GMT+6");
        Calendar c = Calendar.getInstance(tz);
        String date = new SimpleDateFormat("dd").format(c.getTime());
        return date;
    }


    private void initView(View view) {
        server_1 = view.findViewById(R.id.server_1_btn);
        server_2 = view.findViewById(R.id.server_2_btn);
        totalView = view.findViewById(R.id.total_view);
        remainingView = view.findViewById(R.id.remaining_view);
    }

}