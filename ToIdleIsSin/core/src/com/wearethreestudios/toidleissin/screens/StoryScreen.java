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

//    private ImageBlob walkingMan;
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

    private ImageBlob selectedVirtue;
    private float previousX = -1;
    private float previousY = -1;

    private ArrayList<ImageBlob> characters;

    private DialoguePopUp dialoguePopUp;
    private Dialogue dialogue;

    private void initButtons(){
        village = NavButtons.getVillage(game);
        campaign = NavButtons.getCampaign(game);
        story = NavButtons.getStory(game);
//        village.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new VillageScreen(game));
//                ToIdleIsSin.program.run("village");
//                super.clicked(event, x, y);
//            }
//
//            @Override
//            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                village.addAction(
//                        forever(sequence(
//                                scaleTo(1.1f, 1.1f, 0.1f, Interpolation.pow5),
//                                scaleTo(1f, 1f, 0.1f, Interpolation.pow5))));
//                super.enter(event, x, y, pointer, fromActor);
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                village.clearActions();
//                super.exit(event, x, y, pointer, toActor);
//            }
//
//        });

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
        dialoguePopUp = new DialoguePopUp(game);
        dialoguePopUp.getPopup().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speak();
                super.clicked(event, x, y);
            }
        });

        characters = new ArrayList<>();
//        walkingMan = new ImageBlob(game, 1);
//        walkingMan.addState("sprite-animation4", 5, 6, 0.025f, 1, 1);
//        walkingMan.addState("sprite-animation4", 5, 6, 0.025f);

        charity = new ImageBlob(game, 0.75);
        charity.addState("sprites/charity", 1, 1, 0.025f);
        charity.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialogue charityWords;
                if(Program.gameState.getCampaign("campaign1").isCleared() && (1 == Program.gameState.getVirtue("charity").getProgress())){
                    Program.gameState.getVirtue("charity").setProgress(2);
                    charityWords = game.dialogue.characterMainDialogue("charity");
                    Program.gameState.getVirtue("kindness").setProgress(1);
                } else if (1 == Program.gameState.getVirtue("charity").getProgress()){
                    charityWords = game.dialogue.characterMainDialogue("charity");
                } else {
                    charityWords = game.dialogue.characterRandom("charity");
                }
                dialogue = charityWords;
                selectedVirtue = charity;
                speak();
                
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
                Dialogue kindnessWords;
                if(Program.gameState.getCampaign("campaign2").isCleared() && (1 == Program.gameState.getVirtue("kindness").getProgress())){
                    Program.gameState.getVirtue("kindness").setProgress(2);
                    kindnessWords = game.dialogue.characterMainDialogue("kindness");
                    Program.gameState.getVirtue("diligence").setProgress(1);
                } else if (1 == Program.gameState.getVirtue("kindness").getProgress()){
                    kindnessWords = game.dialogue.characterMainDialogue("kindness");
                } else {
                    kindnessWords = game.dialogue.characterRandom("kindness");
                }
                dialogue = kindnessWords;
                selectedVirtue = kindness;
                speak();
                super.clicked(event, x, y);
            }
        });

        diligence = new ImageBlob(game, 0.75);
        diligence.addState("sprites/diligence", 1, 1, 0.025f);
        diligence.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialogue diligenceWords;
                if(Program.gameState.getCampaign("campaign3").isCleared() && (1 == Program.gameState.getVirtue("diligence").getProgress())){
                    Program.gameState.getVirtue("diligence").setProgress(2);
                    diligenceWords = game.dialogue.characterMainDialogue("diligence");
                    Program.gameState.getVirtue("humility").setProgress(1);
                } else if (1 == Program.gameState.getVirtue("diligence").getProgress()){
                    diligenceWords = game.dialogue.characterMainDialogue("diligence");
                } else {
                    diligenceWords = game.dialogue.characterRandom("diligence");
                }
                dialogue = diligenceWords;
                selectedVirtue = diligence;
                speak();
                super.clicked(event, x, y);
            }
        });

        humility = new ImageBlob(game, 0.75);
        humility.addState("sprites/humility", 1, 1, 0.025f);
        humility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialogue humilityWords;
                if(Program.gameState.getCampaign("campaign4").isCleared() && (1 == Program.gameState.getVirtue("humility").getProgress())){
                    Program.gameState.getVirtue("humility").setProgress(2);
                    humilityWords = game.dialogue.characterMainDialogue("humility");
                    Program.gameState.getVirtue("chastity").setProgress(1);
                } else if (1 == Program.gameState.getVirtue("humility").getProgress()){
                    humilityWords = game.dialogue.characterMainDialogue("humility");
                } else {
                    humilityWords = game.dialogue.characterRandom("humility");
                }
                dialogue = humilityWords;
                selectedVirtue = humility;
                speak();
                super.clicked(event, x, y);
            }
        });
        
        chastity = new ImageBlob(game, 0.75);
        chastity.addState("sprites/chastity", 1, 1, 0.025f);
        chastity.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialogue chastityWords;
                if(Program.gameState.getCampaign("campaign5").isCleared() && (1 == Program.gameState.getVirtue("chastity").getProgress())){
                    Program.gameState.getVirtue("chastity").setProgress(2);
                    chastityWords = game.dialogue.characterMainDialogue("chastity");
                    Program.gameState.getVirtue("patience").setProgress(1);
                } else if (1 == Program.gameState.getVirtue("chastity").getProgress()){
                    chastityWords = game.dialogue.characterMainDialogue("chastity");
                } else {
                    chastityWords = game.dialogue.characterRandom("chastity");
                }
                dialogue = chastityWords;
                selectedVirtue = chastity;
                speak();
                super.clicked(event, x, y);
            }
        });
        
        patienceman = new ImageBlob(game, 0.75);
        patienceman.addState("sprites/patienceman", 1, 1, 0.025f);
        patienceman.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialogue patienceWords;
                if(Program.gameState.getCampaign("campaign6").isCleared() && (1 == Program.gameState.getVirtue("patience").getProgress())){
                    Program.gameState.getVirtue("patience").setProgress(2);
                    patienceWords = game.dialogue.characterMainDialogue("patience");
                    Program.gameState.getVirtue("temperance").setProgress(1);
                } else if (1 == Program.gameState.getVirtue("patience").getProgress()){
                    patienceWords = game.dialogue.characterMainDialogue("patience");
                } else {
                    patienceWords = game.dialogue.characterRandom("patience");
                }
                dialogue = patienceWords;
                selectedVirtue = patienceman;
                speak();
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
                Dialogue temperanceWords;
                if(Program.gameState.getCampaign("campaign7").isCleared() && (1 == Program.gameState.getVirtue("temperance").getProgress())){
                    Program.gameState.getVirtue("temperance").setProgress(2);
                    temperanceWords = game.dialogue.characterMainDialogue("temperance");
                } else if (1 == Program.gameState.getVirtue("temperance").getProgress()){
                    temperanceWords = game.dialogue.characterMainDialogue("temperance");
                } else {
                    temperanceWords = game.dialogue.characterRandom("temperance");
                }
                dialogue = temperanceWords;
                selectedVirtue = temperance;
                speak();
                super.clicked(event, x, y);
            }
        });


        stage.addActor(charity);
        charity.setPosition(550,1100);
