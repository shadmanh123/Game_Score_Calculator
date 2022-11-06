package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreditsActivity extends AppCompatActivity {
    public static Intent makeIntent(Context context){
        return new Intent(context, CreditsActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        setupHyperlink();
    }
    private void setupHyperlink() {
        // set hyperlink
        TextView linkTextView = findViewById(R.id.tvCredits);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}