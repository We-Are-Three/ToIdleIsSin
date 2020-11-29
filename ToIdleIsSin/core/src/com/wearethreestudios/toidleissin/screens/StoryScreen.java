package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class StoryScreen extends ScreenAdapter {
//    https://gamefromscratch.com/libgdx-tutorial-2-hello-world/
    ToIdleIsSin game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Vector3 touch = new Vector3();

    private TextureRegion background;

    private ImageBlob walkingMan;
    private ImageBlob door;
    private TextButton village;
    private TextButton campaign;
    private TextButton story;
    private Stage stage;


    private ImageBlob charity;
    private ImageBlob chastity;
    private ImageBlob diligence;
    private ImageBlob humility;
    private ImageBlob kindness;
    private ImageBlob patiencekid;
    private ImageBlob patienceman;
    private ImageBlob temperance;

    private void initButtons(){

        village = new TextButton("Village", game.skin, "navbutton");
//        village.setBounds(500,500, 200, 200);
        village.setPosition((int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.01));
        //coment out if you want size of background
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

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                village.addAction(
                        forever(sequence(
                                scaleTo(1.1f, 1.1f, 0.1f, Interpolation.pow5),
                                scaleTo(1f, 1f, 0.1f, Interpolation.pow5))));
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                village.clearActions();
                super.exit(event, x, y, pointer, toActor);
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
            }
        });

