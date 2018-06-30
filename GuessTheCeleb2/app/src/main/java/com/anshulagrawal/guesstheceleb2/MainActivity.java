package com.anshulagrawal.guesstheceleb2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.anshulagrawal.guesstheceleb2.task.ImageDownloader;
import com.anshulagrawal.guesstheceleb2.task.PageDownloadTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private ImageView image;
    private Button[] buttons = new Button[4];

    private ArrayList<String> celebImages = new ArrayList<>();
    private ArrayList<String> celebNames = new ArrayList<>();
    private Random random = new Random();
    private String[] answers = new String[4];
    private String correctAnswer;
    private int chosenCeleb;
    private int correctLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        image = findViewById(R.id.imageView);
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);

        PageDownloadTask task = new PageDownloadTask();
        try {
            String result = task.execute("http://www.posh24.se/kandisar").get();
            result = result.split("<div class=\"sidebarInnerContainer\">")[0];


            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(result);

            while (m.find()) {
                celebImages.add(m.group(1));
            }
            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(result);

            while (m.find()) {
                celebNames.add(m.group(1));
            }

            // Adding Goku's details as default to be used if an image is not found online
            celebNames.add("Goku");
            celebImages.add(Integer.toString(R.drawable.goku));
            randomizeCeleb();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void randomizeCeleb() throws ExecutionException, InterruptedException {
        chosenCeleb = random.nextInt(celebImages.size());
        Log.i("chosen celeb:", celebNames.get(chosenCeleb));

        Bitmap currentImage = new ImageDownloader().execute(celebImages.get(chosenCeleb)).get();
        if (currentImage != null) {
            image.setImageBitmap(currentImage);
            correctAnswer = celebNames.get(chosenCeleb);
        } else {
            image.setImageResource(R.drawable.goku);
            correctAnswer = "Goku";
        }
        correctLocation = random.nextInt(4);
        Log.i("Correct location : ", Integer.toString(correctLocation));

        for (int i = 0; i < 4; i++) {
            if (i == correctLocation) {
                answers[i] = celebNames.get(chosenCeleb);
            } else {
                answers[i] = celebNames.get(random.nextInt(celebImages.size()));
            }
        }
        for (int i = 0; i < 4; i++) {
            buttons[i].setText(answers[i]);
        }
    }

    public void buttonClicked(View view) {
        Log.i("Tag:: ", view.getTag().toString());
        Log.i("Correct:: ", Integer.toString(correctLocation));
        if (Integer.parseInt(view.getTag().toString()) == correctLocation) {
            Toast.makeText(this, "That's correct!!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Incorrect! it's " + celebNames.get(chosenCeleb), Toast.LENGTH_LONG).show();
        }
        try {
            randomizeCeleb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
