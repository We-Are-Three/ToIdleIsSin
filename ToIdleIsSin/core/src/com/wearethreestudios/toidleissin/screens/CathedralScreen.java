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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Perk;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.uihelpers.NavButtons;
import com.wearethreestudios.toidleissin.uihelpers.PerkPopUp;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class CathedralScreen extends ScreenAdapter {
    ToIdleIsSin game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private TextureRegion background;

    private TextButton village;
    private TextButton campaign;
    private TextButton story;
    private Stage stage;
    private ImageButton charity;
    private ImageButton chastity;
    private ImageButton diligence;
    private ImageButton humility;
    private ImageButton kindness;
    private ImageButton patience;
    private ImageButton temperance;

    private Table charityMenu;
    private PerkPopUp charitypop;
    private Table chastityMenu;
    private PerkPopUp chastitypop;
    private Table diligenceMenu;
    private PerkPopUp diligencepop;
    private Table humilityMenu;
    private PerkPopUp humilitypop;
    private Table kindnessMenu;
    private PerkPopUp kindnesspop;
    private Table patienceMenu;
    private PerkPopUp patiencepop;
    private Table temperanceMenu;
    private PerkPopUp temperancepop;

    public CathedralScreen(ToIdleIsSin game) {
        game.getScreen().dispose();
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);

        stage = new Stage(gamePort);
        initButtons();
        initPerks();

        background = game.atlas.findRegion("village/cathedral/cathedral_inside");
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
        if(charitypop != null){
            charitypop.update();
            chastitypop.update();
            diligencepop.update();
            humilitypop.update();
            kindnesspop.update();
            patiencepop.update();
            temperancepop.update();
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

    private void initPerks(){
        charitypop = new PerkPopUp("charity", game);
        charityMenu = charitypop.getPopup();
        TextureRegionDrawable a = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        charityMenu.setBackground(a);
//        charityMenu.setPosition((int)(ToIdleIsSin.WIDTH*0.075), (int)(ToIdleIsSin.HEIGHT*0.1));
        charityMenu.setPosition(0, 0);
        charityMenu.pack();
        stage.addActor(charityMenu);
        charityMenu.setVisible(false);

        chastitypop = new PerkPopUp("chastity", game);
        chastityMenu = chastitypop.getPopup();
        TextureRegionDrawable b = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        chastityMenu.setBackground(b);
//        chastityMenu.setPosition((int)(ToIdleIsSin.WIDTH*0.075), (int)(ToIdleIsSin.HEIGHT*0.1));
        chastityMenu.setPosition(0, 0);
        chastityMenu.pack();
        stage.addActor(chastityMenu);
        chastityMenu.setVisible(false);

        temperancepop = new PerkPopUp("temperance", game);
        temperanceMenu = temperancepop.getPopup();
        TextureRegionDrawable c = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        temperanceMenu.setBackground(c);
//        temperanceMenu.setPosition((int)(ToIdleIsSin.WIDTH*0.075), (int)(ToIdleIsSin.HEIGHT*0.1));
        temperanceMenu.setPosition(0, 0);
        temperanceMenu.pack();
        stage.addActor(temperanceMenu);
        temperanceMenu.setVisible(false);

        diligencepop = new PerkPopUp("diligence", game);
        diligenceMenu = diligencepop.getPopup();
        TextureRegionDrawable d = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        diligenceMenu.setBackground(d);
//        diligenceMenu.setPosition((int)(ToIdleIsSin.WIDTH*0.075), (int)(ToIdleIsSin.HEIGHT*0.1));
        diligenceMenu.setPosition(0, 0);
        diligenceMenu.pack();
        stage.addActor(diligenceMenu);
        diligenceMenu.setVisible(false);

        kindnesspop = new PerkPopUp("kindness", game);
        kindnessMenu = kindnesspop.getPopup();
        TextureRegionDrawable e = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        kindnessMenu.setBackground(e);
//        kindnessMenu.setPosition((int)(ToIdleIsSin.WIDTH*0.075), (int)(ToIdleIsSin.HEIGHT*0.1));
        kindnessMenu.setPosition(0, 0);
        kindnessMenu.pack();
        stage.addActor(kindnessMenu);
        kindnessMenu.setVisible(false);

        patiencepop = new PerkPopUp("patience", game);
        patienceMenu = patiencepop.getPopup();
        TextureRegionDrawable f = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        patienceMenu.setBackground(f);
//        patienceMenu.setPosition((int)(ToIdleIsSin.WIDTH*0.075), (int)(ToIdleIsSin.HEIGHT*0.1));
        patienceMenu.setPosition(0, 0);
        patienceMenu.pack();
        stage.addActor(patienceMenu);
        patienceMenu.setVisible(false);

        humilitypop = new PerkPopUp("humility", game);
        humilityMenu = humilitypop.getPopup();
        TextureRegionDrawable g = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        humilityMenu.setBackground(g);
//        humilityMenu.setPosition((int)(ToIdleIsSin.WIDTH*0.075), (int)(ToIdleIsSin.HEIGHT*0.1));
        humilityMenu.setPosition(0, 0);
        humilityMenu.pack();
        stage.addActor(humilityMenu);
        humilityMenu.setVisible(false);
    }

    private void initButtons(){
        village = NavButtons.getVillage(game);
        campaign = NavButtons.getCampaign(game);
        story = NavButtons.getStory(game);

        charity = new ImageButton(game.skin, "charity");
        charity.setPosition((int)(ToIdleIsSin.WIDTH*0.255), (int)(ToIdleIsSin.HEIGHT*0.294));
        charity.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!charity.isDisabled()){
                    ToIdleIsSin.program.run("virtue skill tree");
                    ToIdleIsSin.program.run("charityperks");
                    charitypop.update();
                    charityMenu.setVisible(true);
                    game.sound = game.assets.get("sound/jingle.mp3", Sound.class);
                    game.sound.play();
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("charity").getProgress() == 0){
            charity.setDisabled(true);
        }

        chastity = new ImageButton(game.skin, "chastity");
        chastity.setPosition((int)(ToIdleIsSin.WIDTH*0.255), (int)(ToIdleIsSin.HEIGHT*0.128));
        chastity.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!chastity.isDisabled()){
                    ToIdleIsSin.program.run("virtue skill tree");
                    ToIdleIsSin.program.run("chastityperks");
                    chastitypop.update();
                    chastityMenu.setVisible(true);
                    game.sound = game.assets.get("sound/jingle.mp3", Sound.class);
                    game.sound.play();
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("chastity").getProgress() == 0){
            chastity.setDisabled(true);
        }

        diligence = new ImageButton(game.skin, "diligence");
        diligence.setPosition((int)(ToIdleIsSin.WIDTH*0.54), (int)(ToIdleIsSin.HEIGHT*0.294));
        diligence.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!diligence.isDisabled()){
                    ToIdleIsSin.program.run("virtue skill tree");
                    ToIdleIsSin.program.run("diligenceperks");
                    diligencepop.update();
                    diligenceMenu.setVisible(true);
                    game.sound = game.assets.get("sound/jingle.mp3", Sound.class);
                    game.sound.play();
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("diligence").getProgress() == 0){
            diligence.setDisabled(true);
        }

        humility = new ImageButton(game.skin, "humility");
        humility.setPosition((int)(ToIdleIsSin.WIDTH*0.105), (int)(ToIdleIsSin.HEIGHT*0.212));
        humility.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!humility.isDisabled()){
                    ToIdleIsSin.program.run("virtue skill tree");
                    ToIdleIsSin.program.run("humilityperks");
                    humilitypop.update();
                    humilityMenu.setVisible(true);
                    game.sound = game.assets.get("sound/jingle.mp3", Sound.class);
                    game.sound.play();
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("humility").getProgress() == 0){
            humility.setDisabled(true);
        }

        kindness = new ImageButton(game.skin, "kindness");
        kindness.setPosition((int)(ToIdleIsSin.WIDTH*0.395), (int)(ToIdleIsSin.HEIGHT*0.212));
        kindness.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!kindness.isDisabled()){
                    ToIdleIsSin.program.run("virtue skill tree");
                    ToIdleIsSin.program.run("kindnessperks");
                    kindnesspop.update();
                    kindnessMenu.setVisible(true);
                    game.sound = game.assets.get("sound/jingle.mp3", Sound.class);
                    game.sound.play();
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("kindness").getProgress() == 0){
            kindness.setDisabled(true);
        }

        patience = new ImageButton(game.skin, "patience");
        patience.setPosition((int)(ToIdleIsSin.WIDTH*0.68), (int)(ToIdleIsSin.HEIGHT*0.212));
        patience.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!patience.isDisabled()){
                    ToIdleIsSin.program.run("virtue skill tree");
                    ToIdleIsSin.program.run("patienceperks");
                    patiencepop.update();
                    patienceMenu.setVisible(true);
                    game.sound = game.assets.get("sound/jingle.mp3", Sound.class);
                    game.sound.play();
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("patience").getProgress() == 0){
            patience.setDisabled(true);
        }

        temperance = new ImageButton(game.skin, "temperance");
        temperance.setPosition((int)(ToIdleIsSin.WIDTH*0.54), (int)(ToIdleIsSin.HEIGHT*0.128));
        temperance.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!temperance.isDisabled()){
                    ToIdleIsSin.program.run("virtue skill tree");
                    ToIdleIsSin.program.run("temperanceperks");
                    temperancepop.update();
                    temperanceMenu.setVisible(true);
                    game.sound = game.assets.get("sound/jingle.mp3", Sound.class);
                    game.sound.play();
                }
                super.clicked(event, x, y);
            }
        });
        if(Program.gameState.getVirtue("temperance").getProgress() == 0){
            temperance.setDisabled(true);
        }

        stage.addActor(village);
        stage.addActor(campaign);
        stage.addActor(story);
        stage.addActor(charity);
        stage.addActor(chastity);
        stage.addActor(diligence);
        stage.addActor(humility);
        stage.addActor(kindness);
        stage.addActor(patience);
        stage.addActor(temperance);

    }
}
