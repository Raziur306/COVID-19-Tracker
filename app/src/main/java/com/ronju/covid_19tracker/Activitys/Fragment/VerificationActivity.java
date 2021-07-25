package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ronju.covid_19tracker.R;

public class VerificationActivity extends Fragment {
    private String email, phone, password, name;
    private boolean regis = false;
    private FirebaseAuth mAuth;

    public VerificationActivity(Bundle bundle) {
        if (bundle.getBoolean("registration")) {
            regis = bundle.getBoolean("registration");
            name = bundle.getString("userName");
            email = bundle.getString("userEmail");
            phone = bundle.getString("userPhoneNumber");
            password = bundle.getString("userPassword");
        }
        else
        {
            email = bundle.getString("userEmail");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_verification, container, false);

//        if(regis)
//        {
//            register();
//        }


        return view;
    }








    private void initView(View view) {


    }
}