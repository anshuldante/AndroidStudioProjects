package com.anshulagrawal.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    //    private ShapeRenderer shapeRenderer;
    private int gameState = 0;
    private int score = 0;
    private int scoringTube = 0;

    Texture gameOver;

    //Score related
    BitmapFont font;

    // pipe stuff
    private Texture topTube;
    private Texture bottomTube;
    private float gap = 400;
    private float maxTubeOffset;
    private Random randomGenerator;
    private float tubeVelocity = 4;
    private int numberOfTubes = 4;


    private int tubeX[] = new int[numberOfTubes];
    private float tubeOffset[] = new float[numberOfTubes];
    private Rectangle topPipeRectangle[];
    private Rectangle bottomPipeRectangle[];
    private int distanceBetweenTubes;

    // bird stuff
    private Texture[] birds;
    private Texture birdToDisplay;
    private boolean flapState;

    private float birdY = 0;
    private float velocity = 0;
    private float gravity = 1.5f;
    private float acceleration = -30;
    private Circle birdCircle;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);

//        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        topPipeRectangle = new Rectangle[numberOfTubes];
        bottomPipeRectangle = new Rectangle[numberOfTubes];

        gameOver = new Texture("gameover.png");


        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = (Gdx.graphics.getHeight() - birds[0].getWidth()) / 2;


        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

        startGame();
    }

    @Override
    public void render() {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            //if (tubeX[scoringTube] <= (Gdx.graphics.getWidth() / 2 + birds[0].getWidth()) && tubeX[i] > (Gdx.graphics.getWidth() / 2 + birds[0].getWidth() - 4)) {
            if (tubeX[scoringTube] <= Gdx.graphics.getWidth() / 2) {
                score++;
                scoringTube = (scoringTube + 1) % numberOfTubes;
                Gdx.app.log("Score: ", Integer.toString(score));
                Gdx.app.log("Tube X: ", Integer.toString(tubeX[scoringTube]));
            }

            if (Gdx.input.justTouched()) {

                velocity = acceleration;

            }

            for (int i = 0; i < numberOfTubes; i++) {
                if (tubeX[i] < -topTube.getWidth()) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * maxTubeOffset * 2;
                } else {
                    tubeX[i] -= tubeVelocity;
                }

                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                topPipeRectangle[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                bottomPipeRectangle[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
            }

            if (birdY > 0) {
                velocity += gravity;
                birdY -= velocity;
            } else {
                gameState = 2;
            }

        } else if (gameState == 0) {

            if (Gdx.input.justTouched()) {

                gameState = 1;

            }
        } else if (gameState == 2) {
            batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
            if (Gdx.input.justTouched()) {

                gameState = 1;
                startGame();
                score = 0;
                scoringTube = 0;
                velocity = 0;


            }
        }

        flapState = !flapState;
        birdToDisplay = flapState ? birds[0] : birds[1];

        batch.draw(birdToDisplay, (Gdx.graphics.getWidth() - birdToDisplay.getWidth()) / 2, birdY);

        font.draw(batch, String.valueOf(score), 100, 200);

        birdCircle.set(Gdx.graphics.getWidth() / 2, birds[0].getHeight() / 2 + birdY, birds[0].getWidth() / 2);

        for (int i = 0; i < numberOfTubes; i++) {
            if (Intersector.overlaps(birdCircle, topPipeRectangle[i]) || Intersector.overlaps(birdCircle, bottomPipeRectangle[i])) {
                Gdx.app.log("Collision detected!!", "Damn!");
                gameState = 2;
            }
        }
        batch.end();

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//
//        for (int i = 0; i < numberOfTubes; i++) {
//
//            shapeRenderer.rect(topPipeRectangle[i].x, topPipeRectangle[i].y, topPipeRectangle[i].width, topPipeRectangle[i].height);
//            shapeRenderer.rect(bottomPipeRectangle[i].x, bottomPipeRectangle[i].y, bottomPipeRectangle[i].width, bottomPipeRectangle[i].height);
//
//
//        }
//
//
//        shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
//        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }

    private void startGame() {
        birdY = (Gdx.graphics.getHeight() - birds[0].getWidth()) / 2;
        for (int i = 0; i < numberOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * maxTubeOffset * 2;
            tubeX[i] = Gdx.graphics.getWidth() / 2 - bottomTube.getWidth() / 2 + Gdx.graphics.getWidth() / 2 + i * distanceBetweenTubes;

            topPipeRectangle[i] = new Rectangle();
            bottomPipeRectangle[i] = new Rectangle();
        }
    }
}
