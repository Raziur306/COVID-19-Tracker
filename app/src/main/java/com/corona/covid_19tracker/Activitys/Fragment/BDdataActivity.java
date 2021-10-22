package com.corona.covid_19tracker.Activitys.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.corona.covid_19tracker.Adapter.BD_DisRecyclerAdapter;
import com.corona.covid_19tracker.Model.Bd_dis_item;
import com.corona.covid_19tracker.DoInBackground.doInBackground;
import com.corona.covid_19tracker.LoadingDialog;
import com.corona.covid_19tracker.R;
import java.util.ArrayList;


public class BDdataActivity extends Fragment {
    private ArrayList<Bd_dis_item> disData;
    private RecyclerView recyclerView;
    private Dialog loadingDialog;
    private BD_DisRecyclerAdapter mAdapter;
    private boolean flag = true;
    private  View view = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.activity_bddata, container, false);
         swipeRefreshLayout = view.findViewById(R.id.swapLayout);
      //  loading dialog
        loadingDialog = LoadingDialog.getCustomLoadingDialog(getContext());
        loadingDialog.show();

        //recycler
        recyclerView = view.findViewById(R.id.bdRecycler);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);


        getData();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });


        return view;
    }

    private void getData() {
        disData = new ArrayList<>();
        doInBackground bdDisData = new doInBackground(getContext());
        bdDisData.districtDataService(new doInBackground.VolleyDisResponseListener() {
            @Override
            public void onResponse(ArrayList<Bd_dis_item> dataBd) {
                disData = dataBd;
                flag = true;
                loadingDialog.dismiss();
                flag = true;
                setRecyclerAdapter();
            }

            @Override
            public void onErrorResponse() {
                if(flag) {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //set adapter on recyclerView
    private void setRecyclerAdapter() {
        mAdapter = new BD_DisRecyclerAdapter(disData, getContext());
        recyclerView.setAdapter(mAdapter);
        loadingDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }


}