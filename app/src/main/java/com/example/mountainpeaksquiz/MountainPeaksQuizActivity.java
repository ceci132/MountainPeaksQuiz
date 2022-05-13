package com.example.mountainpeaksquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

import DataObjects.Player;

public class MountainPeaksQuizActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mediaPlayer;

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button answerA, answerB, answerC, answerD;
    Button submitButton;
    ProgressBar progressBar;

    int score = 0;
    int totalQuestions = 7;
    int currentQuestionIndex;
    String selectedAnswer = "";
    int counter = 0;

    static int questionsArray[] = new int[8];

    private TextView countDownText;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000; //1min
    private boolean timeRunning;

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mountain_peaks_quiz);

        countDownText = findViewById(R.id.timer);

        Intent intent = getIntent();
        player = new Player(intent.getStringExtra("key"), 0);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        answerA = findViewById(R.id.ans_A);
        answerB = findViewById(R.id.ans_B);
        answerC = findViewById(R.id.ans_C);
        answerD = findViewById(R.id.ans_D);
        submitButton = findViewById(R.id.submit_btn);

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerD.setOnClickListener(this);
        answerC.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : " +totalQuestions);

        currentQuestionIndex = generateRandomIndex();
        questionsArray[counter] = currentQuestionIndex;
        counter++;
        loadNewQuestion();
        //startTimer();
    }

    @Override
    public void onClick(View view) {

        answerA.setBackgroundColor(Color.WHITE);
        answerB.setBackgroundColor(Color.WHITE);
        answerC.setBackgroundColor(Color.WHITE);
        answerD.setBackgroundColor(Color.WHITE);

        progressBar = findViewById(R.id.progressBar);

        Button clickedButton = (Button) view;


        if (clickedButton.getId()==R.id.submit_btn){
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
                player.setPlayerScore(score);
            }

            currentQuestionIndex = generateRandomIndex();
            questionsArray[counter] = currentQuestionIndex;
            progressBar.incrementProgressBy(1);
            counter++;

            loadNewQuestion();


        }else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.YELLOW);
        }
    }

    void loadNewQuestion(){

        if(counter == 8 ) {
            playVictorySong();
            finishQuiz();
            counter = 0;
            return;

        }
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        answerA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        answerB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        answerC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        answerD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }



    void finishQuiz(){
        Intent intent = new Intent(this, QuizRezultActivity.class);
        intent.putExtra("key", player.getUserName());
        intent.putExtra("score", player.getPlayerScore());
        startActivity(intent);
     }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        this.startActivity(new Intent(this, MainActivity.class));
    }

    void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timesUp();
            }
        }.start();

        timeRunning = true;
    }

    void updateTimer(){
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000/1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";

        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);
    }

    void timesUp(){
        playFailSong();
        new AlertDialog.Builder(this)
                .setTitle("Провалихте се!")
                .setMessage("Времето Ви изтече! Ще трябва да започнете отначало.")
                .setPositiveButton("Започни отначло", (dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();
    }

    void playVictorySong(){
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.song);
        }
        mediaPlayer.start();
    }

    void playFailSong(){
        if (mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(this, R.raw.failsound);
        }
        mediaPlayer.start();
    }

    private int generateRandomIndex(){
        Random random = new Random();

        int result = random.nextInt(QuestionAnswer.question.length);

        return result;
    }


}