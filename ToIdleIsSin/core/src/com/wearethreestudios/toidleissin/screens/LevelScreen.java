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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
import com.wearethreestudios.toidleissin.uihelpers.Dialogue;
import com.wearethreestudios.toidleissin.uihelpers.DialoguePopUp;
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;
import com.wearethreestudios.toidleissin.uihelpers.ScrollImage;
import com.wearethreestudios.toidleissin.uihelpers.SlidePopUp;

import java.util.HashMap;

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

    private String job1Command = "addknights";
    private String job2Command = "addmages";
    private String job3Command = "addphysicians";


    private TextButton line1;
    private TextButton line2;
    private TextButton line3;

    private TextButton progressText;
    private TextButton strengthText;
    private boolean strengthHasListener = false;

    private TextButton idle1;
    private TextButton idle2;
    private TextButton idle3;
    private TextButton job1;
    private TextButton job2;
    private TextButton job3;

    private TextButton apu1;
    private TextButton apu2;

    private SlidePopUp slider;

    private int level;
    private Campaign camp;
    private String currentLine;
    private ScrollImage firstscroll;
    private ScrollImage secondscroll;
    private ScrollImage thirdscroll;
    private Lines line;

    private ImageBlob knight;
    private ImageBlob mage;
    private ImageBlob physician;
    private ImageBlob enemy1;
    private ImageBlob enemy2;
    private ImageBlob enemy3;
    private ImageBlob boss;
    private float bossScaleX;
    private float bossScaleY;
    private int bossxoffset = 0;

    private int virtueProgress = 0;
    private ImageBlob virtue;
    private float virtueScaleX;
    private float virtueScaleY;
    private int virtuexoffset = 0;
    
    private ImageBlob vice;
    private float viceScaleX;
    private float viceScaleY;
    private int vicexoffset = 0;

    private ImageBlob boy;
    private ImageBlob girl;

    private DialoguePopUp dialoguePopUp;
    private Dialogue dialogue = null;
    private String selectedVirtue;
    private int sceneVisible = 0;
    private HashMap<String, Boolean> visible;


    private void initButtons(){
        prepareUnits();

        village = NavButtons.getVillage(game);
        campaign = NavButtons.getCampaign(game);
        story = NavButtons.getStory(game);

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
                if(!apu1.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    line.apu1Attack(Program.gameState);
                }
                super.clicked(event, x, y);
            }
        });


        apu2 = new TextButton("apu2", game.skin, "job");
        apu2.setPosition((int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.9));
        apu2.setSize(200, 200);
        apu2.setOrigin(Align.center);
        apu2.setTransform(true);
        apu2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!apu2.isDisabled()) {
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    line.apu2Attack(Program.gameState);
                }
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
                game.sound = game.assets.get("sound/page.mp3", Sound.class);
                game.sound.play();
                ToIdleIsSin.program.run("campaign" + level);
                ToIdleIsSin.program.run("one");
                currentLine = "one";
                firstscroll.get().setVisible(true);
                secondscroll.get().setVisible(false);
                thirdscroll.get().setVisible(false);
                line = camp.getFirstLine();
                prepareUnits();
                strengthHasListener = false;
                strengthText.remove();
                strengthText = new TextButton("strength:\n Ours : Enemies", game.skin, "idle");
                strengthText.setPosition((int)(ToIdleIsSin.WIDTH*0.8-strengthText.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-strengthText.getHeight()/2));
                strengthText.getLabel().setWrap(true);
                strengthText.getLabel().setAlignment(Align.center);
                stage.addActor(strengthText);
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
                game.sound = game.assets.get("sound/page.mp3", Sound.class);
                game.sound.play();
                ToIdleIsSin.program.run("campaign" + level);
                ToIdleIsSin.program.run("two");
                firstscroll.get().setVisible(false);
                secondscroll.get().setVisible(true);
                thirdscroll.get().setVisible(false);
                currentLine = "two";
                line = camp.getSecondLine();
                prepareUnits();
                strengthHasListener = false;
                strengthText.remove();
                strengthText = new TextButton("strength:\n Ours : Enemies", game.skin, "idle");
                strengthText.setPosition((int)(ToIdleIsSin.WIDTH*0.8-strengthText.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-strengthText.getHeight()/2));
                strengthText.getLabel().setWrap(true);
                strengthText.getLabel().setAlignment(Align.center);
                stage.addActor(strengthText);
                super.clicked(event, x, y);
            }
        });

        line3 = new TextButton("daily line", game.skin, "navbutton");
        line3.setPosition((int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.4));
        line3.setSize(200, 200);
        line3.setOrigin(Align.center);
        line3.setTransform(true);
        line3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/page.mp3", Sound.class);
                game.sound.play();
                ToIdleIsSin.program.run("campaign" + level);
                ToIdleIsSin.program.run("three");
                firstscroll.get().setVisible(false);
                secondscroll.get().setVisible(false);
                thirdscroll.get().setVisible(true);
                currentLine = "three";
                line = camp.getThirdLine();
                prepareUnits();
                strengthHasListener = false;
                strengthText.remove();
                strengthText = new TextButton("strength:\n Ours : Enemies", game.skin, "idle");
                strengthText.setPosition((int)(ToIdleIsSin.WIDTH*0.8-strengthText.getWidth()/2), (int)(ToIdleIsSin.HEIGHT*0.94-strengthText.getHeight()/2));
                strengthText.getLabel().setWrap(true);
                strengthText.getLabel().setAlignment(Align.center);
                stage.addActor(strengthText);
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
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!job1.isDisabled()) {
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    int idlePeople = (int) Program.gameState.getGroup("knights").getIdle();
                    int workingPeople = line.getKnights();
                    if (slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int) (ToIdleIsSin.WIDTH * 0.8), (int) (ToIdleIsSin.HEIGHT * 0.4), "Fighting Knights", idlePeople, workingPeople, job1Command) {
                        @Override
                        public void remove() {
                            if (line.getKnights() == 0) line.safeRetreat();
                            super.remove();
                        }
                    };
                    slider.getPopup().setPosition((int) (ToIdleIsSin.WIDTH * 0.5 - slider.getPopup().getWidth() / 2), (int) (ToIdleIsSin.HEIGHT * 0.3 - slider.getPopup().getHeight() / 2));
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
        job2.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!job2.isDisabled()) {
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    int idlePeople = (int) Program.gameState.getGroup("mages").getIdle();
                    int workingPeople = line.getMages();
                    if (slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int) (ToIdleIsSin.WIDTH * 0.8), (int) (ToIdleIsSin.HEIGHT * 0.4), "Fighting Mages", idlePeople, workingPeople, job2Command);
                    slider.getPopup().setPosition((int) (ToIdleIsSin.WIDTH * 0.5 - slider.getPopup().getWidth() / 2), (int) (ToIdleIsSin.HEIGHT * 0.3 - slider.getPopup().getHeight() / 2));
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
        job3.addListener(new ClickListener() {
            long held = Long.MAX_VALUE;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!job3.isDisabled()) {
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    int idlePeople = (int) Program.gameState.getGroup("physicians").getIdle();
                    int workingPeople = line.getPhysicians();
                    if (slider != null) slider.getPopup().remove();
                    slider = new SlidePopUp(game, (int) (ToIdleIsSin.WIDTH * 0.8), (int) (ToIdleIsSin.HEIGHT * 0.4), "Fighting Physicians", idlePeople, workingPeople, job3Command);
                    slider.getPopup().setPosition((int) (ToIdleIsSin.WIDTH * 0.5 - slider.getPopup().getWidth() / 2), (int) (ToIdleIsSin.HEIGHT * 0.3 - slider.getPopup().getHeight() / 2));
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
        stage.addActor(job1);
        stage.addActor(job2);
        stage.addActor(job3);
        stage.addActor(idle1);
        stage.addActor(idle2);
        stage.addActor(idle3);
        stage.addActor(line1);
        stage.addActor(line2);
        stage.addActor(line3);

        Lines templine = camp.getFirstLine();
        double progress = templine.getEnemiesKilled()/templine.getNumberOfEnemies();
        firstscroll = new ScrollImage(game, "campaign/" + level + "/" + "one" +"/scroll-", 6);
        firstscroll.get().setPosition(-(int)((firstscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2);

        templine = camp.getSecondLine();
        progress = templine.getEnemiesKilled()/templine.getNumberOfEnemies();
        secondscroll = new ScrollImage(game, "campaign/" + level + "/" + "two" +"/scroll-", 3);
        secondscroll.get().setPosition(-(int)((secondscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2);

        templine = camp.getThirdLine();
        progress = templine.getEnemiesKilled()/templine.getNumberOfEnemies();
        thirdscroll = new ScrollImage(game, "campaign/" + level + "/" + "three" +"/scroll-", 3);
        thirdscroll.get().setPosition(-(int)((thirdscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2);

        stage.addActor(firstscroll.get());
        stage.addActor(secondscroll.get());
        stage.addActor(thirdscroll.get());
        firstscroll.get().setVisible(true);

        stage.addActor(progressText);
        stage.addActor(apu1);
        stage.addActor(apu2);
        stage.addActor(strengthText);

        if(currentLine.equals("three")){
            firstscroll.get().setVisible(false);
            secondscroll.get().setVisible(false);
            thirdscroll.get().setVisible(true);
            prepareUnits();
        }
        
        switch (level){
            case 1:
                selectedVirtue = "charity";
                break;
            case 2:
                selectedVirtue = "kindness";
                break;
            case 3:
                selectedVirtue = "diligence";
                break;
            case 4:
                selectedVirtue = "humility";
                break;
            case 5:
                selectedVirtue = "chastity";
                break;
            case 6:
                selectedVirtue = "patience";
                break;
            case 7:
                selectedVirtue = "temperance";
                break;
            default:
                break;

        }


    }

    public LevelScreen(ToIdleIsSin game, int level) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
        this.level = level;
        camp = Program.gameState.getCampaign("campaign" + level);
        if(camp.isCleared()){
            currentLine = "three";
            line = camp.getThirdLine();
            ToIdleIsSin.program.run("campaign" + level);
            ToIdleIsSin.program.run("three");
        }else{
            currentLine = "one";
            line = camp.getFirstLine();
        }

        stage = new Stage(gamePort);
        initButtons();
        initDialogue();
        background = game.atlas.findRegion("campaign/level_bg");

    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        if(line.isCleared() || line.getOurPercent().contains("Defend") || line.getOurPercent().contains("Resource")){
            job1.setDisabled(true);
            job2.setDisabled(true);
            job3.setDisabled(true);
            apu1.setDisabled(true);
            apu2.setDisabled(true);
        }else {
            job1.setDisabled(false);
            job2.setDisabled(false);
            job3.setDisabled(false);
            if(Program.gameState.canapu1()){
                apu1.setDisabled(false);
                apu1.setText("apu1");
            }else{
                apu1.setDisabled(true);
                apu1.setText("apu1\n" + (int)(Program.gameState.apu1Time() / Program.SPEED_MODIFIER) + "s");
            }
            if(Program.gameState.canapu2()){
                apu2.setDisabled(false);
                apu2.setText("apu2");
            }else{
                apu2.setText("apu2\n" + (int)(Program.gameState.apu2Time() / Program.SPEED_MODIFIER) + "s");
                apu2.setDisabled(true);
            }
            if(!line.canAPU()){
                apu1.setText("apu1");
                apu1.setDisabled(true);
                apu2.setText("apu2");
                apu2.setDisabled(true);
            }
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
        strengthText.setText(line.getOurPercent());
        if(line.getOurPercent().contains("Defend") || line.getOurPercent().contains("Resource")){
            if(!strengthHasListener){
                strengthHasListener = true;
                strengthText.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Program.gameState.pause();
                        line.defend();
                        Program.gameState.resume();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
            }
            if(!line.canDefend()){
                strengthText.setDisabled(true);
            }else {
                strengthText.setDisabled(false);
            }
        }
        if("one".equals(currentLine) && progress <= 1){
            firstscroll.get().addAction(Actions.moveTo(-(int)((firstscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2, 3f));
        } else if("two".equals(currentLine) && progress <= 1){
            secondscroll.get().addAction(Actions.moveTo(-(int)((secondscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2, 3f));
        } else if("three".equals(currentLine) && progress <= 1){
            thirdscroll.get().addAction(Actions.moveTo(-(int)((thirdscroll.get().getWidth()-ToIdleIsSin.WIDTH)*progress), ToIdleIsSin.HEIGHT/2, 3f));
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);

        game.batch.end();
        stage.act(delta);
        stage.draw();

        game.batch.begin();

        if( virtueProgress > 1)
            virtue.draw((int)(-virtue.getWidth()/2 + virtuexoffset),ToIdleIsSin.HEIGHT/2+170, virtueScaleX, virtueScaleY);

        if( isVisible("unit") ||  line.getMages() > 0)
            mage.draw((int)(200 - mage.getWidth()/2),ToIdleIsSin.HEIGHT/2+200,0.75,0.75);

        if( isVisible("enemy") ||   (line.getNumberOfEnemies() - line.getEnemiesKilled() > 0 && line.getWHICH_LINE() != 1) || ( line.getEnemiesKilled() / line.getNumberOfEnemies() < 0.85 && line.getWHICH_LINE() == 1 ) )
            enemy1.draw((int)(550 - enemy1.getWidth()/2),ToIdleIsSin.HEIGHT/2+200,0.75,0.75);

        if( isVisible("unit") ||  line.getKnights() > 0)
            knight.draw((int)(200 - knight.getWidth()/2),ToIdleIsSin.HEIGHT/2,1.2,1.2);

        if( isVisible("boss") ||  line.getEnemiesKilled() / line.getNumberOfEnemies() > 0.85 &&  line.getEnemiesKilled() / line.getNumberOfEnemies() < 1.0  && line.getWHICH_LINE() == 1){
            boss.draw((int)(500 + bossxoffset - boss.getWidth()/2),ToIdleIsSin.HEIGHT/2+100, bossScaleX, bossScaleY);
            vice.draw((int) (700 + vicexoffset - vice.getWidth()/2), ToIdleIsSin.HEIGHT/2, viceScaleX, viceScaleY);
        }

        if( isVisible("enemy") ||   (line.getNumberOfEnemies() - line.getEnemiesKilled() > 0 && line.getWHICH_LINE() != 1) || ( line.getEnemiesKilled() / line.getNumberOfEnemies() < 0.85 && line.getWHICH_LINE() == 1 ) )
            enemy3.draw((int)(750 - enemy3.getWidth()/2+50),ToIdleIsSin.HEIGHT/2+100,0.8,0.8);

        if( isVisible("unit") ||  line.getPhysicians() > 0)
            physician.draw((int)(-50 - physician.getWidth()/2),ToIdleIsSin.HEIGHT/2,0.75,0.75);

        if( isVisible("enemy") ||   (line.getNumberOfEnemies() - line.getEnemiesKilled() > 0 && line.getWHICH_LINE() != 1) || ( line.getEnemiesKilled() / line.getNumberOfEnemies() < 0.85 && line.getWHICH_LINE() == 1 ) )
            enemy2.draw((int)(400 - enemy2.getWidth()/2),ToIdleIsSin.HEIGHT/2,1.2,1.2);

        if( line.getWHICH_LINE() == 2 && line.isCleared()){
            boy.draw((int)(500 - boy.getWidth()/2),ToIdleIsSin.HEIGHT/2,0.55,0.55);
            girl.draw((int)(800 - girl.getWidth()/2),ToIdleIsSin.HEIGHT/2,0.55,0.55);
        }


        game.batch.end();
        if(line.isCleared() || line.getEnemiesKilled() >= line.getNumberOfEnemies()){
            knight.returnToState(0);
            mage.returnToState(0);
            physician.returnToState(0);
        }else{
            knight.random();
            mage.random();
            physician.random();
        }
        if(line.getKnights() == 0){
            enemy1.returnToState(0);
            boss.returnToState(0);
            enemy2.returnToState(0);
            enemy3.returnToState(0);
        }else{
            enemy1.random();
            boss.random();
            enemy2.random();
            enemy3.random();
        }

        if(dialogue == null) virtueSpeak();

    }

    public void prepareUnits(){
        visible = new HashMap<>();
        visible.put("unit", false);
        visible.put("enemy", false);
        visible.put("boss", false);
        if(knight != null) knight.dispose();
        if(mage != null) mage.dispose();
        if(physician != null) physician.dispose();
        if(enemy1 != null) enemy1.dispose();
        if(enemy3 != null) enemy3.dispose();
        if(boss != null) boss.dispose();
        if(enemy2 != null) enemy2.dispose();
        if(boy != null) boy.dispose();
        if(girl != null) girl.dispose();

        boy = new ImageBlob(game, 1);
        boy.addState("sprites/units/boy", 4, 4, 0.15f);
        boy.addState("sprites/units/boy", 4, 4, 0.15f);
        boy.flip();
        girl = new ImageBlob(game, 1);
        girl.addState("sprites/units/girl", 4, 4, 0.15f);
        girl.addState("sprites/units/girl", 4, 4, 0.15f);
        girl.flip();

        knight = new ImageBlob(game, 1);
        knight.addState("sprites/units/knightidle", 4, 4, 0.15f);
        knight.addState("sprites/units/knightattack", 4, 4, 0.15f);
        knight.flip();
        mage = new ImageBlob(game, 1);
        mage.addState("sprites/units/mageidle", 4, 4, 0.15f);
        mage.addState("sprites/units/mageattack", 4, 4, 0.15f);
        mage.flip();
        physician = new ImageBlob(game, 1);
        physician.addState("sprites/units/physicianidle", 4, 4, 0.15f);
        physician.addState("sprites/units/physicianattack", 4, 4, 0.15f);
        physician.flip();

        enemy1 = new ImageBlob(game, 1);
        enemy1.addState("sprites/units/darkmonkidle", 4, 4, 0.15f);
        enemy1.addState("sprites/units/darkmonkattack", 4, 4, 0.15f);
        enemy2 = new ImageBlob(game, 1);
        enemy2.addState("sprites/units/skeletonidle", 4, 4, 0.15f);
        enemy2.addState("sprites/units/skeletonattack", 4, 4, 0.15f);
        enemy3 = new ImageBlob(game, 1);
        enemy3.addState("sprites/units/darknunidle", 4, 4, 0.15f);
        enemy3.addState("sprites/units/darknunattack", 4, 4, 0.15f);

        boss = new ImageBlob(game, 1);
        virtue = new ImageBlob(game, 1);
        vice = new ImageBlob(game, 1);
        switch (level){
            case 1:
                boss.addState("sprites/units/monsterenvyidle", 4, 4, 0.2f);
                boss.addState("sprites/units/monsterenvyattack", 4, 4, 0.2f);
                bossScaleX = 1.5f;
                bossScaleY = 1.5f;
                bossxoffset = -150;

                virtue.addState("sprites/units/charity", 4,4,0.2f);
                virtueProgress = Program.gameState.getVirtue("charity").getProgress();
                virtueScaleX = 0.8f;
                virtueScaleY = 0.8f;
                virtuexoffset = -50;

                vice.addState("sprites/units/envy", 4,4,0.2f);
                viceScaleX = 0.9f;
                viceScaleY = 0.9f;
                vicexoffset = 0;
                break;
            case 2:
                boss.addState("sprites/units/monsteravariceidle", 4, 4, 0.15f);
                boss.addState("sprites/units/monsteravariceattack", 4, 4, 0.15f);
                bossScaleX = 1.3f;
                bossScaleY = 1.3f;
                bossxoffset = -100;

                virtue.addState("sprites/units/kindness", 4,4,0.2f);
                virtueProgress = Program.gameState.getVirtue("kindness").getProgress();
                virtueScaleX = 0.85f;
                virtueScaleY = 0.85f;
                virtuexoffset = -50;

                vice.addState("sprites/units/avarice", 4,4,0.2f);
                viceScaleX = 0.8f;
                viceScaleY = 0.8f;
                vicexoffset = 0;
                break;
            case 3:
                boss.addState("sprites/units/monstergluttonyidle", 4, 4, 0.15f);
                boss.addState("sprites/units/monstergluttonyattack", 4, 4, 0.15f);
                bossScaleX = 1.2f;
                bossScaleY = 1.2f;
                bossxoffset = -50;

                virtue.addState("sprites/units/diligence", 4,4,0.2f);
                virtueProgress = Program.gameState.getVirtue("diligence").getProgress();
                virtueScaleX = 0.85f;
                virtueScaleY = 0.85f;
                virtuexoffset = -50;

                vice.addState("sprites/units/gluttony", 4,4,0.2f);
                viceScaleX = 0.8f;
                viceScaleY = 0.8f;
                vicexoffset = 0;
                break;
            case 4:
                boss.addState("sprites/units/monsterslothidle", 4, 4, 0.15f);
                boss.addState("sprites/units/monsterslothattack", 4, 4, 0.15f);
                bossScaleX = 1.2f;
                bossScaleY = 1.2f;
                bossxoffset = -25;

                virtue.addState("sprites/units/humility", 4,4,0.2f);
                virtueProgress = Program.gameState.getVirtue("humility").getProgress();
                virtueScaleX = 0.85f;
                virtueScaleY = 0.85f;
                virtuexoffset = -50;

                vice.addState("sprites/units/sloth", 4,4,0.2f);
                viceScaleX = 0.8f;
                viceScaleY = 0.8f;
                vicexoffset = 0;
                break;
            case 5:
                boss.addState("sprites/units/monsterrageidle", 4, 4, 0.15f);
                boss.addState("sprites/units/monsterrageattack", 4, 4, 0.15f);
                bossScaleX = 1.2f;
                bossScaleY = 1.2f;

                virtue.addState("sprites/units/chastity", 4,4,0.2f);
                virtueProgress = Program.gameState.getVirtue("chastity").getProgress();
                virtueScaleX = 1.1f;
                virtueScaleY = 1.1f;
                virtuexoffset = -50;

                vice.addState("sprites/units/wrath", 4,4,0.2f);
                viceScaleX = 0.85f;
                viceScaleY = 0.85f;
                vicexoffset = 0;
                break;
            case 6:
                boss.addState("sprites/units/monsterlustidle", 4, 4, 0.15f);
                boss.addState("sprites/units/monsterlustattack", 4, 4, 0.15f);
                bossScaleX = 1.6f;
                bossScaleY = 1.6f;
                bossxoffset = -200;

                virtue.addState("sprites/units/patience", 4,4,0.2f);
                virtueProgress = Program.gameState.getVirtue("patience").getProgress();
                virtueScaleX = 0.85f;
                virtueScaleY = 0.85f;
                virtuexoffset = -75;

                vice.addState("sprites/units/lusts", 4,4,0.2f);
                viceScaleX = 0.8f;
                viceScaleY = 0.8f;
                vicexoffset = 0;
                break;
            case 7:
                boss.addState("sprites/units/monsterprideidle", 4, 4, 0.15f);
                boss.addState("sprites/units/monsterprideattack", 4, 4, 0.15f);
                bossScaleX = 1.2f;
                bossScaleY = 1.2f;
                bossxoffset = 0;

                virtue.addState("sprites/units/temperance", 4,4,0.2f);
                virtueProgress = Program.gameState.getVirtue("temperance").getProgress();
                virtueScaleX = 0.85f;
                virtueScaleY = 0.85f;
                virtuexoffset = -50;

                vice.addState("sprites/units/pride", 4,4,0.2f);
                viceScaleX = 0.7f;
                viceScaleY = 0.7f;
                vicexoffset = 0;
                break;
            default:
                boss.addState("sprites/units/monsterprideidle", 4, 4, 0.15f);
                boss.addState("sprites/units/monsterprideattack", 4, 4, 0.15f);
                Program.print("Error, there is no level " + level);
                break;
        }
        virtue.flip();


    }

    public void initDialogue(){
        int width = (int)(ToIdleIsSin.WIDTH*5/6.0);
        int height = ToIdleIsSin.HEIGHT/3;
        dialoguePopUp = new DialoguePopUp(game, width, height);
        dialoguePopUp.getPopup().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speak();
                super.clicked(event, x, y);
            }
        });

        dialoguePopUp.getPopup().setPosition(ToIdleIsSin.WIDTH/12, 300);
        stage.addActor(dialoguePopUp.getPopup());
        dialoguePopUp.getPopup().setVisible(false);
    }

    public void speak(){
        if(dialogue.next()){
            Program.gameState.pause();
            dialoguePopUp.getPopup().setVisible(true);
            dialoguePopUp.setWho(dialogue.getCharacter());
            dialoguePopUp.setWords(dialogue.getMessage());

        } else {
            dialoguePopUp.setWords("");
            dialoguePopUp.setWho("");
            dialoguePopUp.getPopup().setVisible(false);
            dialogue = null;
            releventVisible(0);
            Program.gameState.resume();
        }
    }

    public void virtueSpeak(){
        Dialogue virtueWords;
        if( (3 == Program.gameState.getVirtue(selectedVirtue).getProgress()) && (line.getEnemiesKilled() / line.getNumberOfEnemies() >= 1.0 && line.getWHICH_LINE() == 1)){
            releventVisible(3);
            virtueWords = game.dialogue.characterMainDialogue(selectedVirtue);
            Program.gameState.getVirtue(selectedVirtue).setProgress(4);
        } else if (2 == Program.gameState.getVirtue(selectedVirtue).getProgress() && (line.getEnemiesKilled() / line.getNumberOfEnemies() > 0.85 &&  line.getEnemiesKilled() / line.getNumberOfEnemies() <= 1.0  && line.getWHICH_LINE() == 1)){
            releventVisible(2);
            virtueWords = game.dialogue.characterMainDialogue(selectedVirtue);
            Program.gameState.getVirtue(selectedVirtue).setProgress(3);
        } else {
            return;
        }
        dialogue = virtueWords;
        speak();
    }

    public boolean isVisible(String character){
        return visible.get(character);
    }

    public void releventVisible(int progress){
        switch (progress){
            case 2:
                visible.put("unit", true);
                visible.put("enemy", false);
                visible.put("boss", false);
                break;
            case 3:
                visible.put("unit", true);
                visible.put("enemy", false);
                visible.put("boss", true);
                break;
            default:
                visible.put("unit", false);
                visible.put("enemy", false);
                visible.put("boss", false);
                break;
        }
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
        knight.dispose();
        mage.dispose();
        physician.dispose();
        enemy1.dispose();
        enemy2.dispose();
        stage.dispose();
    }
}