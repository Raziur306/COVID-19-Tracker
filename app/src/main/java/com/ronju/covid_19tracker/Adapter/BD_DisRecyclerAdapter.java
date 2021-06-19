package com.ronju.covid_19tracker.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.ronju.covid_19tracker.Model.Bd_dis_item;
import com.ronju.covid_19tracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class BD_DisRecyclerAdapter extends RecyclerView.Adapter<BD_DisRecyclerAdapter.ViewHolder> {
    private ArrayList<Bd_dis_item> bdDisItem;
    private Context context;
    //spring animation
    float currentVelocity = 0f;


    //


    public BD_DisRecyclerAdapter(ArrayList<Bd_dis_item> bdDisItem, Context context) {
        this.bdDisItem = bdDisItem;
        this.context = context;
       Collections.sort(bdDisItem,dataDescending);
    }
    public static Comparator<Bd_dis_item> dataDescending = new Comparator<Bd_dis_item>() {
        @Override
        public int compare(Bd_dis_item o1, Bd_dis_item o2) {
            return o2.getConfirm()-o1.getConfirm();
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bd_recyler_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( BD_DisRecyclerAdapter.ViewHolder holder, int position) {
        holder.disNameEn.setText(bdDisItem.get(position).getDisNameEn());
        holder.disNameBn.setText(bdDisItem.get(position).getDisNameBn());
        holder.caseConfirmed.setText(String.valueOf(bdDisItem.get(position).getConfirm()));
        holder.counter.setText((position + 1) +".");




        if((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return bdDisItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView disNameEn,disNameBn,caseConfirmed,counter;
        public ViewHolder( View itemView) {
            super(itemView);
            disNameEn = itemView.findViewById(R.id.disNameEng);
            disNameBn = itemView.findViewById(R.id.disNameBn);
            caseConfirmed = itemView.findViewById(R.id.confirmedCase);
            counter = itemView.findViewById(R.id.counter);





        }
    }
}
