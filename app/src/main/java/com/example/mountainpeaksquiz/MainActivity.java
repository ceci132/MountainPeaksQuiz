package com.example.mountainpeaksquiz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_start);
        Button showResultsButton = findViewById(R.id.btn_result);
        name = findViewById(R.id.et_name);

        button.setOnClickListener(v -> {
            openMountainPeaksQuizActivity(name.getText().toString());
            addNotification();
        });

        showResultsButton.setOnClickListener(v -> openShowPlayerResults());
    }

    void restartQuiz() {
        this.startActivity(new Intent(this, MainActivity.class));
    }

    public void addNotification() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String CHANNEL_ID = "CHANNEL";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.twitter_icon)
                .setContentTitle("Подсещане!")
                .setContentText("Не забравяй да публикуваш резултата си след като приключиш куиза!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, notification);
    }

    public void openMountainPeaksQuizActivity(String userName) {
        if (userName.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Грешка!")
                    .setMessage("Моля въведете име, за да започнете играта.")
                    .setPositiveButton("Започни отначло", (dialogInterface, i) -> restartQuiz())
                    .setCancelable(false)
                    .show();
        } else {
            Intent intent = new Intent(this, MountainPeaksQuizActivity.class);
            intent.putExtra("key", userName);
            startActivity(intent);
        }
    }

    public void openShowPlayerResults() {
        Intent intent = new Intent(this, ShowPlayersResults.class);
        startActivity(intent);
    }
}