package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;

public class LoginActivity extends Fragment {
    private View view = null;
    private Button loginBtn;
    TextInputEditText email,password;
    TextInputLayout emailLayout,passwordLayout;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView loginWarning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);
       initView();



        loginBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            loginWarning.setVisibility(View.GONE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loggingIn();
        });

        return view;
    }

    private void loggingIn() {
       mAuth = FirebaseAuth.getInstance();
       mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(Task<AuthResult> task) {
               if(task.isSuccessful())
               {
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer,new DashboardActivity()).commit();
               }
               else
               {
                   progressBar.setVisibility(View.GONE);
                   loginWarning.setVisibility(View.VISIBLE);
                   loginWarning.setText("Wrong/Invalid Email or Password");
                   getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
               }
           }
       });


    }



    private void initView() {
        //inputLayoutEditText
        emailLayout = view.findViewById(R.id.emailLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);

        //inputEditText
        loginBtn = view.findViewById(R.id.login_button);
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);

        progressBar = view.findViewById(R.id.loginProgress);
        loginWarning = view.findViewById(R.id.loginWarning);
    }


}