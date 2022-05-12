package com.example.mountainpeaksquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import DataObjects.Player;

public class MainActivity extends AppCompatActivity{

    private Button button, showResultsButton;
    private EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_start);
        showResultsButton = findViewById(R.id.btn_result);
        name = findViewById(R.id.et_name);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMountainPeaksQuizActivity(name.getText().toString());
            }
        });

        showResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShowPlayerResults();
            }
        });
    }

    void restartQuiz(){
            this.startActivity(new Intent(this, MainActivity.class));
    }


    public void openMountainPeaksQuizActivity(String userName){
        if (userName.isEmpty()){
            new AlertDialog.Builder(this)
                .setTitle("Грешка!")
                .setMessage("Моля въведете име за да зпочнете играта.")
                .setPositiveButton("Започни отначло", (dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();
        }else{
            Intent intent = new Intent(this, MountainPeaksQuizActivity.class);
            intent.putExtra("key", userName);
            startActivity(intent);
        }

    }

    public void openShowPlayerResults(){
        Intent intent = new Intent(this, ShowPlayersResults.class);
        startActivity(intent);
    }
}