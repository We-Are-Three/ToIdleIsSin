package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Monks;
import com.wearethreestudios.toidleissin.program.Program;

public class MonasteryScreen extends ScreenAdapter {
    ToIdleIsSin game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Texture buttonVillage;
    private Rectangle buttonVillageBounds;
    private Texture buttonCampaigns;
    private Rectangle buttonCampaignsBounds;
    private Texture buttonStory;
    private Rectangle buttonStoryBounds;
    private Vector3 touch = new Vector3();
    private Label leftlabal;
    private Label recruitlabel;
    private Label monktraininglabel;
    private Label villageimprovementslabel;
    private Label monkminingbonuslabel;

    private Texture background;
    private Stage stage;

    public MonasteryScreen(final ToIdleIsSin game) {
        this.game = game;
        background = new Texture("monastery_inside.png");

        buttonVillage = new Texture("button_village.png");
        buttonCampaigns = new Texture("button_campaigns.png");
        buttonStory = new Texture("button_story.png");

        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort, game.batch);
        setupControls();

    }

    public void setupControls(){
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = new BitmapFont();
        textStyle.fontColor = Color.RED;
        Gdx.input.setInputProcessor(stage);
        Table control = new Table();
        leftlabal = new Label(""+(int) Program.gameState.getGroup("monks").getIdle(), textStyle);
        leftlabal.setFontScale(3f,3f);

        Image leftRecruit = new Image(new Texture("ui/left.png"));
        leftRecruit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("recruit");
                Program.run("-1");
                leftlabal.setText(""+(int)((Monks)Program.gameState.getGroup("monks")).getIdle());
                recruitlabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getRecruiting());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Image rightRecruit = new Image(new Texture("ui/right.png"));
        rightRecruit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("recruit");
                Program.run("1");
                leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
                recruitlabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getRecruiting());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        recruitlabel = new Label(""+(int) ((Monks)Program.gameState.getGroup("monks")).getRecruiting(), textStyle);
        recruitlabel.setFontScale(3f,3f);
        Image recruit = new Image(new Texture("ui/recruit.png"));


        Image leftmonktraining = new Image(new Texture("ui/left.png"));
        leftmonktraining.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("monktraining");
                Program.run("-1");
                leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
                monktraininglabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getTraining());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Image rightmonktraining = new Image(new Texture("ui/right.png"));
        rightmonktraining.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("monktraining");
                Program.run("1");
                leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
                monktraininglabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getTraining());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        monktraininglabel = new Label(""+(int) ((Monks)Program.gameState.getGroup("monks")).getTraining(), textStyle);
        monktraininglabel.setFontScale(3f,3f);
        Image traini = new Image(new Texture("ui/trainmage.png"));


        Image leftvillageimprovements = new Image(new Texture("ui/left.png"));
        leftvillageimprovements.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("villageimprovements");
                Program.run("-1");
                leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
                villageimprovementslabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getImprovements());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Image rightvillageimprovements = new Image(new Texture("ui/right.png"));
        rightvillageimprovements.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("villageimprovements");
                Program.run("1");
                leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
                villageimprovementslabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getImprovements());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        villageimprovementslabel = new Label(""+(int) ((Monks)Program.gameState.getGroup("monks")).getImprovements(), textStyle);
        villageimprovementslabel.setFontScale(3f,3f);
        Image improvementsi = new Image(new Texture("ui/improvements.png"));

        Image leftmonkminingbonus = new Image(new Texture("ui/left.png"));
        leftmonkminingbonus.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("monkminingbonus");
                Program.run("-1");
                leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
                monkminingbonuslabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getMining());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Image rightmonkminingbonus = new Image(new Texture("ui/right.png"));
        rightmonkminingbonus.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Program.run("monkminingbonus");
                Program.run("1");
                leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
                monkminingbonuslabel.setText(""+(int)((Monks) Program.gameState.getGroup("monks")).getMining());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        monkminingbonuslabel = new Label(""+(int) ((Monks)Program.gameState.getGroup("monks")).getMining(), textStyle);
        monkminingbonuslabel.setFontScale(3f,3f);
        Image miningbonus = new Image(new Texture("ui/setmining.png"));


        control.add(leftlabal).size(100,100).padRight(5);
        control.add(leftRecruit).size(leftRecruit.getWidth(), leftRecruit.getHeight()).padRight(5);
        control.add(recruit).size(recruit.getWidth(), recruit.getHeight());
        control.add(rightRecruit).size(rightRecruit.getWidth(), rightRecruit.getHeight()).padLeft(5);
        control.add(recruitlabel).size(100,100).padLeft(5);
        control.row().pad(5,0,5,0);
        control.add();
        control.add(leftmonktraining).size(leftmonktraining.getWidth(), leftmonktraining.getHeight()).padRight(5);
        control.add(traini).size(traini.getWidth(), traini.getHeight());
        control.add(rightmonktraining).size(rightmonktraining.getWidth(), rightmonktraining.getHeight()).padLeft(5);
        control.add(monktraininglabel).size(100,100).padLeft(5);
        control.row().pad(5,0,5,0);
        control.add();
        control.add(leftvillageimprovements).size(leftvillageimprovements.getWidth(), leftvillageimprovements.getHeight()).padRight(5);
        control.add(improvementsi).size(improvementsi.getWidth(), improvementsi.getHeight());
        control.add(rightvillageimprovements).size(rightvillageimprovements.getWidth(), rightvillageimprovements.getHeight()).padLeft(5);
        control.add(villageimprovementslabel).size(100,100).padLeft(5);
        control.row().pad(5,0,5,0);
        control.add();
        control.add(leftmonkminingbonus).size(leftmonkminingbonus.getWidth(), leftmonkminingbonus.getHeight()).padRight(5);
        control.add(miningbonus).size(miningbonus.getWidth(), miningbonus.getHeight());
        control.add(rightmonkminingbonus).size(rightmonkminingbonus.getWidth(), rightmonkminingbonus.getHeight()).padLeft(5);
        control.add(monkminingbonuslabel).size(100,100).padLeft(5);
        control.setPosition((int)(ToIdleIsSin.WIDTH*0.5-recruit.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25));
        stage.addActor(control);
    }

    @Override
    public void render(float delta) {
        Program.runNextCommand();
        leftlabal.setText(""+(int)Program.gameState.getGroup("monks").getIdle());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);

        // GUI
        game.batch.draw(buttonVillage, (int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.05), buttonVillage.getWidth(), buttonVillage.getHeight());
        buttonVillageBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.05), buttonVillage.getWidth(), buttonVillage.getHeight());

        game.batch.draw(buttonCampaigns, (int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.05), buttonCampaigns.getWidth(), buttonCampaigns.getHeight());
        buttonCampaignsBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.05), buttonCampaigns.getWidth(), buttonCampaigns.getHeight());

        game.batch.draw(buttonStory, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.05), buttonStory.getWidth(), buttonStory.getHeight());
        buttonStoryBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.05), buttonStory.getWidth(), buttonStory.getHeight());
        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                gamePort.unproject(touch.set(screenX, screenY, 0));
                if(buttonVillageBounds.contains(touch.x, touch.y)){
                    game.setScreen(new VillageScreen(game));
                    Program.run("village");

                } else if(buttonCampaignsBounds.contains(touch.x, touch.y)){
                    game.setScreen(new LevelsScreen(game));
                    Program.run("battles");

                } else if(buttonStoryBounds.contains(touch.x, touch.y)){
                    game.setScreen(new StoryScreen(game));
                    Program.run("visual novel");
                }
                stage.touchDown(screenX,screenY,pointer,button);
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE){
                    game.setScreen(new VillageScreen(game));
                }
                return super.keyDown(keycode);
            }
        });
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
        background.dispose();
        buttonVillage.dispose();
        buttonCampaigns.dispose();
        buttonStory.dispose();
        for(Actor a : stage.getActors()){
            a.remove();
        }
        stage.dispose();
    }
}
