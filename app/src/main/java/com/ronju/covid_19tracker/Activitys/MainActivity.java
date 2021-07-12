package com.ronju.covid_19tracker.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.ronju.covid_19tracker.Activitys.Fragment.AboutActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.BDdataActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.CommunityJobActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HealthCareActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HomeActivity;
import com.ronju.covid_19tracker.R;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FragmentTransaction transaction;
    private SwitchCompat themeSwitchCompat;
    private SharedPreferences sharedPreferences;
    private final Handler handler = new Handler();
    private int fragmentIndex = 0;
    private Fragment allFragment[] = {new HomeActivity(), new HealthCareActivity(), null, null, new BDdataActivity(), null, new CommunityJobActivity(), new AboutActivity()};


    //saving current Fragment activity
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_fragment", fragmentIndex);
        Log.d("Saving_fragment", "" + fragmentIndex);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = MainActivity.this.getSharedPreferences("covid-19_shp", Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("DarkMode", 0) == 1) {
            setTheme(R.style.Theme_Dark);
        } else {
            setTheme(R.style.Theme_Light);
        }
        setContentView(R.layout.activity_main);

        initView();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState != null)
            fragmentIndex = savedInstanceState.getInt("current_fragment", 0);
        Log.d("Saving_fragment_currr", "" + fragmentIndex);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
        nav.getMenu().getItem(0).setChecked(true);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                transaction = getSupportFragmentManager().beginTransaction();
                drawerLayout.closeDrawer(GravityCompat.START);

                handler.postDelayed(new Runnable() {
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
                                transaction.replace(R.id.fragmentViewer, allFragment[fragmentIndex]).commit();
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

        themeSwitchCompat = (SwitchCompat) nav.getMenu().findItem(R.id.dark_menu).getActionView();
        if (sharedPreferences.getInt("DarkMode", 0) == 1) {
            themeSwitchCompat.setChecked(true);
        }

        themeSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {

                    //setTheme(R.style.Theme_Dark);
                    onNightModeChanged(AppCompatDelegate.MODE_NIGHT_YES);

                    editor.putInt("DarkMode", 1);
                } else {
                    onNightModeChanged(AppCompatDelegate.MODE_NIGHT_NO);

                    //setTheme(R.style.Theme_Light);
                    editor.putInt("DarkMode", 0);
                }
                editor.apply();
                editor.commit();
            }
        });


    }

    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
        if (mode == AppCompatDelegate.MODE_NIGHT_YES)
            Toast.makeText(MainActivity.this, "Dark Mode Enabled", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Dark Mode Disabled", Toast.LENGTH_LONG).show();
        transaction.replace(R.id.fragmentViewer, new AboutActivity());
        recreate();
    }

    public void clickDrawerCloser(View view) {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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