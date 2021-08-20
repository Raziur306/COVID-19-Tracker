package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ronju.covid_19tracker.Adapter.CountryAdapter;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;

public class CountryViewActivity extends Fragment {
    private RecyclerView recyclerView;
    private CountryAdapter mAdapter;
    private Dialog loadingDialog;
    private androidx.appcompat.widget.SearchView searchView;
    private View view = null;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_country_view, container, false);
        loadingDialog = LoadingDialog.getCustomLoadingDialog(getContext());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Choose your country");
        frameLayout = view.findViewById(R.id.fragmentViewer);

        intView();


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        mAdapter = new CountryAdapter(getContext());
        recyclerView.setAdapter(mAdapter);


        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return view;
    }


    private void intView() {
        recyclerView = view.findViewById(R.id.countryItemView);
        searchView = view.findViewById(R.id.cSearch);
    }


}