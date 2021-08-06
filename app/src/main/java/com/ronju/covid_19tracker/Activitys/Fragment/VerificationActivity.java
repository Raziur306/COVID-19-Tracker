package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.core.view.ContentInfoCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ronju.covid_19tracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends Fragment {
    private String email, phone, password, name;
    private boolean regis = false;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    TextView resentEmail;
    long resendTime = 30000;
    Button openEmail;
    String profileId;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_verification, container, false);
        resentEmail = view.findViewById(R.id.resendMail);
        progressBar = view.findViewById(R.id.vProgress);
        openEmail = view.findViewById(R.id.openEmail);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        openEmail.setOnClickListener(v -> {
            isVerified();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            startActivity(intent);
        });

        sendVerificationEmail();
        isVerified();


        resentEmail.setOnClickListener(v -> {
            sendVerificationEmail();
        });


        return view;
    }

    private void sendVerificationEmail() {
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Verification Email Send Successfully", Toast.LENGTH_SHORT).show();
                counter();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        });
    }


    private void counter() {
        new CountDownTimer(resendTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resentEmail.setText("Resend in " + millisUntilFinished / 1000 + " (s)");
                isVerified();
            }

            @Override
            public void onFinish() {
                resentEmail.setText("Resend");
                resentEmail.setClickable(true);
                resendTime *= 2;
            }
        }.start();
    }

    private void isVerified() {
        user.reload();
        user = mAuth.getCurrentUser();
        if (user.isEmailVerified()) {
            progressBar.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            profile_id();
            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = fStore.collection("user").document(mAuth.getCurrentUser().getUid());
            Map<String, Object> user = new HashMap<>();
            user.put("profile_id", profileId);
            user.put("account_status", "Activated");
            documentReference.update(user);

            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mReference = mDatabase.getReference("user").child(profileId);
            mReference.child("balance").setValue(0);
            DatabaseReference today_status = mReference.child("today_status");
            today_status.child("date").setValue(currentDate());
            today_status.child("count").setValue(0);
//            today_status


            Toast.makeText(getContext(), "Thanks for verifying your email", Toast.LENGTH_LONG).show();
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer, new DashboardActivity()).commit();
                }
            }, 2000);

        }
    }

    private void profile_id() {
        Long time_milli = System.currentTimeMillis() / 100000;
        profileId = time_milli.toString();
    }

    private String currentDate() {
        TimeZone tz = TimeZone.getTimeZone("GMT+6");
        Calendar c = Calendar.getInstance(tz);
        String date = new SimpleDateFormat("dd MMM, yyyy").format(c.getTime());
        return date;
    }


}