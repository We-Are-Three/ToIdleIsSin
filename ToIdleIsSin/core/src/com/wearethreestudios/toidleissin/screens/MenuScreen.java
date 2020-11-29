package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;

public class MenuScreen extends ScreenAdapter {
    private ToIdleIsSin game;
    private Texture background;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private ShapeRenderer shapeRenderer;
    private float progress;

    public MenuScreen(ToIdleIsSin game){
        this.game = game;
        background = new Texture("menu.png");
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        this.progress = 0f;
        game.queueAssets();
    }


    private void update(float delta) {
        progress = MathUtils.lerp(progress, game.assets.getProgress(), .1f);
        if (game.assets.update() && progress >= game.assets.getProgress() - .001f) {
            game.atlas = game.assets.get("atlas/atlassprites.atlas", TextureAtlas.class);
            game.loadSkins();
            game.setScreen(new VillageScreen(game));
            ToIdleIsSin.program.run("village");
        }
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        game.batch.end();

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect((int)(ToIdleIsSin.WIDTH*0.1), gamePort.getScreenHeight() / 2 - 8, ToIdleIsSin.WIDTH - (int)(ToIdleIsSin.WIDTH*0.2), 16);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect((int)(ToIdleIsSin.WIDTH*0.1), gamePort.getScreenHeight() / 2 - 8, progress * (ToIdleIsSin.WIDTH - (int)(ToIdleIsSin.WIDTH*0.2)), 16);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        shapeRenderer.dispose();
    }
}