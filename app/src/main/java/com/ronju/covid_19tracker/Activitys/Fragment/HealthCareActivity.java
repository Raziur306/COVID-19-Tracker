package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronju.covid_19tracker.R;

public class HealthCareActivity extends Fragment {
    ImageView who1;
    TextView viewAll;
    private final String HOTLINES_NUMBER = "https://corona.gov.bd/call_center";
    private final String TEST_BY_OWN ="https://corona.gov.bd/corona-symptom";
    private final String APPLY_VACCINE ="https://surokkha.gov.bd/enroll";
    private final String BRAC_TEST_APPLY="https://coronatest.brac.net";
    Button callNow,hotLines,ownTestBtn,vaccineBtn,testBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_health_care, container, false);

        initView(view);


        callNow.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:33313"));
            startActivity(intent);

        });
        hotLines.setOnClickListener(v->{
            openBrowserTab();
        });

        ownTestBtn.setOnClickListener(v->{
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(getActivity(),Uri.parse(TEST_BY_OWN));
        });

        vaccineBtn.setOnClickListener(v->{
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(getActivity(),Uri.parse(APPLY_VACCINE));
        });

        testBtn.setOnClickListener(v->{
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(getActivity(),Uri.parse(BRAC_TEST_APPLY));
        });

        return view;
    }

    private void openBrowserTab() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(HOTLINES_NUMBER));
    }

    private void initView(View view) {
        callNow = view.findViewById(R.id.callNow);
        hotLines = view.findViewById(R.id.hotLines);
        ownTestBtn = view.findViewById(R.id.ownTestBtn);
        vaccineBtn = view.findViewById(R.id.applyVaccine);
        testBtn = view.findViewById(R.id.applyForTest);
    }
}