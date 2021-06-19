package com.ronju.covid_19tracker.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ronju.covid_19tracker.Activitys.Fragment.AboutActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.BDdataActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HealthCareActivity;
import com.ronju.covid_19tracker.Activitys.Fragment.HomeActivity;
import com.ronju.covid_19tracker.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private long ID = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (getIntent().getExtras() != null) {
            ID = getIntent().getExtras().getLong("ID");
        }



        transaction.replace(R.id.fragmentViewer, new HomeActivity(ID)).commit();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
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
}