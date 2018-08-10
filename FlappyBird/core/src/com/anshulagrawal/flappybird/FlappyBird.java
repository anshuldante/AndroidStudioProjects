package com.anshulagrawal.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;

    Texture[] birds;
    Texture birdToDisplay;
    boolean flapState;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
    }

    @Override
    public void render() {
        flapState = !flapState;
        birdToDisplay = flapState ? birds[0] : birds[1];
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(birdToDisplay, (Gdx.graphics.getWidth() - birdToDisplay.getWidth()) / 2, (Gdx.graphics.getHeight() - birdToDisplay.getWidth()) / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
