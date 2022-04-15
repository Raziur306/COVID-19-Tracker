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
    private var socialTitle: TextView? = null
    private var facebook: ImageView? = null
    private var twitter: ImageView? = null
    private var github: ImageView? = null
    private var instagram: ImageView? = null
    private var email: ImageView? = null
    private var linkedin: ImageView? = null
    private val facebookId = "raziur.rahman01"
    private val twitterId = "https://twitter.com/RaziurRahaman01"
    private val githubId = "https://github.com/Raziur306"
    private val instagramID = "https://www.instagram.com/raziur.rahaman01"
    private val emailAdds = "20103067@iubat.edu"
    private val linkedinID = "https://www.linkedin.com/in/raziur-rahaman"
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_about, container, false)
        intView(view)
        setTextFont(view)


        //facebook
        facebook!!.setOnClickListener {
            val intent = getFBIntent()
            startActivity(intent)
        }

        //github
        github!!.setOnClickListener {
            val intent = githubIntent
            startActivity(intent)
        }
        //instagram
        instagram!!.setOnClickListener {
            val intent = instagramIntent
            startActivity(intent)
        }
        //email
        email!!.setOnClickListener {
            val intent = emailIntent
            startActivity(intent)
        }

        //linkedin
        linkedin!!.setOnClickListener {
            val intent = linkedinIntent
            startActivity(intent)
        }
        //twitter
        twitter!!.setOnClickListener {
            val intent = twitterIntent
            startActivity(intent)
        }
        return view
    }

    private val twitterIntent: Intent
         get() = Intent(Intent.ACTION_VIEW, Uri.parse(twitterId))
    private val linkedinIntent: Intent
         get() = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinID))
    private val emailIntent: Intent
        get() = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$emailAdds"))

    //instagram
    private val instagramIntent: Intent
        get() = Intent(Intent.ACTION_VIEW, Uri.parse(instagramID))

    //github
    private val githubIntent: Intent
         get() = Intent(Intent.ACTION_VIEW, Uri.parse(githubId))

    //facebook
    private fun getFBIntent(): Intent {
        return try {
            requireContext().packageManager.getPackageInfo("com.facebook.katana", 0)
            val facebookScheme = "fb://facewebmodal/f?href=https://www.facebook.com/$facebookId"
            Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme))
        } catch (e: Exception) {
            val facebookProfileURI = "www.fb.com/$facebookId"
            Intent(Intent.ACTION_VIEW, Uri.parse(facebookProfileURI))
        }
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
        developerStudy!!.typeface = typeface
        socialTitle!!.typeface = typeface
    }

    private fun intView(view: View) {
        abTxt1 = view.findViewById(R.id.abTxt1)
        abTxt2 = view.findViewById(R.id.abTxt2)
        abTxt3 = view.findViewById(R.id.abTxt3)
        abTxt4 = view.findViewById(R.id.abTxt4)
        abTxt5 = view.findViewById(R.id.abTxt5)
        abTxt6 = view.findViewById(R.id.abTxt6)
        aboutTitle = view.findViewById(R.id.about_title)
        facebook = view.findViewById(R.id.facebook)
        twitter = view.findViewById(R.id.twitter)
        instagram = view.findViewById(R.id.instagram)
        github = view.findViewById(R.id.github)
        email = view.findViewById(R.id.email)
        linkedin = view.findViewById(R.id.linkedin)
        developer = view.findViewById(R.id.developBy)
        developerName = view.findViewById(R.id.developerName)
        developerStudy = view.findViewById(R.id.developerStudy)
        socialTitle = view.findViewById(R.id.socialTitle)
    }
}