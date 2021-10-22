package com.corona.covid_19tracker.Activitys.Fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.corona.covid_19tracker.LoadingDialog;
import com.corona.covid_19tracker.R;

public class LoginActivity extends Fragment {
    private View view = null;
    private Button loginBtn;
    Dialog loadingDialog;
    TextInputEditText email, password;
    TextInputLayout emailLayout, passwordLayout;
    FirebaseAuth mAuth;
    TextView loginWarning, forgetPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userEmail, userPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);
        loadingDialog = LoadingDialog.getCustomLoadingDialog(getContext());
        initView();
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getActivity().getSharedPreferences("covid-19_shp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userEmail = sharedPreferences.getString("email", null);
        userPassword = sharedPreferences.getString("password", null);
        if (userEmail != null && userPassword != null) {
            email.setText(userEmail);
            password.setText(userPassword);
            loggingIn(userEmail, userPassword);
        }


        loginBtn.setOnClickListener(v -> {
            checkForValidity();
        });
        forgetPassword.setOnClickListener(v -> {
            final EditText editText = new EditText(getContext());
            final AlertDialog.Builder resetPasswordDialog = new AlertDialog.Builder(getContext());
            resetPasswordDialog.setCancelable(false);
            resetPasswordDialog.setTitle("Reset Password ?");
            resetPasswordDialog.setMessage("Enter Your Email To Receive Reset Link: ");
            resetPasswordDialog.setView(editText);
            resetPasswordDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            resetPasswordDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String email = editText.getText().toString().trim();
                    if (!email.isEmpty()) {
                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Reset Link Send to Your Email.", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getContext(), "Error! Reset Link not sent " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
            resetPasswordDialog.show();
        });

        return view;
    }

    private void checkForValidity() {
        if (email.getText().toString().isEmpty()) {
            emailLayout.setError("Field can't be empty.");
        } else if (password.getText().toString().isEmpty()) {
            passwordLayout.setError("Field can't be empty.");
        } else {
            userEmail = email.getText().toString().trim();
            userPassword = password.getText().toString().trim();
            loggingIn(userEmail, userPassword);
        }

    }


    private void loggingIn(String email, String password) {
        loadingDialog.show();
        loginWarning.setVisibility(View.GONE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();

                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user.isEmailVerified()) {
                        FirebaseFirestore.getInstance().collection("users").document(mAuth.getCurrentUser().getUid()).update("device_id", Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer, new DashboardActivity()).commit();
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer, new VerificationActivity()).commit();
                    }
                }
                loadingDialog.dismiss();
                loginWarning.setVisibility(View.VISIBLE);
                loginWarning.setText("Wrong/Invalid Email or Password");

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
        loginWarning = view.findViewById(R.id.loginWarning);
        forgetPassword = view.findViewById(R.id.forget_password);
    }


}