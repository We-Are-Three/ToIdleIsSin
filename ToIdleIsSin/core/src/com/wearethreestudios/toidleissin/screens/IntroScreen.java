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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.Dialogue;
import com.wearethreestudios.toidleissin.uihelpers.DialoguePopUp;
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;
import com.wearethreestudios.toidleissin.uihelpers.ScrollImage;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroScreen extends ScreenAdapter {
    private ToIdleIsSin game;
    private Stage stage;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector3 touch = new Vector3();
    private long lastTime;
    private int dialogueCount = 0;


    private ScrollImage firstscroll;

    private ImageBlob background;

    private ImageBlob knight;
    private ImageBlob mage;
    private ImageBlob charity;

    private DialoguePopUp dialoguePopUp;
    private Dialogue dialogue;
    Runnable transitionRunnable;

    public IntroScreen(ToIdleIsSin game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
        stage = new Stage(gamePort);
        dialogue = game.dialogue.characterMainDialogue("charity", "Intro");

        background = new ImageBlob(game, 1.0);
        background.addState("village/cathedral/cathedral_inside", 1, 1, 0.15f);
        background.setVisible(false);

        stage.addActor(background);

        firstscroll = new ScrollImage(game, "campaign/" + "1" + "/" + "one" +"/scroll-", 6);
        firstscroll.get().setPosition(0, ToIdleIsSin.HEIGHT/2-300);
        firstscroll.get().setVisible(true);
        stage.addActor(firstscroll.get());

        knight = new ImageBlob(game, 1);
        knight.addState("sprites/units/knightidle", 4, 4, 0.08f);
        knight.flip();

        mage = new ImageBlob(game, 1);
        mage.addState("sprites/units/mageidle", 4, 4, 0.08f);
        mage.flip();

        charity = new ImageBlob(game, 1.0);
        charity.addState("sprites/units/charity", 4,4,0.08f);

        stage.addActor(mage);
        stage.addActor(knight);
        stage.addActor(charity);

        knight.setBlobScale((float)(1.2*0.8),(float)(1.2*0.8));
        mage.setBlobScale((float)(0.75*0.8),(float)(0.75*0.8));
        charity.setBlobScale((float)(0.8*0.8),(float)(0.8*0.8));

        knight.setPosition((int)(0 - knight.getWidth()/2),ToIdleIsSin.HEIGHT/2-300);
        mage.setPosition((int)(50 - mage.getWidth()/2),ToIdleIsSin.HEIGHT/2);
        charity.setPosition((int) (750 + charity.getWidth()/2), ToIdleIsSin.HEIGHT/2-200);
        mage.addAction(Actions.fadeOut(0f));
        charity.addAction(Actions.fadeOut(0f));

        lastTime = 0;
    }


    @Override
    public void show() {
        transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.setScreen(new VillageScreen(game));
            }
        };

        InputProcessor general = new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                gamePort.unproject(touch.set(screenX, screenY, 0));
                game.setScreen(new VillageScreen(game));
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean keyDown(int keycode) {
                game.setScreen(new VillageScreen(game));
                return super.keyDown(keycode);
            }
        };
        InputMultiplexer mult = new InputMultiplexer();
        mult.addProcessor(general);
        mult.addProcessor(stage);
        Gdx.input.setInputProcessor(mult);
        initDialogue();
    }

    public void initDialogue(){
        int width = (int)(ToIdleIsSin.WIDTH*5/6.0);
        int height = ToIdleIsSin.HEIGHT/4;
        dialoguePopUp = new DialoguePopUp(game, width, height);
        dialoguePopUp.getPopup().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speak();
                super.clicked(event, x, y);
            }
        });

        dialoguePopUp.getPopup().setPosition(ToIdleIsSin.WIDTH/12, 140);
        stage.addActor(dialoguePopUp.getPopup());
        dialoguePopUp.getPopup().setVisible(false);
    }

    public void speak(){
        if(dialogue.next()){
            dialoguePopUp.getPopup().setVisible(true);
            dialoguePopUp.setWho(dialogue.getCharacter());
            dialoguePopUp.setWords(dialogue.getMessage());

        } else {
            dialoguePopUp.setWords("");
            dialoguePopUp.setWho("");
            dialoguePopUp.getPopup().setVisible(false);
            dialogue = null;
            game.setScreen(new VillageScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
        if(Program.realTime() - lastTime > 3000){
            dialogueCount++;
            speak();
            switch (dialogueCount){
                case 1:
                    dialoguePopUp.getPopup().setVisible(false);
                    break;
                case 6:
                    mage.addAction(Actions.fadeIn(1f));
                    dialoguePopUp.getPopup().setVisible(false);
                    break;
                case 9:
                    firstscroll.get().setVisible(false);
                    background.setVisible(true);
                    dialoguePopUp.getPopup().setVisible(false);
                    break;
                case 13:
                    charity.addAction(Actions.fadeIn(1f));
                    dialoguePopUp.getPopup().setVisible(false);
                    break;
                default:
                    break;
            }

            lastTime = Program.realTime();
        }
    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}