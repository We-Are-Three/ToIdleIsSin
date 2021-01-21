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
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;
import com.wearethreestudios.toidleissin.uihelpers.SlidePopUp;

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

    private String job1Command = "farming";
    private String job2Command = "nuntraining";
    private String job3Command = "studying";
    private String job4Command = "praying";

    private TextButton idleunit;
    private TextButton job1;
    private TextButton job2;
    private TextButton job3;
    private TextButton job4;

    private Hints farming;
    private Hints training;
    private Hints studying;
    private Hints praying;

    private SlidePopUp slider;

    private ImageBlob nun;
    private ImageBlob nun2;
    private ImageBlob oldwoman;

    public NunnaryScreen(ToIdleIsSin game) {
        game.getScreen().dispose();
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();
        prepareUnits();
        initHints();
        background = game.atlas.findRegion("village/nunnary/nunnary_inside");
    }

    public void prepareUnits(){
        nun = new ImageBlob(game, 1);
        nun.addState("sprites/units/nunidle", 4, 4, 0.08f);
        stage.addActor(nun);
        nun.setPosition(ToIdleIsSin.WIDTH*0.5f, ToIdleIsSin.HEIGHT*0.25f);
        
        nun2 = new ImageBlob(game, 1);
        nun2.addState("sprites/units/nunidle", 4, 4, 0.08f);
        stage.addActor(nun2);
        nun2.setPosition(ToIdleIsSin.WIDTH*0.4f, ToIdleIsSin.HEIGHT*0.6f);
        nun2.setBlobScale(0.7f, 0.7f);
        
        oldwoman = new ImageBlob(game, 1);
        oldwoman.addState("sprites/units/oldwoman", 4, 4, 0.08f);
        stage.addActor(oldwoman);
        oldwoman.setPosition(ToIdleIsSin.WIDTH*0.05f, ToIdleIsSin.HEIGHT*0.25f);
        oldwoman.setBlobScale(0.9f, 0.9f);
        oldwoman.flip();
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        idleunit.setText("\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getIdle() + "\n/ " + (int) Program.gameState.getTOTAL_UNITS());
        job1.setText("\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getFarming());
        job2.setText("\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getTraining());
        job3.setText("\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getStudying());
        job4.setText("\n" + (int)((Nuns)Program.gameState.getGroup("nuns")).getPraying());
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
        farming = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Farming", "Farms food for all recruits. Factors into max units allowed.", "supportxmr");
        farming.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -farming.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -farming.getPopup().getHeight()/2));
        TextureRegionDrawable a = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        farming.getPopup().background(a);
        stage.addActor(farming.getPopup());


        training = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Training", "Trains Nuns into Physicians.", "supportxmr");
        training.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -training.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -training.getPopup().getHeight()/2));
        TextureRegionDrawable b = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        training.getPopup().background(b);
        stage.addActor(training.getPopup());


        studying = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Studying", "Studying increases the rate that perk points are earned. They can be spent in the Cathedral.", "supportxmr");
        studying.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -studying.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -studying.getPopup().getHeight()/2));
        TextureRegionDrawable c = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        studying.getPopup().background(c);
        stage.addActor(studying.getPopup());


        praying = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Praying", "The nuns will pray for those on the front line. Increases battle performance for all units.", "supportxmr");
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
                farming.getPopup().setVisible(false);
                training.getPopup().setVisible(false);
                studying.getPopup().setVisible(false);
                praying.getPopup().setVisible(false);
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

        idleunit = new TextButton("", game.skin, "nun");
        idleunit.setPosition((int)(ToIdleIsSin.WIDTH*0.12-idleunit.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.92-idleunit.getHeight()/2));
        idleunit.getLabel().setWrap(true);
        idleunit.getLabel().setAlignment(Align.center);

        job1 = new TextButton("Farm", game.skin, "nunfarming");
        job1.setSize(250,250);
        job1.setPosition((int)(ToIdleIsSin.WIDTH*0.125-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job1.getHeight()/2));
        job1.getLabel().setWrap(true);
        job1.getLabel().setAlignment(Align.center);
        job1.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    farming.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("nuns").getIdle();
                    int workingPeople = (int)((Nuns)Program.gameState.getGroup("nuns")).getFarming();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Farm", idlePeople, workingPeople, job1Command);
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



        job2 = new TextButton("Train", game.skin, "nuntraining");
        job2.setSize(250,250);
        job2.setPosition((int)(ToIdleIsSin.WIDTH*0.375-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job2.getHeight()/2));
        job2.getLabel().setWrap(true);
        job2.getLabel().setAlignment(Align.center);
        job2.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    training.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("nuns").getIdle();
                    int workingPeople = (int)((Nuns)Program.gameState.getGroup("nuns")).getTraining();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Train", idlePeople, workingPeople, job2Command);
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



        job3 = new TextButton("Study", game.skin, "nunstudy");
        job3.setSize(250,250);
        job3.setPosition((int)(ToIdleIsSin.WIDTH*0.625-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job3.getHeight()/2));
        job3.getLabel().setWrap(true);
        job3.getLabel().setAlignment(Align.center);
        job3.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    studying.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("nuns").getIdle();
                    int workingPeople = (int)((Nuns)Program.gameState.getGroup("nuns")).getStudying();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Study", idlePeople, workingPeople, job3Command);
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



        job4 = new TextButton("Pray", game.skin, "nunpray");
        job4.setSize(250,250);
        job4.setPosition((int)(ToIdleIsSin.WIDTH*0.875-job4.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job4.getHeight()/2));
        job4.getLabel().setWrap(true);
        job4.getLabel().setAlignment(Align.center);
        job4.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    praying.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("nuns").getIdle();
                    int workingPeople = (int)((Nuns)Program.gameState.getGroup("nuns")).getPraying();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Pray", idlePeople, workingPeople, job4Command);
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
        stage.addActor(job4);
    }
}
