package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
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
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;

public class LevelsScreen extends ScreenAdapter {
    ToIdleIsSin game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector3 touch = new Vector3();

    private TextureRegion background;

    private TextButton village;
    private TextButton campaign;
    private TextButton story;
    private Stage stage;
    private ImageButton l1;
    private ImageButton l2;
    private ImageButton l3;
    private ImageButton l4;
    private ImageButton l5;
    private ImageButton l6;
    private ImageButton l7;


    public LevelsScreen(ToIdleIsSin game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();

        background = game.atlas.findRegion("campaign/levels");
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
    public void dispose() {
        stage.dispose();
    }

    private void initButtons(){
        village = NavButtons.getVillage(game);
        campaign = NavButtons.getCampaign(game);
        story = NavButtons.getStory(game);

        l1 = new ImageButton(game.skin, "l1");
        l1.setPosition((int)(ToIdleIsSin.WIDTH*0.106), (int)(ToIdleIsSin.HEIGHT*0.372));
        l1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!l1.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("campaign1");
                    ToIdleIsSin.program.run("one");
                    game.setScreen(new LevelScreen(game, 1));
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("charity").getProgress() == 0){
            l1.setDisabled(true);
        }

        l2 = new ImageButton(game.skin, "l2");
        l2.setPosition((int)(ToIdleIsSin.WIDTH*0.26), (int)(ToIdleIsSin.HEIGHT*0.405));
        l2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!l2.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("campaign2");
                    ToIdleIsSin.program.run("one");
                    game.setScreen(new LevelScreen(game, 2));
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("kindness").getProgress() == 0){
            l2.setDisabled(true);
        }

        l3 = new ImageButton(game.skin, "l3");
        l3.setPosition((int)(ToIdleIsSin.WIDTH*0.51), (int)(ToIdleIsSin.HEIGHT*0.466));
        l3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!l3.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("campaign3");
                    ToIdleIsSin.program.run("one");
                    game.setScreen(new LevelScreen(game, 3));
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("diligence").getProgress() == 0){
            l3.setDisabled(true);
        }

        l4 = new ImageButton(game.skin, "l4");
        l4.setPosition((int)(ToIdleIsSin.WIDTH*0.655), (int)(ToIdleIsSin.HEIGHT*0.538));
        l4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!l4.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("campaign4");
                    ToIdleIsSin.program.run("one");
                    game.setScreen(new LevelScreen(game, 4));
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("humility").getProgress() == 0){
            l4.setDisabled(true);
        }

        l5 = new ImageButton(game.skin, "l5");
        l5.setPosition((int)(ToIdleIsSin.WIDTH*0.283), (int)(ToIdleIsSin.HEIGHT*0.562));
        l5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!l5.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("campaign5");
                    ToIdleIsSin.program.run("one");
                    game.setScreen(new LevelScreen(game, 5));
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("chastity").getProgress() == 0){
            l5.setDisabled(true);
        }

        l6 = new ImageButton(game.skin, "l6");
        l6.setPosition((int)(ToIdleIsSin.WIDTH*0.245), (int)(ToIdleIsSin.HEIGHT*0.678));
        l6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!l6.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("campaign6");
                    ToIdleIsSin.program.run("one");
                    game.setScreen(new LevelScreen(game, 6));
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("patience").getProgress() == 0){
            l6.setDisabled(true);
        }

        l7 = new ImageButton(game.skin, "l7");
        l7.setPosition((int)(ToIdleIsSin.WIDTH*0.353), (int)(ToIdleIsSin.HEIGHT*0.788));
        l7.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!l7.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("campaign7");
                    ToIdleIsSin.program.run("one");
                    game.setScreen(new LevelScreen(game, 7));
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("temperance").getProgress() == 0){
            l7.setDisabled(true);
        }

        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(l1);
        stage.addActor(l2);
        stage.addActor(l3);
        stage.addActor(l5);
        stage.addActor(l4);
        stage.addActor(l6);
        stage.addActor(l7);

    }
}
