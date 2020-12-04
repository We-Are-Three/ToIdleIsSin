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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Campaign;
import com.wearethreestudios.toidleissin.program.Knights;
import com.wearethreestudios.toidleissin.program.Lines;
import com.wearethreestudios.toidleissin.program.Mages;
import com.wearethreestudios.toidleissin.program.Modifier;
import com.wearethreestudios.toidleissin.program.Monks;
import com.wearethreestudios.toidleissin.program.Physicians;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.ScrollImage;

public class LevelScreen extends ScreenAdapter {
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

    private String job1Command = "addknights";
    private String job2Command = "addmages";
    private String job3Command = "addphysicians";


    private TextButton line1;
    private TextButton line2;
    private TextButton line3;

    private TextButton progressText;
    private TextButton strengthText;

    private TextButton idle1;
    private TextButton idle2;
    private TextButton idle3;
    private TextButton job1;
    private TextButton left1;
    private TextButton right1;
    private TextButton job2;
    private TextButton left2;
    private TextButton right2;
    private TextButton job3;
    private TextButton left3;
    private TextButton right3;

    private TextButton apu1;
    private TextButton apu2;

    private int level;
    private Campaign camp;
    private String currentLine;
    private ScrollImage firstscroll;
    private ScrollImage secondscroll;
    private ScrollImage thirdscroll;
//    private Group currentImage;
    private Lines line;

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

        progressText = new TextButton("Progress:", game.skin, "idle");
        progressText.setPosition((int)(ToIdleIsSin.WIDTH*0.1-progressText.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-progressText.getHeight()/2));
        progressText.getLabel().setWrap(true);
        progressText.getLabel().setAlignment(Align.center);

        strengthText = new TextButton("strength:\n Ours : Enemies", game.skin, "idle");
        strengthText.setPosition((int)(ToIdleIsSin.WIDTH*0.8-strengthText.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-strengthText.getHeight()/2));
        strengthText.getLabel().setWrap(true);
        strengthText.getLabel().setAlignment(Align.center);


        apu1 = new TextButton("apu1", game.skin, "job");
        apu1.setPosition((int)(ToIdleIsSin.WIDTH*0.2), (int)(ToIdleIsSin.HEIGHT*0.9));
        apu1.setSize(200, 200);
        apu1.setOrigin(Align.center);
        apu1.setTransform(true);
        apu1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("apu1");
                line.apu1Attack(Program.gameState);
                super.clicked(event, x, y);
            }
        });


        apu2 = new TextButton("apu2", game.skin, "job");
        apu2.setPosition((int)(ToIdleIsSin.WIDTH*0.5), (int)(ToIdleIsSin.HEIGHT*0.9));
        apu2.setSize(200, 200);
        apu2.setOrigin(Align.center);
        apu2.setTransform(true);
        apu2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.print("apu2");
                line.apu2Attack(Program.gameState);
                super.clicked(event, x, y);
            }
        });


        
        line1 = new TextButton("line1", game.skin, "navbutton");
        line1.setPosition((int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.4));
        line1.setSize(200, 200);
        line1.setOrigin(Align.center);
        line1.setTransform(true);
        line1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ToIdleIsSin.program.run("campaign" + level);
                ToIdleIsSin.program.run("one");
//                currentImage = firstscroll.get();
                currentLine = "one";
                firstscroll.get().setVisible(true);
                secondscroll.get().setVisible(false);
                thirdscroll.get().setVisible(false);
                line = camp.getFirstLine();
                super.clicked(event, x, y);
            }
        });

        line2 = new TextButton("line2", game.skin, "navbutton");
        line2.setPosition((int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.4));
        line2.setSize(200, 200);
        line2.setOrigin(Align.center);
        line2.setTransform(true);
        line2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ToIdleIsSin.program.run("campaign" + level);
                ToIdleIsSin.program.run("two");
