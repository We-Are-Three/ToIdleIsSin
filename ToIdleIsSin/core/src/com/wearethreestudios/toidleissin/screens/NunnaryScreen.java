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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Monks;
import com.wearethreestudios.toidleissin.program.Nuns;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.Hints;

public class NunnaryScreen extends ScreenAdapter {
    ToIdleIsSin game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector3 touch = new Vector3();

    private TextureRegion background;

    private TextButton village;
    private TextButton campaign;
    private TextButton story;
    private Stage stage;

    private long currentTouch;
    private double touchModifier = 1;

    private String job1Command = "farming";
    private String job2Command = "nuntraining";
    private String job3Command = "studying";
    private String job4Command = "praying";

    private TextButton idleunit;
    private TextButton job1;
    private TextButton left1;
    private TextButton right1;
    private TextButton job2;
    private TextButton left2;
    private TextButton right2;
    private TextButton job3;
    private TextButton left3;
    private TextButton right3;
    private TextButton job4;
    private TextButton left4;
    private TextButton right4;

    private Hints farming;
    private Hints training;
    private Hints studying;
    private Hints praying;

    public NunnaryScreen(ToIdleIsSin game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();
        initHints();
        background = game.atlas.findRegion("village/nunnary/nunnary_inside");
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        if(currentTouch > 0){
            touchModifier = 1 + (Program.realTime() - currentTouch)/200.0;
        }
        if(left1.isPressed()){
            Program.run(job1Command);
            Program.run("-" + (int)(1 * touchModifier));
        }
        if(right1.isPressed()){
            Program.run(job1Command);
            Program.run("" + (int)(1 * touchModifier));
        }
        if(left2.isPressed()){
            Program.run(job2Command);
            Program.run("-" + (int)(1 * touchModifier));
        }
        if(right2.isPressed()){
            Program.run(job2Command);
            Program.run("" + (int)(1 * touchModifier));
        }
        if(left3.isPressed()){
            Program.run(job3Command);
            Program.run("-" + (int)(1 * touchModifier));
        }
        if(right3.isPressed()){
            Program.run(job3Command);
            Program.run("" + (int)(1 * touchModifier));
        }
        if(left4.isPressed()){
            Program.run(job4Command);
            Program.run("-" + (int)(1 * touchModifier));
        }
        if(right4.isPressed()){
            Program.run(job4Command);
            Program.run("" + (int)(1 * touchModifier));
        }
        idleunit.setText("Idle\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getIdle());
        job1.setText("Farm\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getFarming());
        job2.setText("Train\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getTraining());
        job3.setText("Study\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getStudying());
        job4.setText("Pray\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getPraying());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);

        game.batch.end();
        stage.act(delta);
        stage.draw();
    }

    public void initHints(){
        farming = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Farming", "Farms food for all recruits. Factors into max units allowed.", "ui/icon");
        farming.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -farming.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -farming.getPopup().getHeight()/2));
        TextureRegionDrawable a = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        farming.getPopup().background(a);
        stage.addActor(farming.getPopup());


        training = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Training", "Trains Nuns into Physicians.", "ui/icon");
        training.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -training.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -training.getPopup().getHeight()/2));
        TextureRegionDrawable b = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        training.getPopup().background(b);
        stage.addActor(training.getPopup());


        studying = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Studying", "Studying increases the rate that perk points are earned. They can be spent in the Cathedral.", "ui/icon");
        studying.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -studying.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -studying.getPopup().getHeight()/2));
        TextureRegionDrawable c = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        studying.getPopup().background(c);
        stage.addActor(studying.getPopup());


