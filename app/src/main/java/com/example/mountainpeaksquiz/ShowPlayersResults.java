package com.example.mountainpeaksquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ShowPlayersResults extends AppCompatActivity {

    LinearLayout linearLayout1;


    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_players_results);

        readFormResultsFile(ShowPlayersResults.this);

    }

    void readFormResultsFile(Context context) {

        linearLayout1 = findViewById(R.id.cardViewInfo);

        try (BufferedReader input = new BufferedReader(new InputStreamReader(openFileInput("PlayerResult.txt")))) {
            String st;

            while ((st= input.readLine()) != null){

                TextView textView = new TextView(this);
                textView.setText(st);
                textView.setLayoutParams(params);
                linearLayout1.addView(textView);
            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}