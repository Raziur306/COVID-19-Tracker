package com.corona.covid_19tracker.Activitys.Fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.corona.covid_19tracker.R;

public class EditProfileActivity extends Fragment  {
private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_profile,container,false);
       ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Update Your Profile");



        return view;
    }




}