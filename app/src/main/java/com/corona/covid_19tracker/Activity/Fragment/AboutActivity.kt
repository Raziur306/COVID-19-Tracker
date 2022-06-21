package com.corona.covid_19tracker.Activity.Fragment

import android.widget.TextView
import androidx.annotation.RequiresApi
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.corona.covid_19tracker.R
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import java.lang.Exception

class AboutActivity : Fragment() {
    private var abTxt1: TextView? = null
    private var abTxt2: TextView? = null
    private var abTxt3: TextView? = null
    private var abTxt4: TextView? = null
    private var abTxt5: TextView? = null
    private var abTxt6: TextView? = null
    private var aboutTitle: TextView? = null
    private var developer: TextView? = null
    private var developerName: TextView? = null
    private var developerStudy: TextView? = null


    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_about, container, false)
        intView(view)
        setTextFont(view)



        return view
    }


    private fun setTextFont(view: View) {
        val typeface =
            Typeface.createFromAsset(view.context.assets, "fonts/archia-bold-webfont.ttf")
        abTxt1!!.typeface = typeface
        abTxt2!!.typeface = typeface
        abTxt3!!.typeface = typeface
        abTxt4!!.typeface = typeface
        abTxt5!!.typeface = typeface
        abTxt6!!.typeface = typeface
        aboutTitle!!.typeface = typeface
        developer!!.typeface = typeface
        developerName!!.typeface = typeface
    }

    private fun intView(view: View) {
        abTxt1 = view.findViewById(R.id.abTxt1)
        abTxt2 = view.findViewById(R.id.abTxt2)
        abTxt3 = view.findViewById(R.id.abTxt3)
        abTxt4 = view.findViewById(R.id.abTxt4)
        abTxt5 = view.findViewById(R.id.abTxt5)
        abTxt6 = view.findViewById(R.id.abTxt6)
        aboutTitle = view.findViewById(R.id.about_title)
        developer = view.findViewById(R.id.developBy)
        developerName = view.findViewById(R.id.developerName)
        developerStudy = view.findViewById(R.id.developerStudy)
    }
}