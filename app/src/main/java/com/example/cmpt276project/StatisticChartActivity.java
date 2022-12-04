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

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;

import java.util.ArrayList;
import java.util.HashMap;

//Todo: add titles for achievement statistics xml page and statistics chart xml page
// possibly for statistics chart page figure out how to get the page to scroll

public class StatisticChartActivity extends AppCompatActivity {
    private GameManager gameManager;
    private JsonReader jsonReader;
    private Game game;

    private String[] levels = {"Level 1", "Level 2","Level 3","Level 4","Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_chart);
        int index = getIntent().getIntExtra("index", 0);
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        game = gameManager.getGame(index);

        setOnClick(index);

        //Todo: get bar graph and pie chart to show actual data
        setBarChart();
        setPieChart();
    }

    private void setBarChart() {
        BarChart barGraph=  (BarChart) findViewById(R.id.barGraph);

        //initializing array
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        HashMap<Integer, Integer> barLvlStats = game.achievementStatistics();

        for (int i = 0; i < 10; i++){
            float value = (float) barLvlStats.get(i + 1);
//            float value = (float) (i*10.0);
            BarEntry barEntry= new BarEntry(i, value);
            barEntries.add(barEntry);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "things");
//        barDataSet.setColor(ColorTemplate.COLORFUL_COLORS[1]);
//        barDataSet.setDrawValues (false);
        barGraph.setData(new BarData(barDataSet));
        barGraph.animateY(5000);

        barGraph.getDescription().setText("Things chart");
        barGraph.getDescription().setTextColor(Color.BLUE);

    }

    private void setPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
        HashMap<Integer, Integer> pieLvlStats = game.achievementStatistics();

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            float value = (float) pieLvlStats.get(i + 1);
            PieEntry pieEntry= new PieEntry(i, value);
            pieEntries.add(pieEntry);
        }
        ArrayList<String> Level = new ArrayList<>();
        for (int j= 0; j < 10; j++){
            Level.add(levels[j]);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Level Statistics");
        pieDataSet.setSliceSpace(2);
//        pieDataSet.setValueTextSize(12);

        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateY(5000);

        pieChart.getDescription().setText("Other Things chart");
        pieChart.getDescription().setTextColor(Color.BLUE);
    }

//    private void getGameStats(){
//        HashMap<Integer, Integer> lvlStats = game.achievementStatistics();
//
//        for (int i = 0; i < 10; i++){
//            float value = (float) lvlStats.get(i + 1);
//            BarEntry barEntry= new BarEntry(i, value);
//            barEntries.add(barEntry);
//        }
//    }


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