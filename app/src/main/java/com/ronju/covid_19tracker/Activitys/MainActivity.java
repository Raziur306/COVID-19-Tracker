package com.ronju.covid_19tracker.Activitys;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.ronju.covid_19tracker.Activitys.Fragment.AboutActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.BDdataActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HealthCareActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HomeActivity;
import com.ronju.covid_19tracker.R;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FragmentTransaction transaction;
    private MenuItem menuItemWaiting;
    private SwitchCompat themeSwitchCompat;
    private SharedPreferences sharedPreferences;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = MainActivity.this.getSharedPreferences("covid-19_shp", Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("DarkMode",0)==1)
        {
            setTheme(R.style.Theme_Dark);
        }
        else
        {
            setTheme(R.style.Theme_Light);
        }
        setContentView(R.layout.activity_main);

        initView();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new HomeActivity();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentViewer, fragment).commit();
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
                                transaction.replace(R.id.fragmentViewer, new HomeActivity()).commit();
                                break;
                            case R.id.nav_health_status:
                                transaction.replace(R.id.fragmentViewer, new HealthCareActivity()).commit();

                                break;
                            case R.id.nav_prevention:
                                break;
                            case R.id.nav_state_data:
                                transaction.replace(R.id.fragmentViewer, new BDdataActivity()).commit();
                                break;
                            case R.id.nav_symptoms:
                                //transaction.replace(R.id.fragmentViewer, new ).commit();
                                break;
                            case R.id.question:
                                //transaction.replace(R.id.fragmentViewer, new ).commit();
                                break;
                            case R.id.nav_about:
                                transaction.replace(R.id.fragmentViewer, new AboutActivity()).commit();
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
        themeSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    setTheme(R.style.Theme_Dark);
                    editor.putInt("DarkMode", 1);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    setTheme(R.style.Theme_Light);
                    editor.putInt("DarkMode", 0);
                }
                editor.apply();
                editor.commit();
            }
        });
        if(sharedPreferences.getInt("DarkMode",0)==1)
        {
            themeSwitchCompat.setChecked(true);
        }
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