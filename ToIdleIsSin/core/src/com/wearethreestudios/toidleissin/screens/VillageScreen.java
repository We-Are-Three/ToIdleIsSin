package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Program;

public class VillageScreen extends ScreenAdapter {
    ToIdleIsSin game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector3 touch = new Vector3();

    private TextureRegion background;

    private TextButton village;
    private TextButton campaign;
    private TextButton story;
    private Stage stage;
    private ImageButton monastery;
    private ImageButton nunnary;
    private ImageButton barracks;
    private ImageButton cathedral;
    private ImageButton mines;

    private void initButtons(){

        monastery = new ImageButton(game.skin, "monastery");
        monastery.setPosition((int)(ToIdleIsSin.WIDTH*0.045), (int)(ToIdleIsSin.HEIGHT*0.685));
        monastery.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!monastery.isDisabled()){
                    game.setScreen(new MonasteryScreen(game));
                    ToIdleIsSin.program.run("monastery");
                }
                super.clicked(event, x, y);
            }
        });

        nunnary = new ImageButton(game.skin, "nunnary");
        nunnary.setPosition((int)(ToIdleIsSin.WIDTH*0.667), (int)(ToIdleIsSin.HEIGHT*0.49));
        nunnary.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!monastery.isDisabled()){
                    game.setScreen(new NunnaryScreen(game));
                    ToIdleIsSin.program.run("nunnary");
                }
                super.clicked(event, x, y);
            }
        });

        barracks = new ImageButton(game.skin, "barracks");
        barracks.setPosition((int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.44));
        barracks.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!monastery.isDisabled()){
                    game.setScreen(new BarracksScreen(game));
                    ToIdleIsSin.program.run("barracks");
                }
                super.clicked(event, x, y);
            }
        });

        cathedral = new ImageButton(game.skin, "cathedral");
        cathedral.setPosition((int)(ToIdleIsSin.WIDTH*0.339), (int)(ToIdleIsSin.HEIGHT*0.585));
        cathedral.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!monastery.isDisabled()){
                    game.setScreen(new CathedralScreen(game));
                    ToIdleIsSin.program.run("cathedral");
                }
                super.clicked(event, x, y);
            }
        });

        mines = new ImageButton(game.skin, "mines");
        mines.setPosition((int)(ToIdleIsSin.WIDTH*0.63), (int)(ToIdleIsSin.HEIGHT*0.67));
        mines.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!monastery.isDisabled()){
                    game.setScreen(new MinesScreen(game));
                }
                super.clicked(event, x, y);
            }
        });


        village = new TextButton("Village", game.skin, "navbutton");
        village.setPosition((int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.01));
        village.setSize(200, 200);
        village.setOrigin(Align.center);
        village.setTransform(true);
        village.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new VillageScreen(game));
                ToIdleIsSin.program.run("village");
                super.clicked(event, x, y);
            }
        });

        campaign = new TextButton("Campaign", game.skin, "navbutton");
        campaign.setPosition((int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.01));
        campaign.setSize(200, 200);
        campaign.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelsScreen(game));
                ToIdleIsSin.program.run("battles");
                super.clicked(event, x, y);
            }
        });

        story = new TextButton("Story", game.skin, "navbutton");
        story.setPosition((int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.01));
        story.setSize(200, 200);
        story.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StoryScreen(game));
                ToIdleIsSin.program.run("visual novel");
                super.clicked(event, x, y);
            }
        });

        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(monastery);
        stage.addActor(nunnary);
        stage.addActor(barracks);
        stage.addActor(cathedral);
        stage.addActor(mines);

    }

    public VillageScreen(ToIdleIsSin game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();
        background = game.atlas.findRegion("village/village");

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
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {
        InputProcessor general = new InputAdapter(){

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                gamePort.unproject(touch.set(screenX, screenY, 0));
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE){
                    game.setScreen(new MenuScreen(game));
                }
                return super.keyDown(keycode);
            }
        };
        InputMultiplexer mult = new InputMultiplexer();
        mult.addProcessor(general);
        mult.addProcessor(stage);
        Gdx.input.setInputProcessor(mult);

    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
