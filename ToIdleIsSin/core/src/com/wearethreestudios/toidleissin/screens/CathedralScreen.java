package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;

public class CathedralScreen extends ScreenAdapter {
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

    private Texture background;

    private Texture charity;
    private Rectangle charityBounds;
    private Texture chastity;
    private Rectangle chastityBounds;
    private Texture diligence;
    private Rectangle diligenceBounds;
    private Texture humility;
    private Rectangle humilityBounds;
    private Texture kindness;
    private Rectangle kindnessBounds;
    private Texture patience;
    private Rectangle patienceBounds;
    private Texture temperance;
    private Rectangle temperanceBounds;

    public CathedralScreen(ToIdleIsSin game) {
        this.game = game;
        background = new Texture("village/cathedral/cathedral_inside.png");

        charity = new Texture("village/cathedral/charity.png");
        chastity = new Texture("village/cathedral/chastity.png");
        diligence = new Texture("village/cathedral/diligence.png");
        humility = new Texture("village/cathedral/humility.png");
        kindness = new Texture("village/cathedral/kindness.png");
        patience = new Texture("village/cathedral/patience.png");
        temperance = new Texture("village/cathedral/temperance.png");

        buttonVillage = new Texture("button_village.png");
        buttonCampaigns = new Texture("button_campaigns.png");
        buttonStory = new Texture("button_story.png");

        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT);
        gamePort = new FitViewport(ToIdleIsSin.WIDTH, ToIdleIsSin.HEIGHT, gamecam);
    }

    @Override
    public void render(float delta) {
        ToIdleIsSin.program.runNextCommand();
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

        // Buildings
        game.batch.draw(charity, (int)(ToIdleIsSin.WIDTH*0.255), (int)(ToIdleIsSin.HEIGHT*0.294), charity.getWidth(), charity.getHeight());
        charityBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.255), (int)(ToIdleIsSin.HEIGHT*0.294), charity.getWidth(), charity.getHeight());
        
        game.batch.draw(chastity, (int)(ToIdleIsSin.WIDTH*0.255), (int)(ToIdleIsSin.HEIGHT*0.128), chastity.getWidth(), chastity.getHeight());
        chastityBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.255), (int)(ToIdleIsSin.HEIGHT*0.128), chastity.getWidth(), chastity.getHeight());

        game.batch.draw(diligence, (int)(ToIdleIsSin.WIDTH*0.54), (int)(ToIdleIsSin.HEIGHT*0.294), diligence.getWidth(), diligence.getHeight());
        diligenceBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.54), (int)(ToIdleIsSin.HEIGHT*0.294), diligence.getWidth(), diligence.getHeight());

        game.batch.draw(humility, (int)(ToIdleIsSin.WIDTH*0.105), (int)(ToIdleIsSin.HEIGHT*0.212), humility.getWidth(), humility.getHeight());
        humilityBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.105), (int)(ToIdleIsSin.HEIGHT*0.212), humility.getWidth(), humility.getHeight());

        game.batch.draw(kindness, (int)(ToIdleIsSin.WIDTH*0.395), (int)(ToIdleIsSin.HEIGHT*0.212), kindness.getWidth(), kindness.getHeight());
        kindnessBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.395), (int)(ToIdleIsSin.HEIGHT*0.212), kindness.getWidth(), kindness.getHeight());

        game.batch.draw(patience, (int)(ToIdleIsSin.WIDTH*0.68), (int)(ToIdleIsSin.HEIGHT*0.212), patience.getWidth(), patience.getHeight());
        patienceBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.68), (int)(ToIdleIsSin.HEIGHT*0.212), patience.getWidth(), patience.getHeight());

        game.batch.draw(temperance, (int)(ToIdleIsSin.WIDTH*0.54), (int)(ToIdleIsSin.HEIGHT*0.128), temperance.getWidth(), temperance.getHeight());
        temperanceBounds = new Rectangle((int)(ToIdleIsSin.WIDTH*0.54), (int)(ToIdleIsSin.HEIGHT*0.128), temperance.getWidth(), temperance.getHeight());
        
        game.batch.end();
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
                    ToIdleIsSin.program.run("village");

                } else if(buttonCampaignsBounds.contains(touch.x, touch.y)){
                    game.setScreen(new LevelsScreen(game));
                    ToIdleIsSin.program.run("battles");

                } else if(buttonStoryBounds.contains(touch.x, touch.y)){
                    game.setScreen(new StoryScreen(game));
                    ToIdleIsSin.program.run("visual novel");
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
        charity.dispose();
        chastity.dispose();
        diligence.dispose();
        humility.dispose();
        kindness.dispose();
        patience.dispose();
        temperance.dispose();
    }
}
