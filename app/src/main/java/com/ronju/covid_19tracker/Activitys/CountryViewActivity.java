package com.ronju.covid_19tracker.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronju.covid_19tracker.Adapter.CountryAdapter;
import com.ronju.covid_19tracker.LoadingDialog;
import com.ronju.covid_19tracker.R;

public class CountryViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CountryAdapter mAdapter;
    private LoadingDialog loadingDialog;
    private SearchView searchView;
    private TextView backPress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_view);
        loadingDialog = new LoadingDialog(this);

        intView();


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        mAdapter = new CountryAdapter(this);
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

        backPress.setOnClickListener(v->{
            onBackPressed();
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void intView() {
        recyclerView = findViewById(R.id.countryItemView);
        searchView = findViewById(R.id.cSearch);
        backPress = findViewById(R.id.backPress);
    }


}