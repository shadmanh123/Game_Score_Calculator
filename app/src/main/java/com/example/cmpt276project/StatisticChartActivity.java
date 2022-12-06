package com.example.cmpt276project;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;

import java.util.ArrayList;
import java.util.HashMap;


public class StatisticChartActivity extends AppCompatActivity {
    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private GameManager gameManager;
    private JsonReader jsonReader;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_chart);
        int index = getIntent().getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        game = gameManager.getGame(index);

        setOnClick(index);

        setBarChart();
//        setPieChart();
    }

    private void setBarChart() {
        BarChart barGraph =  (BarChart) findViewById(R.id.barGraph);

        //initializing array
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        HashMap<Integer, Integer> barLvlStats = game.achievementStatistics();

        for (int i = 0; i < 10; i++){
            float value = (float) barLvlStats.get(i + 1);
            BarEntry barEntry= new BarEntry(i, value);
            barEntries.add(barEntry);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "NUMBER OF TIMES LEVEL WAS ACHIEVED");
//        barDataSet.setDrawValues (false);
//
        barGraph.setData(new BarData(barDataSet));
        barGraph.animateY(5000);

        final ArrayList<Integer> colours = new ArrayList<>();
        colours.add(Color.parseColor("#0000ff")); //blue
        colours.add(Color.parseColor("#0047ab")); //cobalt blue
        colours.add(Color.parseColor("#6495ed")); //corn flower blue
        colours.add(Color.parseColor("#6082b6")); //glaucous
        colours.add(Color.parseColor("#a7c7e7")); //pastel blue
        colours.add(Color.parseColor("#b6d0e2")); //powder blue
        colours.add(Color.parseColor("#96ded1")); //robin egg blue
        colours.add(Color.parseColor("#9fe2bf")); //seafoam green
        colours.add(Color.parseColor("#40b5ad")); //verdigris
        colours.add(Color.parseColor("#088f8f")); //blue green

        barDataSet.setColors(colours);

        barGraph.getDescription().setEnabled(false);
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Level 1");
        xAxisLabel.add("Level 2");
        xAxisLabel.add("Level 3");
        xAxisLabel.add("Level 4");
        xAxisLabel.add("Level 5");
        xAxisLabel.add("Level 6");
        xAxisLabel.add("Level 7");
        xAxisLabel.add("Level 8");
        xAxisLabel.add("Level 9");
        xAxisLabel.add("Level 10");


        XAxis xAxis = barGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);
            }
        };

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        barGraph.getXAxis().setGranularity(1);
        barGraph.getXAxis().setTextColor(Color.GRAY);
        barGraph.getXAxis().setGranularityEnabled(true);
    }

    /*private void setPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
        HashMap<Integer, Integer> pieLvlStats = game.achievementStatistics();

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            float value = (float) pieLvlStats.get(i + 1);
            float value = (float) (i*10.0);
            PieEntry pieEntry= new PieEntry(i, value);
            pieEntries.add(pieEntry);
        }
        ArrayList<String> Level = new ArrayList<>();
        for (int j= 0; j < 10; j++){
            Level.add(levels[j]);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Level Statistics");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateY(5000);

        pieChart.getDescription().setText("Other Things chart");
        pieChart.getDescription().setTextColor(Color.BLUE);
    }*/


    private void setOnClick(int index) {
        Button table = findViewById(R.id.statsTable);
        table.setOnClickListener(v -> {
            finish();
        });
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, StatisticChartActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }
}