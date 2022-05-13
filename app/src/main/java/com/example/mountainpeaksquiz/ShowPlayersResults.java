package com.example.mountainpeaksquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShowPlayersResults extends AppCompatActivity {

    LinearLayout linearLayout1;
    Button button;

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_players_results);

        button = findViewById(R.id.backButton);

        button.setOnClickListener(v -> openMainActivity());

        readFromResultsFile();
    }

    void readFromResultsFile() {

        linearLayout1 = findViewById(R.id.cardViewInfo);

        try (BufferedReader input = new BufferedReader(new InputStreamReader(openFileInput("PlayerResult.txt")))) {
            String st;

            while ((st = input.readLine()) != null) {

                TextView textView = new TextView(this);
                textView.setText(st);
                textView.setLayoutParams(params);
                linearLayout1.addView(textView);
            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}