//        village.getLabel().setWrap(true);
//        village.getLabel().setEllipsis(true);

        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);

    }


    public StoryScreen(final ToIdleIsSin game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
        stage = new Stage(gamePort);
        initButtons();

        background = game.atlas.findRegion("story/cottage");
        initSprites();

        door = new ImageBlob(game, 0.75);
        door.addState("story/door", 1, 1, 0.025f)
                .addState("story/door-click", 1, 1, 0.025f);

    }

    public void initSprites(){
        walkingMan = new ImageBlob(game, 1);
        walkingMan.addState("sprite-animation4", 5, 6, 0.025f, 1, 1);
        walkingMan.addState("sprite-animation4", 5, 6, 0.025f);

        charity = new ImageBlob(game, 0.75);
        charity.addState("sprites/charity", 1, 1, 0.025f);
        charity.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am charity");
                if(Program.gameState.getCampaign("campaign1").isCleared() && (1 == Program.gameState.getVirtue("charity").getProgress())){
                    Program.print("" + Program.gameState.getVirtue("charity").getProgress());
                    // Do Diologue options


                    Program.gameState.getVirtue("charity").setProgress(2);
                    Program.gameState.getVirtue("kindness").setProgress(1);
                }
//                charity.addAction(
//                        sequence(
//                                scaleTo(1.1f, 1.1f, 1f, Interpolation.pow5),
//                                scaleTo(1f, 1f, 1f, Interpolation.pow5),
//                                run(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        charity.flip();
//                                    }
//                                }),
//                                moveBy(-200, 0, 1f)));
                super.clicked(event, x, y);
            }
        });

        kindness = new ImageBlob(game, 0.75);
        kindness.addState("sprites/kindness", 1, 1, 0.025f);
        kindness.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am kindness");
                if(Program.gameState.getCampaign("campaign2").isCleared() && (1 == Program.gameState.getVirtue("kindness").getProgress())){
                    Program.print("" + Program.gameState.getVirtue("kindness").getProgress());
                    // Do Diologue options


                    Program.gameState.getVirtue("kindness").setProgress(2);
                    Program.gameState.getVirtue("diligence").setProgress(1);
                }
                super.clicked(event, x, y);
            }
        });

        diligence = new ImageBlob(game, 0.75);
        diligence.addState("sprites/dilligence", 1, 1, 0.025f);
        diligence.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am diligence");
                if(Program.gameState.getCampaign("campaign3").isCleared() && (1 == Program.gameState.getVirtue("diligence").getProgress())){
                    Program.print("" + Program.gameState.getVirtue("diligence").getProgress());
                    // Do Diologue options


                    Program.gameState.getVirtue("diligence").setProgress(2);
                    Program.gameState.getVirtue("humility").setProgress(1);
                }
                super.clicked(event, x, y);
            }
        });

        humility = new ImageBlob(game, 0.75);
        humility.addState("sprites/humility", 1, 1, 0.025f);
        humility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am humility");
                if(Program.gameState.getCampaign("campaign4").isCleared() && (1 == Program.gameState.getVirtue("humility").getProgress())){
                    Program.print("" + Program.gameState.getVirtue("humility").getProgress());
                    // Do Diologue options


                    Program.gameState.getVirtue("humility").setProgress(2);
                    Program.gameState.getVirtue("chastity").setProgress(1);
                }
                super.clicked(event, x, y);
            }
        });
        
        chastity = new ImageBlob(game, 0.75);
        chastity.addState("sprites/chastity", 1, 1, 0.025f);
        chastity.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am chastity");
                if(Program.gameState.getCampaign("campaign5").isCleared() && (1 == Program.gameState.getVirtue("chastity").getProgress())){
                    Program.print("" + Program.gameState.getVirtue("chastity").getProgress());
                    // Do Diologue options


                    Program.gameState.getVirtue("chastity").setProgress(2);
                    Program.gameState.getVirtue("patience").setProgress(1);
                }
                super.clicked(event, x, y);
            }
        });
        
        patienceman = new ImageBlob(game, 0.75);
        patienceman.addState("sprites/patienceman", 1, 1, 0.025f);
        patienceman.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am patienceman");
                if(Program.gameState.getCampaign("campaign6").isCleared() && (1 == Program.gameState.getVirtue("patience").getProgress())){
                    Program.print("" + Program.gameState.getVirtue("patience").getProgress());
                    // Do Diologue options


                    Program.gameState.getVirtue("patience").setProgress(2);
                    Program.gameState.getVirtue("temperance").setProgress(1);
                }
                super.clicked(event, x, y);
            }
        });
        
        patiencekid = new ImageBlob(game, 0.75);
        patiencekid.addState("sprites/patiencekid", 1, 1, 0.025f);
        patiencekid.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am patiencekid");
                super.clicked(event, x, y);
            }
        });
        
        temperance = new ImageBlob(game, 0.75);
        temperance.addState("sprites/temperance", 1, 1, 0.025f);
        temperance.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("I am temperance");
                if(Program.gameState.getCampaign("campaign7").isCleared() && (1 == Program.gameState.getVirtue("temperance").getProgress())){
                    Program.print("" + Program.gameState.getVirtue("temperance").getProgress());
                    // Do Diologue options


                    Program.gameState.getVirtue("temperance").setProgress(2);
//                    Program.gameState.getVirtue("diligence").setProgress(1);
                }
                super.clicked(event, x, y);
            }
        });


        stage.addActor(charity);
        charity.setPosition(550,1100);
        charity.debug();
        if(Program.gameState.getVirtue("charity").getProgress() == 0){
            charity.setVisible(false);
        }

        stage.addActor(patiencekid);
        patiencekid.setPosition(50,1050);
        patiencekid.debug();

        stage.addActor(patienceman);
        patienceman.setPosition(200,1100);
        patienceman.debug();
        if(Program.gameState.getVirtue("patience").getProgress() == 0){
            patienceman.setVisible(false);
            patiencekid.setVisible(false);
        }

        stage.addActor(kindness);
        kindness.setPosition(0,650);
        kindness.flip();
        kindness.debug();
        if(Program.gameState.getVirtue("kindness").getProgress() == 0){
            kindness.setVisible(false);
        }

        stage.addActor(temperance);
        temperance.setPosition(350,700);
        temperance.debug();
        if(Program.gameState.getVirtue("temperance").getProgress() == 0){
            temperance.setVisible(false);
        }

        stage.addActor(chastity);
        chastity.setPosition(600,200);
        chastity.debug();
        if(Program.gameState.getVirtue("chastity").getProgress() == 0){
            chastity.setVisible(false);
        }

        stage.addActor(diligence);
        diligence.setPosition(250,200);
        diligence.debug();
        if(Program.gameState.getVirtue("diligence").getProgress() == 0){
            diligence.setVisible(false);
        }

        stage.addActor(humility);
        humility.setPosition(-100,200);
        humility.flip();
        humility.debug();
        if(Program.gameState.getVirtue("humility").getProgress() == 0){
            humility.setVisible(false);
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

        // ELEMENTS
        door.draw((int)(ToIdleIsSin.WIDTH*0.74), (int)(ToIdleIsSin.HEIGHT*0.49), 1.0, 1.0);

        walkingMan.draw(250,250,200,200);

        game.batch.end();
        stage.act(delta);
        stage.draw();

        walkingMan.returnToState(0);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {
        InputProcessor general =  new InputAdapter(){
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                gamePort.unproject(touch.set(screenX, screenY, 0));
                if(door.contains(touch.x, touch.y)){
                    door.switchState(1);
                }else{
                    door.returnToState(0);
                }
                return super.mouseMoved(screenX, screenY);
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                gamePort.unproject(touch.set(screenX, screenY, 0));
                if(walkingMan.contains(touch.x, touch.y)){
                    walkingMan.switchState(1);
                }
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
        door.dispose();
        walkingMan.dispose();
        stage.dispose();
    }
}
