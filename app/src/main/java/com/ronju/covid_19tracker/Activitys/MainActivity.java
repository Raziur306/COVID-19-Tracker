package com.ronju.covid_19tracker.Activitys;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ronju.covid_19tracker.Activitys.Fragment.AboutActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.BDdataActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.DashboardActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.LoginRegisterTabActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HealthCareActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HomeActivity;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;

public class MainActivity extends AppCompatActivity {
    private NavigationView nav;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private FragmentTransaction transaction;
    private SwitchCompat themeSwitchCompat;
    private SwitchCompat locationSwitchCompat;
    private SharedPreferences sharedPreferences;


    private int fragmentIndex = 0;
    private Fragment allFragment[] = {new HomeActivity(), new HealthCareActivity(), null, null, new BDdataActivity(), null, new LoginRegisterTabActivity(), new AboutActivity()};


    //saving current Fragment activity
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_fragment", fragmentIndex);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        sharedPreferences = MainActivity.this.getSharedPreferences("covid-19_shp", Context.MODE_PRIVATE);

        if (sharedPreferences.getInt("DarkMode", 0) == 1) {
            setTheme(R.style.Theme_Dark);

        } else {
            setTheme(R.style.Theme_Light);
        }

        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        setContentView(R.layout.activity_main);

        initView();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState != null) {
            fragmentIndex = savedInstanceState.getInt("current_fragment", 0);
        }

        transaction = getSupportFragmentManager().beginTransaction();


        if (fragmentIndex == 6) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                transaction.replace(R.id.fragmentViewer, new DashboardActivity()).commit();
            } else {
                transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
            }
        } else {
            transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
        }

        nav.getMenu().getItem(0).setChecked(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                transaction = getSupportFragmentManager().beginTransaction();
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                fragmentIndex = 0;
                                transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
                                break;
                            case R.id.nav_health_status:
                                fragmentIndex = 1;
                                transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();

                                break;
                            case R.id.nav_prevention:
                                fragmentIndex = 2;
                                break;
                            case R.id.nav_state_data:
                                fragmentIndex = 3;
                                transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
                                break;
                            case R.id.nav_symptoms:
                                fragmentIndex = 4;
                                //transaction.replace(R.id.fragmentViewer, new ).commit();
                                break;
                            case R.id.question:
                                fragmentIndex = 5;
                                //transaction.replace(R.id.fragmentViewer, new ).commit();
                                break;
                            case R.id.community_job:
                                fragmentIndex = 6;
                                deviceIdentifier();
                                break;
                            case R.id.nav_about:
                                fragmentIndex = 7;
                                transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
                                break;
                        }
                    }
                }, 300);
                return true;
            }
        });

//settings
        //dark_mode

        themeSwitchCompat = (SwitchCompat) nav.getMenu().findItem(R.id.dark_menu).getActionView().findViewById(R.id.nav_switch_item);
        if (sharedPreferences.getInt("DarkMode", 0) == 1) {
            themeSwitchCompat.setChecked(true);
        }

        themeSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    onNightModeChanged(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putInt("DarkMode", 1);
                } else {
                    onNightModeChanged(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putInt("DarkMode", 0);
                }
                editor.apply();
            }
        });
//location
        locationSwitchCompat = (SwitchCompat) nav.getMenu().findItem(R.id.location_menu).getActionView().findViewById(R.id.nav_switch_item);
        locationSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MainActivity.this, "Item Changed", Toast.LENGTH_SHORT).show();
            }
        });


//closing keyboard
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText())
                    inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    toolbar.findViewById(R.id.toolbar_data).setVisibility(View.GONE);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } else {
                    toolbar.findViewById(R.id.toolbar_data).setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    toggle.syncState();
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                }
            }
        });


    }

    private void deviceIdentifier() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null && getApplicationContext().getSharedPreferences("covid-19_shp", Context.MODE_PRIVATE).getString("email", null) != null) {

            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("device_id").equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))) {
                        transaction.replace(R.id.fragmentViewer, new DashboardActivity()).commit();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", null);
                        editor.apply();
                        FirebaseAuth.getInstance().signOut();
                        Dialog dialog = LoadingDialog.getUnitDialog(MainActivity.this);
                        ((TextView) dialog.findViewById(R.id.ads_dialog_text)).setText("You are signed in from another device.");
                        dialog.findViewById(R.id.ads_dialog_cancel_btn).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        dialog.show();
                        transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();

                    }
                }
            });
        } else {
            transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
        }
    }


    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
        if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            Toast.makeText(MainActivity.this, "Dark Mode Enabled", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MainActivity.this, "Dark Mode Disabled", Toast.LENGTH_LONG).show();
        }
        recreate();
    }

    public void clickDrawerCloser(View view) {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.nav_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

}