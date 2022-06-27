package com.corona.covid_19tracker.Activity.Fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.webkit.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.Units.Unit
import com.corona.covid_19tracker.databinding.FragmentWebBinding
import com.corona.covid_19tracker.utils.AdsTask
import kotlin.system.exitProcess

class WebFragment : Fragment() {
    private lateinit var binding: FragmentWebBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebBinding.inflate(layoutInflater)
        binding.webLink.settings.javaScriptEnabled = true
        binding.webLink.webViewClient = WebViewClient()
        binding.webLink.settings.loadWithOverviewMode = true;
        binding.webLink.settings.useWideViewPort = true;
        binding.webLink.settings.allowContentAccess = true
        binding.webLink.settings.cacheMode = WebSettings.LOAD_DEFAULT



        binding.webLink.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                injectJs(view)
                binding.webViewProgressbar.visibility = View.INVISIBLE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                binding.webLink.loadUrl(Unit.PRIVACY_POLICY)

            }

        }
        binding.webLink.loadUrl(Unit.PRIVACY_POLICY)

        binding.webLink.setOnKeyListener { p0, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP
                && binding.webLink.canGoBack()
            ) {
                binding.webLink.goBack()

            } else
                exitProcess(0)

            true

        }

        AdsTask(requireContext()).setBannerAds(binding.adView)
        return binding.root
    }

    private fun injectJs(view: WebView?) {
        val typeValue1 = TypedValue()
        val typeValue2 = TypedValue()
        try {
            val theme = requireContext().theme
            theme.resolveAttribute(R.attr.text_color, typeValue1, true)
            theme.resolveAttribute(R.attr.background_color, typeValue2, true)
        } catch (e: Exception) {
        }

        view?.loadUrl(
            """
                javascript:(function(){
                let body =   document.getElementsByTagName("body")[0];
                body.style.color="#${Integer.toHexString(typeValue1.data and 0x00ffffff)}";
                body.style.background="#${Integer.toHexString(typeValue2.data and 0x00ffffff)} ";
                })()
            """
        )
        binding.webLink.visibility = View.VISIBLE

    }

    override fun onResume() {
        super.onResume()
        binding.webLink.loadUrl(Unit.PRIVACY_POLICY)
    }

}