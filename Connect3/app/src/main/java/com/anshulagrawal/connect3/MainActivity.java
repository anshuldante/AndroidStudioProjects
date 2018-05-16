package com.anshulagrawal.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private int activePlayer = 1;
    private boolean isgameActive = true;
    private Set<ImageView> usedBoxes = new HashSet<>();

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    // 2 means unplayed

    public void playAgain(View view) {
        LinearLayout layout = findViewById(R.id.layout);
        layout.setVisibility(View.INVISIBLE);
        for (int i = 0; i < 9; i++) {
            gameState[i] = 2;
        }
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 9; i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        activePlayer = 1;
        isgameActive = true;
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int counterIndex = Integer.parseInt(counter.getTag().toString());

        if (gameState[counterIndex] == 2 && isgameActive) {
            gameState[counterIndex] = activePlayer;
            usedBoxes.add(counter);
            counter.setTranslationY(-1000f);

            counter.setImageResource(activePlayer == 0 ? R.drawable.red : R.drawable.yellow);
            activePlayer = activePlayer == 0 ? 1 : 0;

            counter.animate().translationYBy(1000f).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    System.out.println(gameState[winningPosition[0]]);
                    // Someone has won!

                    TextView text = (TextView) findViewById(R.id.text);
                    text.setText((gameState[winningPosition[0]] == 0 ? "Red" : "Yellow") + "  has won!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
                    layout.setVisibility(View.VISIBLE);
                } else {
                    boolean isGameOver = true;
                    for (int i : gameState) {
                        if (i == 2)
                            isGameOver = false;
                    }
                    if (isGameOver) {
                        TextView text = (TextView) findViewById(R.id.text);
                        text.setText("It's a draw!");
                        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