//                currentImage = secondscroll.get();
                firstscroll.get().setVisible(false);
                secondscroll.get().setVisible(true);
                thirdscroll.get().setVisible(false);
                currentLine = "two";
                line = camp.getSecondLine();
                super.clicked(event, x, y);
            }
        });

        line3 = new TextButton("line3", game.skin, "navbutton");
        line3.setPosition((int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4));
        line3.setSize(200, 200);
        line3.setOrigin(Align.center);
        line3.setTransform(true);
        line3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ToIdleIsSin.program.run("campaign" + level);
                ToIdleIsSin.program.run("three");
//                currentImage = thirdscroll.get();
                firstscroll.get().setVisible(false);
                secondscroll.get().setVisible(false);
                thirdscroll.get().setVisible(true);
                currentLine = "three";
                line = camp.getThirdLine();
                super.clicked(event, x, y);
            }
        });
        
        

        idle1 = new TextButton("Idle Knights", game.skin, "job");
        idle1.setSize(200,200);
        idle1.setPosition((int)(ToIdleIsSin.WIDTH*0.3-idle1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.35-idle1.getHeight()/2));
        idle1.getLabel().setWrap(true);
        idle1.getLabel().setAlignment(Align.center);

        job1 = new TextButton("Fighting Knights", game.skin, "job");
        job1.setSize(200,200);
        job1.setPosition((int)(ToIdleIsSin.WIDTH*0.3-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job1.getHeight()/2));
        job1.getLabel().setWrap(true);
        job1.getLabel().setAlignment(Align.center);
        job1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        left1 = new TextButton("", game.skin, "left");
        left1.setSize(100,100);
        left1.setPosition((int)(ToIdleIsSin.WIDTH*0.3-job1.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left1.getHeight()/2));

        right1 = new TextButton("", game.skin, "right");
        right1.setSize(100,100);
        right1.setPosition((int)(ToIdleIsSin.WIDTH*0.3), (int)(ToIdleIsSin.HEIGHT*0.17-right1.getHeight()/2));



        idle2 = new TextButton("Idle Mages", game.skin, "job");
        idle2.setSize(200,200);
        idle2.setPosition((int)(ToIdleIsSin.WIDTH*0.5-idle2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.35-idle2.getHeight()/2));
        idle2.getLabel().setWrap(true);
        idle2.getLabel().setAlignment(Align.center);

        job2 = new TextButton("Fighting Mages", game.skin, "job");
        job2.setSize(200,200);
        job2.setPosition((int)(ToIdleIsSin.WIDTH*0.5-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job2.getHeight()/2));
        job2.getLabel().setWrap(true);
        job2.getLabel().setAlignment(Align.center);

        left2 = new TextButton("", game.skin, "left");
        left2.setSize(100,100);
        left2.setPosition((int)(ToIdleIsSin.WIDTH*0.5-job2.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left2.getHeight()/2));

        right2 = new TextButton("", game.skin, "right");
        right2.setSize(100,100);
        right2.setPosition((int)(ToIdleIsSin.WIDTH*0.5), (int)(ToIdleIsSin.HEIGHT*0.17-right2.getHeight()/2));



        idle3 = new TextButton("Idle Physicians", game.skin, "job");
        idle3.setSize(200,200);
        idle3.setPosition((int)(ToIdleIsSin.WIDTH*0.7-idle3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.35-idle3.getHeight()/2));
        idle3.getLabel().setWrap(true);
        idle3.getLabel().setAlignment(Align.center);

        job3 = new TextButton("Fighting Physicians", game.skin, "job");
        job3.setSize(200,200);
        job3.setPosition((int)(ToIdleIsSin.WIDTH*0.7-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.25-job3.getHeight()/2));
        job3.getLabel().setWrap(true);
        job3.getLabel().setAlignment(Align.center);

        left3 = new TextButton("", game.skin, "left");
        left3.setSize(100,100);
        left3.setPosition((int)(ToIdleIsSin.WIDTH*0.7-job3.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.17-left3.getHeight()/2));

        right3 = new TextButton("", game.skin, "right");
        right3.setSize(100,100);
        right3.setPosition((int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.17-right3.getHeight()/2));



        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(job1);
        stage.addActor(job2);
        stage.addActor(job3);
        stage.addActor(left1);
        stage.addActor(right1);
        stage.addActor(left2);
        stage.addActor(right2);
        stage.addActor(left3);
        stage.addActor(right3);
        stage.addActor(idle1);
        stage.addActor(idle2);
        stage.addActor(idle3);
        stage.addActor(line1);
        stage.addActor(line2);
        stage.addActor(line3);

        firstscroll = new ScrollImage(game, "campaign/" + level + "/" + "one" +"/scroll-", 6);
        firstscroll.get().setPosition(0, ToIdleIsSin.HEIGHT/2);
        secondscroll = new ScrollImage(game, "campaign/" + level + "/" + "two" +"/scroll-", 3);
        secondscroll.get().setPosition(0, ToIdleIsSin.HEIGHT/2);
        thirdscroll = new ScrollImage(game, "campaign/" + level + "/" + "three" +"/scroll-", 6);
        thirdscroll.get().setPosition(0, ToIdleIsSin.HEIGHT/2);

        Program.print("width: " + firstscroll.get().getWidth() + " ");
