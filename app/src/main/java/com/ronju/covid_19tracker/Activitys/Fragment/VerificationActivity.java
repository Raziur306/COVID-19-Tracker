package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ronju.covid_19tracker.R;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends Fragment {
    private String email, phone, password, name;
    private boolean regis = false;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    TextView resentEmail;
    long resendTime=30000;
    Button openEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_verification, container, false);
        resentEmail = view.findViewById(R.id.resendMail);
        openEmail = view.findViewById(R.id.openEmail);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        openEmail.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            startActivity(intent);
        });


        sendVerificationEmail();
        resentEmail.setOnClickListener(v -> {
            sendVerificationEmail();
        });


        return view;
    }

    private void sendVerificationEmail() {
        if (user.isEmailVerified()) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer, new DashboardActivity());
        } else {
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
    }






    private void counter() {
        new CountDownTimer(resendTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                user = mAuth.getCurrentUser();
                user.reload();
                if (user.isEmailVerified()) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer, new DashboardActivity());
                }
                resentEmail.setText("Resend in "+millisUntilFinished/1000+" (s)");
            }
            @Override
            public void onFinish() {
                resentEmail.setText("Resend");
                resentEmail.setClickable(true);
                resendTime*=2;
            }
        }.start();
    }

}