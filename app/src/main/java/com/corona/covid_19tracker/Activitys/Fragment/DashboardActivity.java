package com.corona.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.corona.covid_19tracker.LoadingDialog;
import com.corona.covid_19tracker.R;

public class DashboardActivity extends Fragment {
    private Button withdrawBtn;
    FloatingActionButton fab;
    ImageView editProfile;
    LinearLayout helpDesk, feedBack, watchVideo, installApps;
    boolean isFabOpen = false;
    Dialog dialog;
    FirebaseDatabase firebaseDatabase;
    String serverMaintenance = "0", dialogMessage;
    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    String uid;
    String profile_id;
    Dialog loadingDialog;
    TextView name, profileId, balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        loadingDialog = LoadingDialog.getCustomLoadingDialog(getContext());
        loadingDialog.show();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        fetchData();

        dialog = LoadingDialog.getUnitDialog(getContext());
        initView(view);

        editProfile.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("null").replace(R.id.fragmentViewer,new EditProfileActivity()).commit();
        });

        withdrawBtn.setOnClickListener(v -> {
            withdrawProcess();
        });

        fab.setOnClickListener(v -> {
            if (isFabOpen) {
                closeFab();
            } else {
                openFab();
            }
        });

        watchVideo.setOnClickListener(v -> {
            serverStatus();
        });

        installApps.setOnClickListener(v -> {
            ((TextView) dialog.findViewById(R.id.ads_dialog_text)).setText("Coming soon.....");
            dialog.show();
        });

        dialog.findViewById(R.id.ads_dialog_cancel_btn).setOnClickListener(v -> {
            dialog.dismiss();
        });

        withdrawBtn.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragmentViewer,new WithdrawActivity()).commit();
        });

        return view;
    }

    private void withdrawProcess() {

    }

    private void serverStatus() {
        FirebaseRemoteConfig mConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mConfig.setConfigSettingsAsync(configSettings);
        mConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    serverMaintenance = mConfig.getString("server_maintenance");
                    dialogMessage = mConfig.getString("dialog_message");

                    if (serverMaintenance.equals("1")) {
                        showDialog();
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragmentViewer, new AdsActivity()).commit();

                    }
                }
            }
        });
    }

    private void fetchData() {
        DocumentReference documentReference = fireStore.collection("users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getString("name"));
                profile_id = documentSnapshot.getString("profile_id");
                profileId.setText(profile_id);
                currentBalance(profile_id);

            }
        });


    }

    private void currentBalance(String profile_id) {
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("users").child(profile_id).child("balance").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                balance.setText(dataSnapshot.getValue().toString());
                loadingDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(getContext(), "Please check your network.", Toast.LENGTH_SHORT).show();
                currentBalance(profile_id);
            }
        });


    }


    private void showDialog() {
        ((TextView) dialog.findViewById(R.id.ads_dialog_text)).setText(dialogMessage);
        dialog.show();
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
        installApps = view.findViewById(R.id.install_apps);
        name = view.findViewById(R.id.profileName);
        balance = view.findViewById(R.id.currentBalance);
        profileId = view.findViewById(R.id.profileId);
        withdrawBtn = view.findViewById(R.id.withdraw_btn);
        editProfile = view.findViewById(R.id.edit_profile);
    }
}