//        currentImage = firstscroll.get();
        stage.addActor(firstscroll.get());
        stage.addActor(secondscroll.get());
        stage.addActor(thirdscroll.get());
        firstscroll.get().setVisible(true);

        stage.addActor(progressText);
        stage.addActor(apu1);
        stage.addActor(apu2);
        stage.addActor(strengthText);


    }

    public LevelScreen(ToIdleIsSin game, int level) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
        this.level = level;
        camp = Program.gameState.getCampaign("campaign" + level);
        line = camp.getFirstLine();
        currentLine = "one";

        stage = new Stage(gamePort);
        initButtons();
        background = game.atlas.findRegion("campaign/level_bg");

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
        if(Program.gameState.canapu1()){
            apu1.setDisabled(false);
        }else {
            apu1.setDisabled(true);
        }
        if(Program.gameState.canapu2()){
            apu2.setDisabled(false);
        }else {
            apu2.setDisabled(true);
        }
        if(Program.gameState.canapu1()){
            apu1.setText("apu1");
        }else{
            apu1.setText("apu1\n" + Program.gameState.apu1Time() + "s");
        }
        if(Program.gameState.canapu2()){
            apu2.setText("apu2");
        }else{
            apu2.setText("apu2\n" + Program.gameState.apu2Time() + "s");
        }
        if(Program.gameState.getValue(Modifier.APU1) == 0) apu1.setVisible(false);
        if(Program.gameState.getValue(Modifier.APU2) == 0) apu2.setVisible(false);
        idle1.setText("Idle Knights\n" + (int)((Knights)Program.gameState.getGroup("knights")).getIdle());
        idle2.setText("Idle Mages\n" + (int)((Mages)Program.gameState.getGroup("mages")).getIdle());
        idle3.setText("Idle Physicians\n" + (int)((Physicians)Program.gameState.getGroup("physicians")).getIdle());
        job1.setText("Fighting Knights\n" + line.getKnights());
        job2.setText("Fighting Mages\n" + line.getMages());
        job3.setText("Fighting Physicians\n" + line.getPhysicians());
        double progress = line.getEnemiesKilled()/line.getNumberOfEnemies();
        progressText.setText("Progress\n" + String.format("%1$,.2f", 100*progress) + "%");
        strengthText.setText("strength:\nOurs : Enemies\n" + String.format("%1$,.2f", 100*line.getOurPercent()) + "%" + " : " + String.format("%1$,.2f", 100*(1-line.getOurPercent())) + "%");
        if("one".equals(currentLine) && progress <= 1){
            firstscroll.get().setPosition(-(int)((firstscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2);
        } else if("two".equals(currentLine) && progress <= 1){
            secondscroll.get().setPosition(-(int)((secondscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2);
        } else if("three".equals(currentLine) && progress <= 1){
            thirdscroll.get().setPosition(-(int)((thirdscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2);
        }
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
                currentTouch = Program.realTime();
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
}