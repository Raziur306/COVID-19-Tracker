package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class RegisterActivity extends Fragment {
    private TextInputEditText editTextName, editTextPhoneNumber, editTextPassword, editTextConfirmPassword, editTextEmail;
    private TextInputLayout nameLayout, numberLayout, passwordLayout, confirmPasswordLayout, emailLayout;
    private Button signInBtn;
    Dialog loadingDialog;
    private String email = "", number = "", password = "", confirmPassword = "", name = "";
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    private final String CANT_BE_EMPTY = "Field can't be empty.";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, container, false);
        loadingDialog = LoadingDialog.getCustomLoadingDialog(getContext());

        mAuth = FirebaseAuth.getInstance();
        intiView(view);
        inputFieldValidity();


//register Button
        signInBtn.setOnClickListener(v -> {
            disableScreenTouch();
            registerCall();

        });

        return view;
    }

    private void inputFieldValidity() {
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

                if (s.toString().isEmpty() || s.toString().length()<9) {
                    passwordLayout.setError("Password length must be 8-16 character.");
                } else {
                    passwordLayout.setError(null);
                    if (s.toString().trim().length() >= 8)
                        password = s.toString();
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

                if (!s.toString().equals(password)) {
                    confirmPasswordLayout.setError("Password not match.");
                } else {
                    confirmPasswordLayout.setError(null);
                    if (!s.toString().isEmpty() && s.toString().equals(password)) {
                        confirmPasswordLayout.setBoxStrokeColor(ContextCompat.getColor(getContext(), R.color.green));
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

    private void registerCall() {
        name = editTextName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString();
        confirmPassword = editTextConfirmPassword.getText().toString();
        number = "+880" + editTextPhoneNumber.getText().toString();
        boolean valid = userInfoValidity(name, email, password, confirmPassword, number);
        if (!valid) {
            enableScreenTouch();
            Toast.makeText(getContext(), "Recheck your info", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        } else {
            onEmailCheckListener();
        }

    }


    private void onEmailCheckListener() {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(Task<SignInMethodQueryResult> task) {
                if (task.getResult().getSignInMethods().size() == 0) {
                    register();
                } else {
                    Toast.makeText(getContext(), "Email Already Exist", Toast.LENGTH_LONG).show();
                    enableScreenTouch();
                    emailLayout.setError("Email Already Exist.");
                }
            }
        });
    }


    private void register() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    fStore = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = fStore.collection("users").document(mAuth.getCurrentUser().getUid());
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("pNumber", number);
                    user.put("email", email);
                    documentReference.set(user);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer, new VerificationActivity()).commit();
                } else {
                    Toast.makeText(getContext(), "Failed to register", Toast.LENGTH_SHORT).show();
                }
                enableScreenTouch();

            }
        });
    }




    private void disableScreenTouch() {
       loadingDialog.show();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void enableScreenTouch() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
      loadingDialog.dismiss();
    }

    private boolean userInfoValidity(String name, String email, String password, String confirmPassword, String number) {
        if (name.isEmpty()) {
            nameLayout.setError(CANT_BE_EMPTY);
            return false;
        } else if (name.length() < 3) {
            nameLayout.setError("Name must contain minimum 3 character");
            return false;
        }
        if (email.isEmpty()) {
            emailLayout.setError(CANT_BE_EMPTY);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Invalid Email");
            return false;
        }

        if (number.isEmpty()) {
            numberLayout.setError(CANT_BE_EMPTY);
            return false;
        } else if (number.length() < 10) {
            numberLayout.setError("Invalid number");
            return false;
        }
        if (password.isEmpty()) {
            passwordLayout.setError(CANT_BE_EMPTY);
            return false;
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordLayout.setError(CANT_BE_EMPTY);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Password not match");
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
        editTextEmail = view.findViewById(R.id.registerEmail);
    }
}