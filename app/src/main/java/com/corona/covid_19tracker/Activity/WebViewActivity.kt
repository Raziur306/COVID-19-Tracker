package com.corona.covid_19tracker.Activity

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.corona.covid_19tracker.Encryption.Encrypter
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.databinding.ActivityWebViewBinding
import com.corona.covid_19tracker.utils.AdsTask

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ((application as Encrypter).getMode() == true) {
            setTheme(R.style.Theme_Dark)
        } else {
            setTheme(R.style.Theme_Light)
        }
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.red)

        binding.backBtnWeb.setOnClickListener {
            finish()
        }
        val url = intent.extras?.getString("url")

        binding.webLink.settings.javaScriptEnabled = true
        binding.tittleOfWeb.text = "Loading......"
        binding.webLink.webViewClient = WebViewClient()
        binding.webLink.settings.builtInZoomControls = true;
        binding.webLink.settings.setSupportZoom(true);
        binding.webLink.settings.loadWithOverviewMode = true;
        binding.webLink.settings.useWideViewPort = true;
        binding.webLink.settings.allowContentAccess = true
        binding.webLink.settings.cacheMode = WebSettings.LOAD_DEFAULT



        binding.webLink.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.tittleOfWeb.text = view?.title
                binding.webViewProgressbar.visibility = View.INVISIBLE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (url != null) {
                    binding.webLink.loadUrl(url)
                }
            }

        }
        if (url != null) {
            binding.webLink.loadUrl(url)
        }
        binding.backBtnWeb.setOnClickListener {
            finish();
        }

        AdsTask(this).setBannerAds(binding.adView)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK ->
                    if (binding.webLink.canGoBack()) {
                        binding.webLink.goBack();
                    } else {
                        finish();
                    }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

}
