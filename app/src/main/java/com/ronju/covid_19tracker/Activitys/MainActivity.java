package com.ronju.covid_19tracker.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ronju.covid_19tracker.Activitys.Fragment.AboutActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.BDdataActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HealthCareActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HomeActivity;
import com.ronju.covid_19tracker.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavigationView nav;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private long ID = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (getIntent().getExtras() != null) {
            ID = getIntent().getExtras().getLong("ID");
        }

        transaction.replace(R.id.fragmentViewer, new HomeActivity(ID)).commit();






        bottomNavigationView = findViewById(R.id.bottom_nav);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.mHOme:
                        transaction.replace(R.id.fragmentViewer, new HomeActivity(ID)).commit();
                        break;
                    case R.id.bdDetails:
                        transaction.replace(R.id.fragmentViewer, new BDdataActivity()).commit();
                        break;
                    case R.id.healthCare:
                        transaction.replace(R.id.fragmentViewer, new HealthCareActivity()).commit();
                        break;
                    case R.id.about:
                        transaction.replace(R.id.fragmentViewer, new AboutActivity()).commit();
                        break;
                }
                return true;
            }
        });


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