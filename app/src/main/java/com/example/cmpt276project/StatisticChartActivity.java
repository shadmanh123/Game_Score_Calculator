package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticChartActivity extends AppCompatActivity {

    //Initialize variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_chart);
        int index = getIntent().getIntExtra("index", 0);

        setOnClick(index);
        setBarChart();
        setPieChart();
    }

    private void setPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            float value = (float) (i*10.0);
            PieEntry pieEntry= new PieEntry(i, value);
            pieEntries.add(pieEntry);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "other things");

        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateY(5000);

        pieChart.getDescription().setText("Other Things chart");
        pieChart.getDescription().setTextColor(Color.BLUE);
    }

    private void setBarChart() {
        BarChart barGraph=  (BarChart) findViewById(R.id.barGraph);

        //initializing array
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            float value = (float) (i*10.0);
            BarEntry barEntry= new BarEntry(i, value);
            barEntries.add(barEntry);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "things");
//        barDataSet.setColor(ColorTemplate.COLORFUL_COLORS.length);
//        barDataSet.setDrawValues (false);
        barGraph.setData(new BarData(barDataSet));
        barGraph.animateY(5000);

        barGraph.getDescription().setText("Things chart");
        barGraph.getDescription().setTextColor(Color.BLUE);

    }

    private void setOnClick(int index) {
        Button table = findViewById(R.id.statsTable);
        table.setOnClickListener(v -> {
            finish();
        });
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, StatisticChartActivity.class);
        intent.putExtra("index", gameIndex);
        return intent;
    }
}