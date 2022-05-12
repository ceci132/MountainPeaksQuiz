package com.example.mountainpeaksquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import DataObjects.Player;

public class QuizRezultActivity extends AppCompatActivity {

    LinearLayout linearLayout1;
    LinearLayout linearLayout;

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz_rezult);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("key");
        int playerScore = intent.getIntExtra("score", 0);


        linearLayout = findViewById(R.id.info);
        linearLayout1 = findViewById(R.id.cardViewInfo);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(20,20,20,20);

        for (int i = 0; i < QuestionAnswer.question.length ; i++) {

            TextView textView = new TextView(this);
            textView.setText(QuestionAnswer.question[i]);
            textView.setLayoutParams(params);
            linearLayout1.addView(textView);

            TextView textView1 = new TextView(this);
            textView1.setText(QuestionAnswer.correctAnswers[i]);
            textView1.setLayoutParams(params);
            linearLayout1.addView(textView1);

        }

        Button saveResult = new Button(this);
        saveResult.setText("Запази резултата!");
        linearLayout.addView(saveResult);

        Button returnToMenu = new Button(this);
        returnToMenu.setText("Започни нова игра!");
        linearLayout.addView(returnToMenu);

        returnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        saveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile(userName, playerScore, QuizRezultActivity.this);
            }
        });
    }

    public void openMainActivity(){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

    private void writeToFile (String name, int score, Context context) {

        String dirPath = context.getFilesDir().getAbsolutePath();
        String filePath = dirPath + "/PlayerResult";
        File resultsFile = new File("/storage/sdcard0/file.txt");

        try (OutputStreamWriter output = new OutputStreamWriter(openFileOutput("PlayerResult.txt", MODE_APPEND))) {
            output.write(name);
            output.write('-');
            output.write(Integer.toString(score));
            output.write("\n");
            output.flush();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
}