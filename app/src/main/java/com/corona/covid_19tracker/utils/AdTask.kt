package com.corona.covid_19tracker.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.corona.covid_19tracker.Encryption.Encrypter
import com.corona.covid_19tracker.R
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


class AdsTask(private val context: Context) {
    private var count = 0;

    init {
        MobileAds.initialize(context.applicationContext) {}
    }

    private val adRequest = AdRequest.Builder().build()

    private var e: Encrypter = context.applicationContext as Encrypter
    fun setBannerAds(adView: AdView) {
        if (e.getBannerOnOff() == true) {
            adView.visibility = View.VISIBLE
            adView.loadAd(adRequest)
        }
    }

    fun showInterstitialAds() {
        val shownAds = e.getInterstitialAdShown()!!
        ++count
        if (count % 3 == 1 && shownAds < e.getInterstitialAdsCount()!!) {
            InterstitialAd.load(
                context.applicationContext,
                context.getString(R.string.interstitial_ad_id),
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        showRewardAds()
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        e.setInterstitialAdShown(shownAds + 1)
                        interstitialAd.show(context as Activity)
                    }
                })
        } else
            showRewardAds()

    }

    private fun showRewardAds() {
        val shown = e.getRewardAdsShown()!!
        if (e.getRewardedAdCount()!! > shown) {
            RewardedAd.load(
                context,
                context.getString(R.string.rewarded_ad_id),
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {

                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        if (e.getRewardedAdCount()!! > e.getRewardAdsShown()!!)
                            rewardedAd.show(
                                context as Activity,
                                OnUserEarnedRewardListener() {
                                    e.setRewardAdsShown(shown + 1)
                                })
                    }
                })
        }
    }

    //native
    fun nativeAdLoader(view: TemplateView) {
        val adLoader = AdLoader.Builder(context, context.getString(R.string.native_ad_id))
            .forNativeAd { nativeAd ->
                view.setNativeAd(nativeAd)
                view.visibility = View.VISIBLE
                Log.d("Ronju", "AdLoaded")
            }
            .build()

        adLoader.loadAd(adRequest)
    }


}