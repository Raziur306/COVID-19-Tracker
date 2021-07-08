package com.ronju.covid_19tracker.Activitys.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.ronju.covid_19tracker.Adapter.CountryAdapter;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;

public class CountryViewActivity extends Fragment {
    private RecyclerView recyclerView;
    private CountryAdapter mAdapter;
    private LoadingDialog loadingDialog;
    private SearchView searchView;
    private TextView backPress;
    private View view = null;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_country_view, container, false);
        loadingDialog = new LoadingDialog(getContext());
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        frameLayout = view.findViewById(R.id.fragmentViewer);

//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
//        layoutParams.setMargins(0,0,0,0);
//        frameLayout.setLayoutParams(layoutParams);

        intView();


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        mAdapter = new CountryAdapter(getContext());
        recyclerView.setAdapter(mAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        backPress.setOnClickListener(v -> {

//                    ((AppCompatActivity)getActivity()).getSupportActionBar().show();

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentViewer,new HomeActivity()).commit();
        });

        return view;
    }


    private void intView() {
        recyclerView = view.findViewById(R.id.countryItemView);
        searchView = view.findViewById(R.id.cSearch);
        backPress = view.findViewById(R.id.backPress);
    }


}