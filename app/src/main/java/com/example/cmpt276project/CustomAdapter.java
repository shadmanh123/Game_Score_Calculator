package com.example.cmpt276project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[][] play;
    LayoutInflater inflater;

    public CustomAdapter(Context GameHistory, String[][]play){
        this.context = GameHistory;
        this.play = play;
        inflater = LayoutInflater.from(GameHistory);
    }

    @Override
    public int getCount() {
        return play.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        view = inflater.inflate(R.layout.activity_game_history, null);

        return null;
    }
}
