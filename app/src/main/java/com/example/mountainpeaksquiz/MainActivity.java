package com.example.mountainpeaksquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_start);
        showResultsButton = findViewById(R.id.btn_result);
        name = findViewById(R.id.et_name);


        addNotification();

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

    private void addNotification() {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.img)
                .setContentTitle("John's Android Studio Tutorials")
                .setContentText("A video has just arrived!");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
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