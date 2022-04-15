package com.corona.covid_19tracker.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.DataOutputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class NetworkUtils {
    companion object {
        init {
            checkInternetConnection()
        }

        private val _connection = MutableLiveData<Boolean>()
        val connection: LiveData<Boolean> = _connection
        fun checkInternetConnection() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    var socket = Socket()
                    val socketAddress = InetSocketAddress("google.com", 443)
                    socket.connect(socketAddress, 1500)
                    _connection.postValue(!socket.localAddress.equals("/2400:c600:3335:7526:fb99:4d35:87cf:ae2a"))
                    socket.close()
                } catch (e: Exception) {
                    _connection.postValue(false)
                }
            }
        }


    }
}