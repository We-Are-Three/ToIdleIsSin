package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen extends ScreenAdapter {
    private ToIdleIsSin game;
    private Stage stage;
    private Image splashImg;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    public SplashScreen(ToIdleIsSin game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
        stage = new Stage(gamePort);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MenuScreen(game));
            }
        };

        Texture splashTex = new Texture("wat.png");
        splashImg = new Image(splashTex);
        splashImg.setOrigin(splashImg.getWidth(), splashImg.getHeight());
        splashImg.setPosition(ToIdleIsSin.WIDTH / 2 - splashImg.getWidth(), ToIdleIsSin.HEIGHT - splashImg.getHeight());
        splashImg.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(1f, 1f, 2.5f, Interpolation.pow5),
                        moveTo(ToIdleIsSin.WIDTH / 2- splashImg.getWidth()/2, ToIdleIsSin.HEIGHT / 2 - splashImg.getHeight()/2, 2f, Interpolation.swing)),
                delay(0.25f), fadeOut(0.25f), run(transitionRunnable)));

        stage.addActor(splashImg);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
