package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AdsActivity extends Fragment {
    private final String GAME_ID = "4218577";
    private final String INTERSTITIAL_ID = "Interstitial_Android";
    Dialog unitDialog;
    Button server_1, server_2;
    private boolean testsMode = true;
    UnityAdsListener unityAdsListener;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String UID, profile_id;

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

            if (UnityAds.isReady())
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
        firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                profile_id = documentSnapshot.getString("profile_id");

               firebaseDatabase.getReference("users").child(profile_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Log.d("Ronju",""+snapshot);
                        if (snapshot.exists()) {

                            if (!snapshot.child("today_status").child("date").getValue(String.class).equals(currentDate())) {
                                firebaseDatabase.getReference("users").child("today_status").child("date").setValue(currentDate());
                                firebaseDatabase.getReference("users").child("today_status").child("count").setValue("0");
                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


    private void initializeAdsUnit() {
        UnityAds.initialize(getActivity(), GAME_ID, testsMode);
        UnityAds.addListener(unityAdsListener);
        if (UnityAds.isInitialized()) {
            server_1.setText("Show Now");
        }
    }


    //unity ads listener
    public class UnityAdsListener implements IUnityAdsListener {
        @Override
        public void onUnityAdsReady(String s) {
            server_1.setText("Show Now");
        }

        @Override
        public void onUnityAdsStart(String s) {
            server_1.setText("Preparing");
            initializeAdsUnit();
        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
            ((TextView) unitDialog.findViewById(R.id.ads_dialog_text)).setText("Ad seen successfully");
            unitDialog.show();
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
            initializeAdsUnit();
        }
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
    }

}