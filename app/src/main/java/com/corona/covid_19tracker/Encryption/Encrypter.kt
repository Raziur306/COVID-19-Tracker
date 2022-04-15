package com.corona.covid_19tracker.Encryption

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.corona.covid_19tracker.BuildConfig

class Encrypter : Application() {
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var editor: SharedPreferences.Editor;
    override fun onCreate() {
        super.onCreate()
        val masterKey: MasterKey =
            MasterKey.Builder(applicationContext).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            applicationContext,
            "FILE",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        editor = sharedPreferences?.edit()!!
    }


    fun setMode(value: Boolean) {
        editor.putBoolean("DarkMode", value)
        editor.apply()
    }

    fun getMode(): Boolean? {
        return sharedPreferences?.getBoolean("DarkMode", false)
    }

    fun writeNotification(value: Boolean) {
        editor.putBoolean("Notification", value)
        editor.apply()
    }

    fun getNotification(): Boolean? {
        return sharedPreferences?.getBoolean("Notification", true)
    }

    fun writeLatestVersionCode(value: Int) {
        editor.putInt("latest_version_code", value)
        editor.apply()
    }

    fun getLatestVersionCode(): Int? {
        return sharedPreferences?.getInt("latest_version_code", BuildConfig.VERSION_CODE)
    }


    fun setDefaultCountry(value: Int) {
        editor.putInt("Country", value)
        editor.apply()
    }

    fun getDefaultCountry(): Int? {
        return sharedPreferences?.getInt("Country", 50)
    }
    //for ad

    fun setInterstitialAdsCount(value: Int) {
        editor.putInt("adsShown", value)
        editor.apply()
    }

    fun getInterstitialAdsCount(): Int? {
        return sharedPreferences?.getInt("adsShown", 0)

    }

    fun setAdDate(value: String) {
        editor.putString("date", value)
        editor.apply()
    }

    fun getDateOfAd(): String? {
        return sharedPreferences?.getString("date", "")
    }
}