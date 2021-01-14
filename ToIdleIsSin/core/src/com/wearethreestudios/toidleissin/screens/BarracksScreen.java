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
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;
import com.wearethreestudios.toidleissin.uihelpers.SlidePopUp;

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

    private String job1Command = "physiciantonun";
    private String job2Command = "magetomonk";
    private String job3Command = "goodworks";

    private TextButton idleunit;
    private TextButton job1;
    private TextButton job2;
    private TextButton job3;

    private Hints retirephysician;
    private Hints retiremage;
    private Hints goodworks;

    private SlidePopUp slider;

    private ImageBlob knight;
    private ImageBlob mage;
    private ImageBlob physician;

    public BarracksScreen(ToIdleIsSin game) {
        game.getScreen().dispose();
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();
        prepareUnits();
        initHints();
        background = game.atlas.findRegion("village/barracks/barracks_inside");
    }

    public void prepareUnits(){
        physician = new ImageBlob(game, 1);
        physician.addState("sprites/units/physicianidle", 4, 4, 0.08f);
        stage.addActor(physician);
        physician.setPosition(ToIdleIsSin.WIDTH*0.05f, ToIdleIsSin.HEIGHT*0.25f);
        physician.flip();

        mage = new ImageBlob(game, 1);
        mage.addState("sprites/units/mageidle", 4, 4, 0.08f);
        stage.addActor(mage);
        mage.setPosition(ToIdleIsSin.WIDTH*0.15f, ToIdleIsSin.HEIGHT*0.5f);
        mage.setBlobScale(0.8f, 0.8f);
        mage.flip();

        knight = new ImageBlob(game, 1);
        knight.addState("sprites/units/knightidle", 4, 4, 0.08f);
        stage.addActor(knight);
        knight.setPosition(ToIdleIsSin.WIDTH*0.22f, ToIdleIsSin.HEIGHT*0.23f);
        knight.setBlobScale(1.5f, 1.5f);
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        idleunit.setText("\n" + (int)((Knights)Program.gameState.getGroup("knights")).getIdle() + "\n/ " + (int) Program.gameState.getTOTAL_UNITS());
        job1.setText("\n" + (int)((Physicians)Program.gameState.getGroup("physicians")).getIdle() + "\n/ " + (int) Program.gameState.getTOTAL_UNITS());
        job2.setText("\n" + (int)((Mages)Program.gameState.getGroup("mages")).getIdle() + "\n/ " + (int) Program.gameState.getTOTAL_UNITS());
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
        retirephysician = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Idle Physicians", "Nuns trained into Physicians. Bring them into battle to heal units, and reduce enemy damage.", "supportxmr");
        retirephysician.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -retirephysician.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -retirephysician.getPopup().getHeight()/2));
        TextureRegionDrawable a = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        retirephysician.getPopup().background(a);
        stage.addActor(retirephysician.getPopup());


        retiremage = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Idle Mages", "Monks trained into Mages. Bring them into battle for a boost in your attack power.", "supportxmr");
        retiremage.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -retiremage.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -retiremage.getPopup().getHeight()/2));
        TextureRegionDrawable b = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        retiremage.getPopup().background(b);
        stage.addActor(retiremage.getPopup());


        goodworks = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Good Works", "Knights travel around, defending those on base and around. Factors into the max units allowed.", "supportxmr");
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
                retirephysician.getPopup().setVisible(false);
                retiremage.getPopup().setVisible(false);
                goodworks.getPopup().setVisible(false);
                if(slider != null){
                    if( !slider.hit(touch.x, touch.y) ){
                        slider.remove();
                    }
                }
                return super.touchDown(screenX, screenY, pointer, button);
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
    public void dispose() {
        stage.dispose();
    }


    private void initButtons(){
        village = NavButtons.getVillage(game);
        campaign = NavButtons.getCampaign(game);
        story = NavButtons.getStory(game);

        idleunit = new TextButton("Idle", game.skin, "knight");
        idleunit.setPosition((int)(ToIdleIsSin.WIDTH*0.12-idleunit.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.92-idleunit.getHeight()/2));
        idleunit.getLabel().setWrap(true);
        idleunit.getLabel().setAlignment(Align.center);

        job1 = new TextButton("Idle Nun", game.skin, "medic");
//        job1.setSize(200,200);
        job1.setPosition((int)(ToIdleIsSin.WIDTH*0.6-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.92-job1.getHeight()/2));
        job1.getLabel().setWrap(true);
        job1.getLabel().setAlignment(Align.center);
        job1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                retirephysician.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });



        job2 = new TextButton("Idle Monk", game.skin, "mage");
//        job2.setSize(200,200);
        job2.setPosition((int)(ToIdleIsSin.WIDTH*0.88-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.92-job2.getHeight()/2));
        job2.getLabel().setWrap(true);
        job2.getLabel().setAlignment(Align.center);
        job2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                retiremage.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });



        job3 = new TextButton("Good works", game.skin, "job");
        job3.setSize(250,250);
        job3.setPosition((int)(ToIdleIsSin.WIDTH*0.5-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job3.getHeight()/2));
        job3.getLabel().setWrap(true);
        job3.getLabel().setAlignment(Align.center);
        job3.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    goodworks.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("knights").getIdle();
                    int workingPeople = (int)((Knights)Program.gameState.getGroup("knights")).getGoodworks();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Good Works", idlePeople, workingPeople, job3Command);
                    slider.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -slider.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -slider.getPopup().getHeight()/2));
                    stage.addActor(slider.getPopup());
                    Program.gameState.pause();
                }
                super.clicked(event, x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                held = Program.realTime();
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(idleunit);
        stage.addActor(job1);
        stage.addActor(job2);
        stage.addActor(job3);
    }
}
