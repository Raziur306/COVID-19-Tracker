package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ronju.covid_19tracker.R;

import java.util.regex.Pattern;


public class RegisterActivity extends Fragment {
    private TextInputEditText editTextName, editTextPhoneNumber, editTextPassword, editTextConfirmPassword, editTextEmail;
    private TextInputLayout nameLayout, numberLayout, passwordLayout, confirmPasswordLayout, emailLayout;
    private Button signInBtn;
    ProgressBar progressBar;
    String userPassword = "", userName = "", validPass = "";
    Boolean passValidity = false;
    FirebaseAuth mAuth;
    private final String CANT_BE_EMPTY = "Field can't be empty.";
    private final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        intiView(view);
        userInputValidity();


        signInBtn.setOnClickListener(v -> {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.VISIBLE);
            makeBundle();

        });


        return view;
    }

    private void userInputValidity() {
        //on text changed
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 3 && !s.toString().trim().isEmpty()) {
                    nameLayout.setError("Name must contain minimum 3 character!");
                } else {

                    nameLayout.setError(null);
                    if (s.toString().trim().length() > 2) {
                        userName = s.toString().trim();
                    } else {
                        userName = "";
                    }
                }
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!(PASSWORD_PATTERN.matcher(s.toString().trim()).matches()) && !s.toString().isEmpty()) {
                    passwordLayout.setError("Password must include:\n 8-16 character.\nNo white space< >\nAt least one number(0-9).\nAt least one capital letter(A-Z).\nAt least one small letter(a-z).\nAt least one special symbol (@#$%^&+=).");
                } else {
                    passwordLayout.setError(null);
                    if (s.toString().trim().length() >= 8)
                        userPassword = s.toString();
                }
            }
        });
        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().equals(userPassword)) {
                    confirmPasswordLayout.setError("Password not match.");
                } else {
                    confirmPasswordLayout.setError(null);
                    if (!s.toString().isEmpty() && s.toString().equals(userPassword)) {
                        passValidity = true;
                        confirmPasswordLayout.setBoxStrokeColor(ContextCompat.getColor(getContext(), R.color.green));
                        validPass = s.toString().trim();
                    } else {
                        passValidity = false;
                        validPass = "";
                    }
                }
            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    emailLayout.setError("Invalid Email");
                } else
                    emailLayout.setError(null);

            }
        });
    }

    private void makeBundle() {
        boolean valid = userInfoValidity();
        if (!valid) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Recheck your info", Toast.LENGTH_SHORT).show();
        } else {
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("registration",true);
//            bundle.putString("userName", userName);
//            bundle.putString("userPhoneNumber", "+880" + editTextPhoneNumber.getText());
//            bundle.putString("userEmail",editTextEmail.getText().toString().trim());
//            bundle.putString("userPassword", validPass);
//            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragmentViewer, new VerificationActivity(bundle)).commit();
            register();
        }

    }


    private void register() {

        mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString().trim(), validPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer,new DashboardActivity()).commit();
                }
                else
                {
                    Toast.makeText(getContext(), "Failed to register", Toast.LENGTH_SHORT).show();
                }
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private boolean userInfoValidity() {
        if (editTextName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "" + (editTextName.getText().toString().isEmpty()), Toast.LENGTH_SHORT).show();
            nameLayout.setError(CANT_BE_EMPTY);
            return false;
        } else if (userName.length() < 3) {
            nameLayout.setError("Name must contain minimum 3 character");
            return false;
        }
        if (editTextEmail.getText().toString().trim().isEmpty()) {
            emailLayout.setError(CANT_BE_EMPTY);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString().trim()).matches()) {
            emailLayout.setError("Invalid Email");
            return false;
        }

        if (editTextPhoneNumber.getText().toString().trim().isEmpty()) {
            numberLayout.setError(CANT_BE_EMPTY);
            return false;
        } else if (editTextPhoneNumber.getText().toString().trim().length() < 10) {
            numberLayout.setError("Invalid number");
            return false;
        }
        if (editTextPassword.getText().toString().trim().isEmpty()) {
            passwordLayout.setError(CANT_BE_EMPTY);
            return false;
        }
        if (editTextConfirmPassword.getText().toString().isEmpty()) {
            confirmPasswordLayout.setError(CANT_BE_EMPTY);
            return false;
        }
        if (!passValidity) {
            return false;
        }
        return true;
    }

    private void intiView(View view) {
        //TextInputLayout
        nameLayout = view.findViewById(R.id.nameLayout);
        numberLayout = view.findViewById(R.id.numberLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);
        confirmPasswordLayout = view.findViewById(R.id.confirmPasswordLayout);
        emailLayout = view.findViewById(R.id.emailLayout);


        //TextInputEditText
        editTextName = view.findViewById(R.id.registerName);
        editTextPhoneNumber = view.findViewById(R.id.registerNumber);
        editTextPassword = view.findViewById(R.id.registerPassword);
        editTextConfirmPassword = view.findViewById(R.id.registerConfirmPassword);
        signInBtn = view.findViewById(R.id.signupBtn);
        progressBar = view.findViewById(R.id.regisActivityProgressbar);
        editTextEmail = view.findViewById(R.id.registerEmail);
    }
}