package com.anshulagrawal.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private TextView[] answerTextBoxArr;
    private TextView timerTextBox;
    private TextView scoreTextBox;
    private TextView questionTestBox;

    private CountDownTimer countDownTimer;
    private Button playAgainButton;

    Random random = new Random();

    private int totalAnswered;
    private int totalCorrect;
    private int correctAnswer;

    private boolean isGameInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAgainButton = findViewById(R.id.playAgainButton);

        timerTextBox = findViewById(R.id.timerTextBox);
        scoreTextBox = findViewById(R.id.scoreTextBox);
        questionTestBox = findViewById(R.id.questionTestBox);

        answerTextBoxArr = new TextView[4];
        answerTextBoxArr[0] = findViewById(R.id.answerATB);
        answerTextBoxArr[1] = findViewById(R.id.answerBTB);
        answerTextBoxArr[2] = findViewById(R.id.answerCTB);
        answerTextBoxArr[3] = findViewById(R.id.answerDTB);
    }

    public void enableGame(View view) {
        findViewById(R.id.entryButton).setVisibility(View.GONE);
        findViewById(R.id.brainTrainerGodLayout).setVisibility(View.VISIBLE);
        startTimer();
    }

    public void startTimer() {
        isGameInProgress = true;
        buildNewQuestion();
        setScore();
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextBox.setText(Integer.toString((int) Math.ceil(millisUntilFinished / 1000)) + "s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "You ran out of time!! Your score is: " + totalCorrect + "/" + totalAnswered, Toast.LENGTH_LONG).show();
                isGameInProgress = false;
                totalCorrect = 0;
                totalAnswered = 0;
                correctAnswer = -1;
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void playAgain(View view) {
        view.setVisibility(View.INVISIBLE);
        startTimer();
    }

    public void submitAnswer(View view) {
        if (isGameInProgress) {
            Log.i("Clicked box is: ", view.getTag().toString());
            Log.i("Correct answer is at: ", Integer.toString(correctAnswer));
            totalAnswered++;
            if (correctAnswer == Integer.parseInt(view.getTag().toString())) {
                totalCorrect++;
            }

            setScore();
            buildNewQuestion();
        }
    }


    private void setScore() {
        scoreTextBox.setText(totalCorrect + "/" + totalAnswered);
    }

    private void buildNewQuestion() {
        int argumentOne = getRandomInt(49, 1);
        int argumentTwo = getRandomInt(49, 1);
        int answer = argumentOne + argumentTwo;

        correctAnswer = getRandomInt(4);

        questionTestBox.setText(argumentOne + " + " + argumentTwo);
        answerTextBoxArr[0].setText(Integer.toString(getRandomInt(99, 1, answer)));
        answerTextBoxArr[1].setText(Integer.toString(getRandomInt(99, 1, answer)));
        answerTextBoxArr[2].setText(Integer.toString(getRandomInt(99, 1, answer)));
        answerTextBoxArr[3].setText(Integer.toString(getRandomInt(99, 1, answer)));

        answerTextBoxArr[correctAnswer].setText(Integer.toString(answer));
    }

    public int getRandomInt(int bound, int offset, int exclusion) {
        int result = exclusion;
        while (result == exclusion) {
            result = random.nextInt(bound) + offset;
        }
        return result;
    }

    public int getRandomInt(int bound, int offset) {
        return random.nextInt(bound) + offset;
    }

    public int getRandomInt(int bound) {
        return random.nextInt(bound);
    }
}
