package com.corona.covid_19tracker;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartClass extends com.github.mikephil.charting.charts.PieChart {
    private  Context mContext;

    public PieChartClass(Context context) {
        super(context);
        mContext = context;
    }

    public PieChartClass(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public PieChartClass(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setupDataChart(PieChart pieChart) {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setCenterText("COVID-19");
        pieChart.setCenterTextSize(18);
        pieChart.getDescription().setEnabled(false);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextColor(Color.WHITE);
        l.setTextSize(11);
        l.setDrawInside(false);
        l.setEnabled(true);
    }
    public void loadPieChartData(PieChart pieChart,ArrayList<PieEntry> entries) {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
               for (int color : ColorTemplate.JOYFUL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.LIBERTY_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.PASTEL_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
       // dataSet.setColors(colors);
       dataSet.setColors(new int[]{R.color.yellow,R.color.green,R.color.purple_700,R.color.red},mContext);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(1500, Easing.EaseInOutQuad);


        dataSet.setValueLinePart1OffsetPercentage(90.f);
        dataSet.setValueLinePart1Length(.10f);
        dataSet.setValueLinePart2Length(.50f);


    }

}

