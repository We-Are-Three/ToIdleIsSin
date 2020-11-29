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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Knights;
import com.wearethreestudios.toidleissin.program.Mages;
import com.wearethreestudios.toidleissin.program.Monks;
import com.wearethreestudios.toidleissin.program.Physicians;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.Hints;

public class BarracksScreen extends ScreenAdapter {
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

    private String job1Command = "physiciantonun";
    private String job2Command = "magetomonk";
    private String job3Command = "goodworks";

    private TextButton idleunit;
    private TextButton job1;
    private TextButton left1;
    private TextButton job2;
    private TextButton left2;
    private TextButton job3;
    private TextButton left3;
    private TextButton right3;

    private Hints retirephysician;
    private Hints retiremage;
    private Hints goodworks;

    public BarracksScreen(ToIdleIsSin game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();
        initHints();
        background = game.atlas.findRegion("village/barracks/barracks_inside");
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        if(currentTouch > 0){
            touchModifier = 1 + (Program.realTime() - currentTouch)/200.0;
        }
        if(left1.isPressed()){
            Program.run(job1Command);
            Program.run("" + (int)(1 * touchModifier));
        }
        if(left2.isPressed()){
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
        idleunit.setText("Idle\n" + (int)((Knights)Program.gameState.getGroup("knights")).getIdle());
        job1.setText("Physicians\n" + (int)((Physicians)Program.gameState.getGroup("physicians")).getIdle());
        job2.setText("Mages\n" + (int)((Mages)Program.gameState.getGroup("mages")).getIdle());
        job3.setText("Good works\n" + (int)((Knights)Program.gameState.getGroup("knights")).getGoodworks());
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
        retirephysician = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Retire Physicians", "Send the Physicians back to become Nuns again.", "ui/icon");
        retirephysician.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -retirephysician.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -retirephysician.getPopup().getHeight()/2));
        TextureRegionDrawable a = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        retirephysician.getPopup().background(a);
        stage.addActor(retirephysician.getPopup());


        retiremage = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Retire Mages", "Send the Mages back to become Monks again.", "ui/icon");
        retiremage.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -retiremage.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -retiremage.getPopup().getHeight()/2));
        TextureRegionDrawable b = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        retiremage.getPopup().background(b);
        stage.addActor(retiremage.getPopup());


        goodworks = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Good Works", "Knights travel around, defending those on base and around. Factors into the max units allowed.", "ui/icon");
        goodworks.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -goodworks.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -goodworks.getPopup().getHeight()/2));
        TextureRegionDrawable c = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        goodworks.getPopup().background(c);
        stage.addActor(goodworks.getPopup());
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
                retirephysician.getPopup().setVisible(false);
                retiremage.getPopup().setVisible(false);
                goodworks.getPopup().setVisible(false);
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

        job1 = new TextButton("To Nun", game.skin, "job");
        job1.setSize(200,200);
        job1.setPosition((int)(ToIdleIsSin.WIDTH*0.3-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job1.getHeight()/2));
        job1.getLabel().setWrap(true);
        job1.getLabel().setAlignment(Align.center);
        job1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                retirephysician.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left1 = new TextButton("Demote", game.skin, "left");
        left1.setSize(200,100);
        left1.setPosition((int)(ToIdleIsSin.WIDTH*0.3-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left1.getHeight()/2));



        job2 = new TextButton("To Monk", game.skin, "job");
        job2.setSize(200,200);
        job2.setPosition((int)(ToIdleIsSin.WIDTH*0.5-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job2.getHeight()/2));
        job2.getLabel().setWrap(true);
        job2.getLabel().setAlignment(Align.center);
        job2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                retiremage.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left2 = new TextButton("Demote", game.skin, "left");
        left2.setSize(200,100);
        left2.setPosition((int)(ToIdleIsSin.WIDTH*0.5-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left2.getHeight()/2));



        job3 = new TextButton("Good works", game.skin, "job");
        job3.setSize(200,200);
        job3.setPosition((int)(ToIdleIsSin.WIDTH*0.7-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job3.getHeight()/2));
        job3.getLabel().setWrap(true);
        job3.getLabel().setAlignment(Align.center);
        job3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goodworks.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left3 = new TextButton("", game.skin, "left");
        left3.setSize(100,100);
        left3.setPosition((int)(ToIdleIsSin.WIDTH*0.7-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left3.getHeight()/2));

        right3 = new TextButton("", game.skin, "right");
        right3.setSize(100,100);
        right3.setPosition((int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.17-right3.getHeight()/2));


        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(idleunit);
        stage.addActor(job1);
        stage.addActor(job2);
        stage.addActor(job3);
        stage.addActor(left1);
        stage.addActor(left2);
        stage.addActor(left3);
        stage.addActor(right3);
    }

}
