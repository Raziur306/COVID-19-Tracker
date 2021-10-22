package com.corona.covid_19tracker.Activitys.Fragment;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.corona.covid_19tracker.R;

public class AboutActivity extends Fragment {
    private TextView abTxt1, abTxt2, abTxt3, abTxt4, abTxt5, abTxt6, aboutTitle, developer, developerName, developerStudy,socialTitle;
    private ImageView facebook, twitter, github, instagram, email, linkedin;
    private String facebookId = "raziur.rahman01";
    private String twitterId = "https://twitter.com/RaziurRahaman01";
    private String githubId = "https://github.com/Raziur306";
    private String instagramID = "https://www.instagram.com/raziur.rahaman01";
    private String emailAdds = "20103067@iubat.edu";
    private String linkedinID = "https://www.linkedin.com/in/raziur-rahaman";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about, container, false);
        

        intView(view);
        setTextFont(view);


        //facebook
        facebook.setOnClickListener(v -> {
            Intent intent = getFBIntent(getContext());
            startActivity(intent);
        });

        //github
        github.setOnClickListener(v -> {
            Intent intent = getGithubIntent();
            startActivity(intent);
        });
        //instagram
        instagram.setOnClickListener(v -> {
            Intent intent = getInstagramIntent();
            startActivity(intent);
        });
        //email
        email.setOnClickListener(v -> {
            Intent intent = getEmailIntent();
            startActivity(intent);
        });

        //linkedin
        linkedin.setOnClickListener(v -> {
            Intent intent = getLinkedinIntent();
            startActivity(intent);
        });
        //twitter
        twitter.setOnClickListener(v -> {
            Intent intent = getTwitterIntent();
            startActivity(intent);
        });


        

        return view;
    }

    private Intent getTwitterIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(twitterId));
    }

    private Intent getLinkedinIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(linkedinID));
    }

    private Intent getEmailIntent() {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAdds));
    }

    //instagram
    private Intent getInstagramIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(instagramID));
    }

    //github
    private Intent getGithubIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(githubId));
    }

    //facebook
    private Intent getFBIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            String facebookScheme = "fb://facewebmodal/f?href=https://www.facebook.com/" + facebookId;
            return new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
        } catch (Exception e) {
            String facebookProfileURI = "www.fb.com/" + facebookId;
            return new Intent(Intent.ACTION_VIEW, Uri.parse(facebookProfileURI));
        }
    }


    private void setTextFont(View view) {
        Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/archia-bold-webfont.ttf");

        abTxt1.setTypeface(typeface);
        abTxt2.setTypeface(typeface);
        abTxt3.setTypeface(typeface);
        abTxt4.setTypeface(typeface);
        abTxt5.setTypeface(typeface);
        abTxt6.setTypeface(typeface);
        aboutTitle.setTypeface(typeface);
        developer.setTypeface(typeface);
        developerName.setTypeface(typeface);
        developerStudy.setTypeface(typeface);
        socialTitle.setTypeface(typeface);
    }


    private void intView(View view) {
        abTxt1 = view.findViewById(R.id.abTxt1);
        abTxt2 = view.findViewById(R.id.abTxt2);
        abTxt3 = view.findViewById(R.id.abTxt3);
        abTxt4 = view.findViewById(R.id.abTxt4);
        abTxt5 = view.findViewById(R.id.abTxt5);
        abTxt6 = view.findViewById(R.id.abTxt6);
        aboutTitle = view.findViewById(R.id.about_title);
        facebook = view.findViewById(R.id.facebook);
        twitter = view.findViewById(R.id.twitter);
        instagram = view.findViewById(R.id.instagram);
        github = view.findViewById(R.id.github);
        email = view.findViewById(R.id.email);
        linkedin = view.findViewById(R.id.linkedin);
        developer = view.findViewById(R.id.developBy);
        developerName = view.findViewById(R.id.developerName);
        developerStudy = view.findViewById(R.id.developerStudy);
        socialTitle = view.findViewById(R.id.socialTitle);
    }

}