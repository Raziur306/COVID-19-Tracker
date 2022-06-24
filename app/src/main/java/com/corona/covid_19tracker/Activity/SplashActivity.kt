package com.corona.covid_19tracker.Activity

import android.app.Activity
import android.content.Context
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
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.corona.covid_19tracker.Api.ControlDataService
import com.corona.covid_19tracker.Api.ControllerDataRetrofitHelper
import com.corona.covid_19tracker.BuildConfig
import com.corona.covid_19tracker.Encryption.Encrypter
import com.corona.covid_19tracker.Repository.Response
import com.corona.covid_19tracker.Repository.SplashRepository
import com.corona.covid_19tracker.ViewModel.SplashActivityViewModel
import com.corona.covid_19tracker.ViewModel.SplashActivityViewModelFactory
import com.corona.covid_19tracker.databinding.ActivitySplashBinding
import com.google.android.gms.ads.MobileAds
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

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
        animView()
        MobileAds.initialize(this) {}
        val controllerDataService =
            ControllerDataRetrofitHelper.getInstance().create(ControlDataService::class.java)
        val repository = SplashRepository(controllerDataService)
        val viewModel = ViewModelProvider(
            this, SplashActivityViewModelFactory(repository)
        ).get(
            SplashActivityViewModel::class.java
        )
        val encrypter = applicationContext as Encrypter

        val dateFormat = SimpleDateFormat("yyyy.MM.dd");
        val date = dateFormat.format(Date())
        Log.d("Ronju", date)

        if (date != encrypter.getAdDate()) {
            encrypter.setAdDate(date)
            encrypter.setInterstitialAdShown(0)
            encrypter.setRewardAdsShown(0)
        }


        viewModel.result.observe(this, Observer {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data.let {
                        if (it != null && it.data.isNotEmpty()) {
                            encrypter.writeLatestVersionCode(it.data[0].version_code)
                            encrypter.setInterstitialAdsCount(it.data[0].interstitial_ad_limit)
                            encrypter.setBannerOnOff(it.data[0].banner_ad_on_off)
                            encrypter.setNativeAdOnoff(it.data[0].native_on_off)
                            encrypter.setRewardedAdCount(it.data[0].reward_ad_limit)
                            if (BuildConfig.VERSION_CODE != it.data[0].version_code)
                                lunchAlertDialog()
                            else
                                startMainActivity()
                        } else
                            startMainActivity()
                    }
                }
                is Response.Error -> {
                    if (BuildConfig.VERSION_CODE != encrypter.getLatestVersionCode())
                        lunchAlertDialog()
                    else

                        startMainActivity()
                }
            }
        })
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