        praying = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Praying", "The nuns will pray for those on the front line. Increases battle performance for all units.", "ui/icon");
        praying.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -praying.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -praying.getPopup().getHeight()/2));
        TextureRegionDrawable d = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        praying.getPopup().background(d);
        stage.addActor(praying.getPopup());
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
                currentTouch = Program.realTime();
                farming.getPopup().setVisible(false);
                training.getPopup().setVisible(false);
                studying.getPopup().setVisible(false);
                praying.getPopup().setVisible(false);
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                currentTouch = 0;
                touchModifier = 1;
                return super.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE){
                    game.setScreen(new VillageScreen(game));
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
        stage.dispose();
    }

    private void initButtons(){
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

        idleunit = new TextButton("Idle", game.skin, "idle");
        idleunit.setPosition((int)(ToIdleIsSin.WIDTH*0.1-idleunit.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-idleunit.getHeight()/2));
        idleunit.getLabel().setWrap(true);
        idleunit.getLabel().setAlignment(Align.center);

        job1 = new TextButton("Farm", game.skin, "job");
        job1.setSize(200,200);
        job1.setPosition((int)(ToIdleIsSin.WIDTH*0.2-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job1.getHeight()/2));
        job1.getLabel().setWrap(true);
        job1.getLabel().setAlignment(Align.center);
        job1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                farming.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left1 = new TextButton("", game.skin, "left");
        left1.setSize(100,100);
        left1.setPosition((int)(ToIdleIsSin.WIDTH*0.2-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left1.getHeight()/2));

        right1 = new TextButton("", game.skin, "right");
        right1.setSize(100,100);
        right1.setPosition((int)(ToIdleIsSin.WIDTH*0.2), (int)(ToIdleIsSin.HEIGHT*0.17-right1.getHeight()/2));



        job2 = new TextButton("Train", game.skin, "job");
        job2.setSize(200,200);
        job2.setPosition((int)(ToIdleIsSin.WIDTH*0.4-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job2.getHeight()/2));
        job2.getLabel().setWrap(true);
        job2.getLabel().setAlignment(Align.center);
        job2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                training.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left2 = new TextButton("", game.skin, "left");
        left2.setSize(100,100);
        left2.setPosition((int)(ToIdleIsSin.WIDTH*0.4-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left2.getHeight()/2));

        right2 = new TextButton("", game.skin, "right");
        right2.setSize(100,100);
        right2.setPosition((int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.17-right2.getHeight()/2));



        job3 = new TextButton("Study", game.skin, "job");
        job3.setSize(200,200);
        job3.setPosition((int)(ToIdleIsSin.WIDTH*0.6-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job3.getHeight()/2));
        job3.getLabel().setWrap(true);
        job3.getLabel().setAlignment(Align.center);
        job3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                studying.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left3 = new TextButton("", game.skin, "left");
        left3.setSize(100,100);
        left3.setPosition((int)(ToIdleIsSin.WIDTH*0.6-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left3.getHeight()/2));

        right3 = new TextButton("", game.skin, "right");
        right3.setSize(100,100);
        right3.setPosition((int)(ToIdleIsSin.WIDTH*0.6), (int)(ToIdleIsSin.HEIGHT*0.17-right3.getHeight()/2));



        job4 = new TextButton("Pray", game.skin, "job");
        job4.setSize(200,200);
        job4.setPosition((int)(ToIdleIsSin.WIDTH*0.8-job4.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job4.getHeight()/2));
        job4.getLabel().setWrap(true);
        job4.getLabel().setAlignment(Align.center);
        job4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                praying.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left4 = new TextButton("", game.skin, "left");
        left4.setSize(100,100);
        left4.setPosition((int)(ToIdleIsSin.WIDTH*0.8-job4.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left4.getHeight()/2));

        right4 = new TextButton("", game.skin, "right");
        right4.setSize(100,100);
        right4.setPosition((int)(ToIdleIsSin.WIDTH*0.8), (int)(ToIdleIsSin.HEIGHT*0.17-right4.getHeight()/2));

        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(idleunit);
        stage.addActor(job1);
        stage.addActor(job2);
        stage.addActor(job3);
        stage.addActor(job4);
        stage.addActor(left1);
        stage.addActor(right1);
        stage.addActor(left2);
        stage.addActor(right2);
        stage.addActor(left3);
        stage.addActor(right3);
        stage.addActor(left4);
        stage.addActor(right4);

    }
}