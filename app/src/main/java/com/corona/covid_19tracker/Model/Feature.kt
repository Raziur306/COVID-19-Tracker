package com.corona.covid_19tracker.Model

import androidx.annotation.Keep

@Keep

data class Feature(
    val properties: Properties,
    val type: String
)