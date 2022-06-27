package com.corona.covid_19tracker.Model

import androidx.annotation.Keep

@Keep
data class Data(
    val banner_ad_on_off: Boolean,
    val interstitial_ad_limit: Int,
    val native_on_off: Boolean,
    val reward_ad_limit: Int,
    val version_code: Int
)