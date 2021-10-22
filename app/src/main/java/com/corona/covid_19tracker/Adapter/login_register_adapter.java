package com.corona.covid_19tracker.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.corona.covid_19tracker.Activitys.Fragment.LoginActivity;
import com.corona.covid_19tracker.Activitys.Fragment.RegisterActivity;

public class login_register_adapter extends FragmentStateAdapter {
    private Context context;
    private int totalTabs;

    public login_register_adapter(FragmentManager fragmentManager, Lifecycle lifecycle, int totalTabs) {
        super(fragmentManager, lifecycle);
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment createFragment(int position) {
        if (position == 1)
            return new RegisterActivity();
        else
            return new LoginActivity();
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