//        charity.debug();

        stage.addActor(patiencekid);
        patiencekid.setPosition(50,1050);
//        patiencekid.debug();

        stage.addActor(patienceman);
        patienceman.setPosition(200,1100);
//        patienceman.debug();
        if(Program.gameState.getVirtue("patience").getProgress() == 0){
            patienceman.setVisible(false);
            patiencekid.setVisible(false);
        }

        stage.addActor(kindness);
        kindness.setPosition(0,650);
        kindness.flip();
//        kindness.debug();
        if(Program.gameState.getVirtue("kindness").getProgress() == 0){
            kindness.setVisible(false);
        }

        stage.addActor(temperance);
        temperance.setPosition(350,700);
//        temperance.debug();
        if(Program.gameState.getVirtue("temperance").getProgress() == 0){
            temperance.setVisible(false);
        }

        stage.addActor(chastity);
        chastity.setPosition(600,200);
//        chastity.debug();
        if(Program.gameState.getVirtue("chastity").getProgress() == 0){
            chastity.setVisible(false);
        }

        stage.addActor(diligence);
        diligence.setPosition(250,200);
//        diligence.debug();
        if(Program.gameState.getVirtue("diligence").getProgress() == 0){
            diligence.setVisible(false);
        }

        stage.addActor(humility);
        humility.setPosition(-100,200);
        humility.flip();
//        humility.debug();
        if(Program.gameState.getVirtue("humility").getProgress() == 0){
            humility.setVisible(false);
        }

        characters.add(charity);
        characters.add(patiencekid);
        characters.add(patienceman);
        characters.add(kindness);
        characters.add(temperance);
        characters.add(chastity);
        characters.add(diligence);
        characters.add(humility);

        allInvisible();
        allVisible();

        dialoguePopUp.getPopup().setPosition(ToIdleIsSin.WIDTH/6, 300);
        stage.addActor(dialoguePopUp.getPopup());
        dialoguePopUp.getPopup().setVisible(false);
    }

    public void allVisible(){
        if(Program.gameState.getVirtue("charity").getProgress() != 0){
            charity.setVisible(true);
        }
        if(Program.gameState.getVirtue("patience").getProgress() != 0){
            patienceman.setVisible(true);
            patiencekid.setVisible(true);
        }
        if(Program.gameState.getVirtue("kindness").getProgress() != 0){
            kindness.setVisible(true);
        }
        if(Program.gameState.getVirtue("temperance").getProgress() != 0){
            temperance.setVisible(true);
        }
        if(Program.gameState.getVirtue("chastity").getProgress() != 0){
            chastity.setVisible(true);
        }
        if(Program.gameState.getVirtue("diligence").getProgress() != 0){
            diligence.setVisible(true);
        }
        if(Program.gameState.getVirtue("humility").getProgress() != 0){
            humility.setVisible(true);
        }
    }

    public void allInvisible(){
        charity.setVisible(false);
        patienceman.setVisible(false);
        patiencekid.setVisible(false);
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
            Program.print("" + dialogue.getCharacter() + ": " + dialogue.getMessage());
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

//        walkingMan.draw(250,250,200,200);

        game.batch.end();
        stage.act(delta);
        stage.draw();

//        walkingMan.returnToState(0);
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
//                if(walkingMan.contains(touch.x, touch.y)){
//                    walkingMan.switchState(1);
//                }
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
//        walkingMan.dispose();
        stage.dispose();
    }
}
