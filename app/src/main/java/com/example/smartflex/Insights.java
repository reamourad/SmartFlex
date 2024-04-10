package com.example.smartflex;

import static com.example.smartflex.Database.balance;
import static com.example.smartflex.Database.income;
import static com.example.smartflex.Database.needsCategory;
import static com.example.smartflex.Database.savingsCategory;
import static com.example.smartflex.Database.wantsCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Insights extends AppCompatActivity {

    private  bottoms_icon BottomIconFragment;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);
        FragmentManager fragmentManager = getSupportFragmentManager();
        BottomIconFragment = (bottoms_icon) fragmentManager.findFragmentById(R.id.fragmentContainerView);

        pieChart = findViewById(R.id.pieChart_view);
        if(income != 0){
            showPieChart();
            initPieChart();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomIconFragment.setColorInsight();
    }

    private void showPieChart(){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        ArrayList<Integer> colors = new ArrayList<>();
        for(Category category : needsCategory){
            typeAmountMap.put(category.name, (int)category.cost);
        }
        for(Category category : wantsCategory){
            typeAmountMap.put(category.name, (int)category.cost);
        }
        for(Category category : savingsCategory){
            typeAmountMap.put(category.name, (int)category.cost);
        }
        typeAmountMap.put("Leftover", (int)balance);
        //initializing colors for the entries

        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        // Set text color for percentage values
        pieDataSet.setValueTextColor(Color.WHITE);


        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        pieChart.setUsePercentValues(true);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        // Set custom value formatter
        pieData.setValueFormatter(new DefaultValueFormatter(0) {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void initPieChart(){
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);

        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(false);

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#000000"));

        // Hide legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);


    }
}