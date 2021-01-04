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
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.Hints;
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;

public class MinesScreen extends ScreenAdapter {
    ToIdleIsSin game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector3 touch = new Vector3();

    private TextureRegion background;

    private TextButton village;
    private TextButton campaign;
    private TextButton story;
    private Stage stage;

    private TextButton idleunit;
    private TextButton claimBonus;
    private TextButton job1;
    private TextButton left1;

    private Hints mining;
    private Hints bonus;

    private ImageBlob man;
    private ImageBlob woman;

    private void initButtons(){
        village = NavButtons.getVillage(game);
        campaign = NavButtons.getCampaign(game);
        story = NavButtons.getStory(game);

        idleunit = new TextButton("Hash Rate:", game.skin, "idle");
        idleunit.setPosition((int)(ToIdleIsSin.WIDTH*0.1-idleunit.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-idleunit.getHeight()/2));
        idleunit.getLabel().setWrap(true);
        idleunit.getLabel().setAlignment(Align.center);

        claimBonus = new TextButton("Resources:", game.skin, "idle");
        claimBonus.setPosition((int)(ToIdleIsSin.WIDTH*0.5-claimBonus.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-claimBonus.getHeight()/2));
        claimBonus.getLabel().setWrap(true);
        claimBonus.getLabel().setAlignment(Align.center);
        claimBonus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bonus.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        job1 = new TextButton("Mine", game.skin, "job");
        job1.setSize(200,200);
        job1.setPosition((int)(ToIdleIsSin.WIDTH*0.5-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job1.getHeight()/2));
        job1.getLabel().setWrap(true);
        job1.getLabel().setAlignment(Align.center);
        job1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mining.getPopup().setVisible(true);
                super.clicked(event, x, y);
            }
        });

        left1 = new TextButton("Start", game.skin, "job");
        left1.setSize(200,100);
        left1.setPosition((int)(ToIdleIsSin.WIDTH*0.5-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left1.getHeight()/2));
        left1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/startmine.mp3", Sound.class);
                game.sound.play();
                ToIdleIsSin.program.run("mines");
                super.clicked(event, x, y);
            }
        });

        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(idleunit);
        stage.addActor(claimBonus);
        stage.addActor(job1);
        stage.addActor(left1);

    }

    public void prepareUnits(){
        man = new ImageBlob(game, 1);
        man.addState("sprites/units/man", 4, 4, 0.08f);
        stage.addActor(man);
        man.setPosition(ToIdleIsSin.WIDTH*0.5f, ToIdleIsSin.HEIGHT*0.55f);

        woman = new ImageBlob(game, 1);
        woman.addState("sprites/units/woman", 4, 4, 0.08f);
        stage.addActor(woman);
        woman.setPosition(ToIdleIsSin.WIDTH*0.0f, ToIdleIsSin.HEIGHT*0.25f);
        woman.flip();
    }

    public MinesScreen(ToIdleIsSin game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();
        prepareUnits();
        initHints();
        background = game.atlas.findRegion("village/mines/mine_inside");
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        if(Program.isMining()){
            left1.setText("Stop");
        }else{
            left1.setText("Start");
        }

        if(Program.miner != null){
            idleunit.setVisible(true);
//            Program.print("HS: " +Program.miner.hashrate());
            idleunit.setText("Hash Rate:\n" + String.format("%1$,.2f", Program.miner.hashrate()) );
        }else{
            idleunit.setVisible(false);
        }
        claimBonus.setText("Resources:\n" + Program.gameState.getResources());
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
        mining = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Mining", "Monks start mining and the game speed increases. Mines Monero as a background process on the phone.", "supportxmr");
        mining.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -mining.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -mining.getPopup().getHeight()/2));
        TextureRegionDrawable a = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        mining.getPopup().background(a);
        stage.addActor(mining.getPopup());

        bonus = new Hints(game, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4), "Resources", "Used to construct walls on the daily line.", "ui/icon");
        bonus.getPopup().setPosition((int)(ToIdleIsSin.WIDTH*0.5 -bonus.getPopup().getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.55 -bonus.getPopup().getHeight()/2));
        TextureRegionDrawable b = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        bonus.getPopup().background(a);
        stage.addActor(bonus.getPopup());
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
                mining.getPopup().setVisible(false);
                bonus.getPopup().setVisible(false);
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
        man.dispose();
    }
}
