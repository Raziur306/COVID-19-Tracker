package com.corona.covid_19tracker.Activity

import androidx.appcompat.app.AppCompatActivity
import android.view.animation.Animation
import android.os.Bundle
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import com.corona.covid_19tracker.R
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import com.corona.covid_19tracker.BuildConfig
import com.corona.covid_19tracker.databinding.ActivitySplashBinding
import com.google.android.gms.ads.MobileAds
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var topAnimation: Animation? = null
    private var zoomAnimation: Animation? = null
    private var mAlertDialog: AlertDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.hide(
                android.view.WindowInsets.Type.statusBars()
            );
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        MobileAds.initialize(this) {}
        animView()
        checkForUpdate()
    }

    private fun animView() {
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_out_animation)
        binding.splashTitle.animation = topAnimation
        binding.img1.animation = zoomAnimation
        binding.img2.animation = zoomAnimation
        binding.img3.animation = zoomAnimation
        binding.img4.animation = zoomAnimation
        binding.img5.animation = zoomAnimation
        binding.img6.animation = zoomAnimation
        binding.img7.animation = zoomAnimation
        binding.img8.animation = zoomAnimation
        binding.img9.animation = zoomAnimation
        binding.img10.animation = zoomAnimation
    }


    private fun checkForUpdate() {
        startMainActivity()
    }

    private fun lunchAlertDialog() {
        mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog!!.setCancelable(false)
        mAlertDialog!!.setTitle("App Update Required")
        mAlertDialog!!.setMessage("COVID-19 Tracker recommends you to update to latest version")
        mAlertDialog!!.setPositiveButton("Update") { _: DialogInterface?, _: Int ->
            val package_name = BuildConfig.APPLICATION_ID
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$package_name")
                    )
                )
            } catch (e: Exception) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$package_name")
                    )
                )
            }
        }
        mAlertDialog!!.show()
    }

    //start activity
    private fun startMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }, 2250)
    }
}