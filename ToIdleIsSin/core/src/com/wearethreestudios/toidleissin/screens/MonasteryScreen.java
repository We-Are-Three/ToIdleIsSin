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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.Hints;
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;
import com.wearethreestudios.toidleissin.uihelpers.SlidePopUp;

public class MonasteryScreen extends ScreenAdapter {
    ToIdleIsSin game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector3 touch = new Vector3();

    private TextureRegion background;

    private TextButton village;
    private TextButton campaign;
    private TextButton story;
    private Stage stage;

    private String job1Command = "recruit";
    private String job2Command = "monktraining";
    private String job3Command = "villageimprovements";
    private String job4Command = "monkminingbonus";

    private TextButton idleunit;
    private TextButton job1;
    private TextButton job2;
    private TextButton job3;
    private TextButton job4;

    private Hints recruit;
    private Hints training;
    private Hints building;
    private Hints mining;

    private SlidePopUp slider;

    private ImageBlob monk;
    private ImageBlob monk2;
    private ImageBlob oldman;

    public MonasteryScreen(final ToIdleIsSin game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort, game.batch);
        initButtons();
        prepareUnits();
        initHints();
        background = game.atlas.findRegion("village/monastery/monastery_inside");

    }

    public void prepareUnits(){
        monk = new ImageBlob(game, 1);
        monk.addState("sprites/units/sloth", 4, 4, 0.08f);
        stage.addActor(monk);
        monk.setPosition(ToIdleIsSin.WIDTH*0.05f, ToIdleIsSin.HEIGHT*0.25f);
        monk.flip();

        monk2 = new ImageBlob(game, 1);
        monk2.addState("sprites/units/sloth", 4, 4, 0.08f);
        stage.addActor(monk2);
        monk2.setPosition(ToIdleIsSin.WIDTH*0.4f, ToIdleIsSin.HEIGHT*0.5f);
        monk2.setBlobScale(0.9f, 0.9f);

        oldman = new ImageBlob(game, 1);
        oldman.addState("sprites/units/oldman", 4, 4, 0.08f);
        stage.addActor(oldman);
        oldman.setPosition(ToIdleIsSin.WIDTH*0.5f, ToIdleIsSin.HEIGHT*0.25f);
    }

    @Override
    public void render(float delta) {
        Program.runNextCommand();
        idleunit.setText("Idle Monks\n" + (int)((Monks)Program.gameState.getGroup("monks")).getIdle() + " / " + (int) Program.gameState.getTOTAL_UNITS());
        job1.setText("Recruit\n" + (int)((Monks)Program.gameState.getGroup("monks")).getRecruiting());
        job2.setText("Train\n" + (int)((Monks)Program.gameState.getGroup("monks")).getTraining());
        job3.setText("Build\n" + (int)((Monks)Program.gameState.getGroup("monks")).getImprovements());
        job4.setText("Mine\n" + (int)((Monks)Program.gameState.getGroup("monks")).getMining());
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
        recruit = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Recruit", "Increase the rate that you recruit Monks, Nuns, and Knights", "ui/icon");
        recruit.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -recruit.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -recruit.getPopup().getHeight()/2));
        TextureRegionDrawable a = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        recruit.getPopup().background(a);
        stage.addActor(recruit.getPopup());


        training = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Training", "Trains Monks into Mages.", "ui/icon");
        training.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -training.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -training.getPopup().getHeight()/2));
        TextureRegionDrawable b = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        training.getPopup().background(b);
        stage.addActor(training.getPopup());


        building = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Building", "Building housing for new recruits. Factors into max units allowed.", "ui/icon");
        building.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -building.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -building.getPopup().getHeight()/2));
        TextureRegionDrawable c = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        building.getPopup().background(c);
        stage.addActor(building.getPopup());


        mining = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Mining", "Increases the Mining output, which increases the speed that things are done.", "ui/icon");
        mining.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -mining.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -mining.getPopup().getHeight()/2));
        TextureRegionDrawable d = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        mining.getPopup().background(d);
        stage.addActor(mining.getPopup());
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
                recruit.getPopup().setVisible(false);
                training.getPopup().setVisible(false);
                building.getPopup().setVisible(false);
                mining.getPopup().setVisible(false);
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

        idleunit = new TextButton("Idle", game.skin, "idle");
        idleunit.setPosition((int)(ToIdleIsSin.WIDTH*0.1-idleunit.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-idleunit.getHeight()/2));
        idleunit.getLabel().setWrap(true);
        idleunit.getLabel().setAlignment(Align.center);

        job1 = new TextButton("Recruiting", game.skin, "job");
        job1.setSize(200,200);
        job1.setPosition((int)(ToIdleIsSin.WIDTH*0.2-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job1.getHeight()/2));
        job1.getLabel().setWrap(true);
        job1.getLabel().setAlignment(Align.center);
        job1.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    recruit.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("monks").getIdle();
                    int workingPeople = (int)((Monks)Program.gameState.getGroup("monks")).getRecruiting();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "recruit", idlePeople, workingPeople, job1Command);
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



        job2 = new TextButton("Train", game.skin, "job");
        job2.setSize(200,200);
        job2.setPosition((int)(ToIdleIsSin.WIDTH*0.4-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job2.getHeight()/2));
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
                    int idlePeople = (int)Program.gameState.getGroup("monks").getIdle();
                    int workingPeople = (int)((Monks)Program.gameState.getGroup("monks")).getTraining();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "train", idlePeople, workingPeople, job2Command);
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



        job3 = new TextButton("Build", game.skin, "job");
        job3.setSize(200,200);
        job3.setPosition((int)(ToIdleIsSin.WIDTH*0.6-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job3.getHeight()/2));
        job3.getLabel().setWrap(true);
        job3.getLabel().setAlignment(Align.center);
        job3.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    building.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("monks").getIdle();
                    int workingPeople = (int)((Monks)Program.gameState.getGroup("monks")).getImprovements();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "recruit", idlePeople, workingPeople, job3Command);
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



        job4 = new TextButton("Mine", game.skin, "job");
        job4.setSize(200,200);
        job4.setPosition((int)(ToIdleIsSin.WIDTH*0.8-job4.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-job4.getHeight()/2));
        job4.getLabel().setWrap(true);
        job4.getLabel().setAlignment(Align.center);
        job4.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                if(Program.realTime() - held > 1000*0.25){
                    mining.getPopup().setVisible(true);
                }else{
                    int idlePeople = (int)Program.gameState.getGroup("monks").getIdle();
                    int workingPeople = (int)((Monks)Program.gameState.getGroup("monks")).getMining();
                    if(slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "recruit", idlePeople, workingPeople, job4Command);
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
