package com.example.mountainpeaksquiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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


    @RequiresApi(api = Build.VERSION_CODES.M)
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
                addNotification();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addNotification() {


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, String.valueOf(MainActivity.class))
                .setSmallIcon(R.drawable.twittericon)
                .setContentTitle("Подсещане!")
                .setContentText("Не забравяй да публикуваш резултата си след като приключиш куиза!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, builder.build());
    }


    public void openMountainPeaksQuizActivity(String userName){
        if (userName.isEmpty()){
            new AlertDialog.Builder(this)
                .setTitle("Грешка!")
                .setMessage("Моля въведете име за да започнете играта.")
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