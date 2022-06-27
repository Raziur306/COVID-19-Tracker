package com.corona.covid_19tracker.Model

import androidx.annotation.Keep

@Keep
data class Properties(
    val bnName: String,
    val confirmed: Int,
    val key: String,
    val name: String,
    val uid: Int
)