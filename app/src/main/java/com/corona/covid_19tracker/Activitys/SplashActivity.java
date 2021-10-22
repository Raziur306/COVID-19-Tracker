package com.corona.covid_19tracker.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PackageInfoCompat;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.corona.covid_19tracker.BuildConfig;
import com.corona.covid_19tracker.R;

public class SplashActivity extends AppCompatActivity {
    private FirebaseRemoteConfig mConfig;
    private TextView title;
    private String LATEST_APP_VERSION = "0";
    private ImageView img1;
    private Animation topAnimation,bottomAnimation,zoomAnimation;
    private boolean flag = true;
    private AlertDialog.Builder mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R)
        {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if(insetsController!=null)
            {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        }
        else
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        animView();
        checkForUpdate();
    }

    private void animView() {
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        zoomAnimation = AnimationUtils.loadAnimation(this,R.anim.zoom_out_animation);


        title = findViewById(R.id.splashTitle);
        title.setAnimation(topAnimation);

        img1 = findViewById(R.id.img1);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img2);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img3);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img4);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img5);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img6);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img7);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img8);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img9);
        img1.setAnimation(zoomAnimation);
        img1 = findViewById(R.id.img10);
        img1.setAnimation(zoomAnimation);



    }

    private int getVersionCode() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) PackageInfoCompat.getLongVersionCode(packageInfo);
    }

    private void checkForUpdate() {
        mConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10)
                .build();
        mConfig.setConfigSettingsAsync(configSettings);

        mConfig.fetchAndActivate().addOnCompleteListener(task -> {
            LATEST_APP_VERSION = mConfig.getString("latest_app_version");
            if (getVersionCode() < Integer.parseInt(LATEST_APP_VERSION)) {
                lunchAlertDialog();
            }
            else
            {
                startMainActivity();
            }
        });

    }

    private void lunchAlertDialog() {
        mAlertDialog = new AlertDialog.Builder(this);
        mAlertDialog.setCancelable(false);
        mAlertDialog.setTitle("App Update Required");
        mAlertDialog.setMessage("COVID-19 Tracker recommends you to update to latest version");
        mAlertDialog.setPositiveButton("Update", (dialog, which) -> {
            final String package_name= BuildConfig.APPLICATION_ID;
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+package_name)));
            }catch (Exception e)
            {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id="+package_name)));
            }
        });
        mAlertDialog.show();
    }

    //start activity
    private void startMainActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        }, 2250);
    }

}