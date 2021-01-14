package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.Dialogue;
import com.wearethreestudios.toidleissin.uihelpers.DialoguePopUp;
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class StoryScreen extends ScreenAdapter {
//    https://gamefromscratch.com/libgdx-tutorial-2-hello-world/
    ToIdleIsSin game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Vector3 touch = new Vector3();

    private TextureRegion background;

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
    private ImageBlob patienceman;
    private ImageBlob temperance;

    private ImageBlob selectedVirtue;
    private float previousX = -1;
    private float previousY = -1;

    private DialoguePopUp dialoguePopUp;
    private Dialogue dialogue;

    private void initButtons(){
        village = NavButtons.getVillage(game);
        campaign = NavButtons.getCampaign(game);
        story = NavButtons.getStory(game);

        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);

    }


    public StoryScreen(final ToIdleIsSin game) {
        game.getScreen().dispose();
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
        stage = new Stage(gamePort);
        initButtons();

        background = game.atlas.findRegion("story/cottage");
        initSprites();

        door = new ImageBlob(game, 0.75);
        door.addState("story/door", 1,  1, 0.025f)
                .addState("story/door-click", 1,  1, 0.025f);

    }

    public void initSprites(){
        int width = (int)(ToIdleIsSin.WIDTH*5/6.0);
        int height = (int)(ToIdleIsSin.HEIGHT*3.0/12);
        dialoguePopUp = new DialoguePopUp(game, (int)(ToIdleIsSin.WIDTH*1.2), height);
        dialoguePopUp.getPopup().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speak();
                super.clicked(event, x, y);
            }
        });


        charity = new ImageBlob(game, 0.75);
        charity.addState("sprites/units/charity", 4,  4, 0.08f);
        charity.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                virtueSpeak("charity", 1, "kindness");
                super.clicked(event, x, y);
            }
        });

        kindness = new ImageBlob(game, 0.75);
        kindness.addState("sprites/units/kindness", 4,  4, 0.08f);
        kindness.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                virtueSpeak("kindness", 2, "diligence");
                super.clicked(event, x, y);
            }
        });

        diligence = new ImageBlob(game, 0.75);
        diligence.addState("sprites/units/diligence", 4,  4, 0.08f);
        diligence.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                virtueSpeak("diligence", 3, "humility");
                super.clicked(event, x, y);
            }
        });

        humility = new ImageBlob(game, 0.75);
        humility.addState("sprites/units/humility", 4,  4, 0.08f);
        humility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                virtueSpeak("humility", 4, "chastity");
                super.clicked(event, x, y);
            }
        });
        
        chastity = new ImageBlob(game, 0.6);
        chastity.addState("sprites/units/chastity", 4,  4, 0.08f);
        chastity.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                virtueSpeak("chastity", 5, "patience");
                super.clicked(event, x, y);
            }
        });
        
        patienceman = new ImageBlob(game, 0.75);
        patienceman.addState("sprites/units/patience", 4,  4, 0.08f);
        patienceman.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                virtueSpeak("patience", 6, "temperance");
                super.clicked(event, x, y);
            }
        });
        
        temperance = new ImageBlob(game, 0.75);
        temperance.addState("sprites/units/temperance", 4,  4, 0.08f);
        temperance.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                virtueSpeak("temperance", 7, null);
                super.clicked(event, x, y);
            }
        });


        stage.addActor(charity);
        charity.setPosition(550,1100);
        charity.setBlobScale(0.9f, 0.9f);

        stage.addActor(patienceman);
        patienceman.setPosition(150,1100);

        stage.addActor(kindness);
        kindness.setPosition(0,650);
        kindness.flip();

        stage.addActor(temperance);
        temperance.setPosition(350,700);

        stage.addActor(chastity);
        chastity.setPosition(450,200);
        chastity.setBlobScale(1.5f, 1.5f);
        chastity.setHitOffset(0.2f, 0);

        stage.addActor(diligence);
        diligence.setPosition(250,200);

        stage.addActor(humility);
        humility.setPosition(-100,200);
        humility.flip();

        allInvisible();
        allVisible();

        dialoguePopUp.getPopup().setPosition((int)(-ToIdleIsSin.WIDTH*0.1), 300);
        stage.addActor(dialoguePopUp.getPopup());
        dialoguePopUp.getPopup().setVisible(false);
    }

    public void allVisible(){
        charity.setVisible(true);
        if(Program.gameState.getVirtue("patience").getProgress() >= 1 && Program.gameState.getCampaign("campaign6").getSecondLine().isCleared()){
            patienceman.setVisible(true);
        }
        if(Program.gameState.getVirtue("kindness").getProgress() >= 1 && Program.gameState.getCampaign("campaign2").getSecondLine().isCleared()){
            kindness.setVisible(true);
        }
        if(Program.gameState.getVirtue("temperance").getProgress() >= 1 && Program.gameState.getCampaign("campaign7").getSecondLine().isCleared()){
            temperance.setVisible(true);
        }
        if(Program.gameState.getVirtue("chastity").getProgress() >= 1 && Program.gameState.getCampaign("campaign5").getSecondLine().isCleared()){
            chastity.setVisible(true);
        }
        if(Program.gameState.getVirtue("diligence").getProgress() >= 1 && Program.gameState.getCampaign("campaign3").getSecondLine().isCleared()){
            diligence.setVisible(true);
        }
        if(Program.gameState.getVirtue("humility").getProgress() >= 1 && Program.gameState.getCampaign("campaign4").getSecondLine().isCleared()){
            humility.setVisible(true);
        }
    }

    public void virtueSpeak(String virtue, int number, String nextVirtue){
        Dialogue virtueWords;
        if(Program.gameState.getCampaign("campaign" + number).isCleared() && (4 == Program.gameState.getVirtue(virtue).getProgress())){
            virtueWords = game.dialogue.characterMainDialogue(virtue);
            Program.gameState.getVirtue(virtue).setProgress(5);
            if(nextVirtue != null) Program.gameState.getVirtue(nextVirtue).setProgress(1);
        } else if (1 == Program.gameState.getVirtue(virtue).getProgress() && Program.gameState.getCampaign("campaign" + number).getSecondLine().isCleared()){
            virtueWords = game.dialogue.characterMainDialogue(virtue);
            Program.gameState.getVirtue(virtue).setProgress(2);
        }
        else {
//            virtueWords = game.dialogue.characterRandom(virtue);
            return;
        }
        dialogue = virtueWords;
        switch (virtue){
            case "charity":
                selectedVirtue = charity;
                break;
            case "patience":
                selectedVirtue = patienceman;
                break;
            case "kindness":
                selectedVirtue = kindness;
                break;
            case "temperance":
                selectedVirtue = temperance;
                break;
            case "chastity":
                selectedVirtue = chastity;
                break;
            case "diligence":
                selectedVirtue = diligence;
                break;
            case "humility":
                selectedVirtue = humility;
                break;
            default:
                break;

        }
        speak();
    }

    public void allInvisible(){
        if(Program.gameState.getVirtue("temperance").getProgress() == 5){
            Program.gameState.getVirtue("temperance").setProgress(6);
            return;
        }
        charity.setVisible(false);
        patienceman.setVisible(false);
        kindness.setVisible(false);
        temperance.setVisible(false);
        chastity.setVisible(false);
        diligence.setVisible(false);
        humility.setVisible(false);
    }

    public void speak(){
        if(previousX == -1){
            allInvisible();
            selectedVirtue.setVisible(true);
            previousX = selectedVirtue.getX();
            previousY = selectedVirtue.getY();
            selectedVirtue.setTouchable(Touchable.disabled);
            selectedVirtue.addAction(Actions.moveTo(ToIdleIsSin.WIDTH/2 - selectedVirtue.getWidth()/2, ToIdleIsSin.HEIGHT/2 -selectedVirtue.getHeight()/2, 0.5f));
        }
        if(dialogue.next()){
            dialoguePopUp.getPopup().setVisible(true);
            dialoguePopUp.setWho(dialogue.getCharacter());
            dialoguePopUp.setWords(dialogue.getMessage());

        } else {
            dialoguePopUp.setWords("");
            dialoguePopUp.setWho("");
            dialoguePopUp.getPopup().setVisible(false);
            dialogue = null;
            selectedVirtue.addAction(Actions.moveTo(previousX, previousY, 0.5f));
            selectedVirtue.setTouchable(Touchable.enabled);
            selectedVirtue = null;
            previousX = -1;
            previousY = -1;
            allVisible();
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
    public void dispose() {
        door.dispose();
        stage.dispose();
    }
}
