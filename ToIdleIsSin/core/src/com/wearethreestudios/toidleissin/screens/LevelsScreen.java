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

public class LevelsScreen extends ScreenAdapter {
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

    private Texture l1;
    private Rectangle l1Bounds;
    private Texture l2;
    private Rectangle l2Bounds;
    private Texture l3;
    private Rectangle l3Bounds;
    private Texture l4;
    private Rectangle l4Bounds;
    private Texture l5;
    private Rectangle l5Bounds;
    private Texture l6;
    private Rectangle l6Bounds;
    private Texture l7;
    private Rectangle l7Bounds;

    public LevelsScreen(ToIdleIsSin game) {
        this.game = game;
        background = new Texture("campaign/levels.png");
        
        l1 = new Texture("campaign/lvl1.png");
        l2 = new Texture("campaign/lvl2.png");
        l3 = new Texture("campaign/lvl3.png");
        l4 = new Texture("campaign/lvl4.png");
        l5 = new Texture("campaign/lvl5.png");
        l6 = new Texture("campaign/lvl6.png");
        l7 = new Texture("campaign/lvl7.png");

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
        buttonVillageBounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.05), buttonVillage.getWidth(), buttonVillage.getHeight() );

        game.batch.draw(buttonCampaigns, (int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.05), buttonCampaigns.getWidth(), buttonCampaigns.getHeight());
        buttonCampaignsBounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.05), buttonCampaigns.getWidth(), buttonCampaigns.getHeight() );

        game.batch.draw(buttonStory, (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.05), buttonStory.getWidth(), buttonStory.getHeight());
        buttonStoryBounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.05), buttonStory.getWidth(), buttonStory.getHeight() );

        // Levels
        game.batch.draw(l1, (int)(ToIdleIsSin.WIDTH*0.106), (int)(ToIdleIsSin.HEIGHT*0.372), l1.getWidth(), l1.getHeight());
        l1Bounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.106), (int)(ToIdleIsSin.HEIGHT*0.372), l1.getWidth(), l1.getHeight() );

        game.batch.draw(l2, (int)(ToIdleIsSin.WIDTH*0.26), (int)(ToIdleIsSin.HEIGHT*0.405), l2.getWidth(), l2.getHeight());
        l2Bounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.26), (int)(ToIdleIsSin.HEIGHT*0.405), l2.getWidth(), l2.getHeight() );

        game.batch.draw(l3, (int)(ToIdleIsSin.WIDTH*0.51), (int)(ToIdleIsSin.HEIGHT*0.466), l3.getWidth(), l3.getHeight());
        l3Bounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.51), (int)(ToIdleIsSin.HEIGHT*0.466), l3.getWidth(), l3.getHeight() );

        game.batch.draw(l4, (int)(ToIdleIsSin.WIDTH*0.655), (int)(ToIdleIsSin.HEIGHT*0.538), l4.getWidth(), l4.getHeight());
        l4Bounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.655), (int)(ToIdleIsSin.HEIGHT*0.538), l4.getWidth(), l4.getHeight() );

        game.batch.draw(l5, (int)(ToIdleIsSin.WIDTH*0.283), (int)(ToIdleIsSin.HEIGHT*0.562), l5.getWidth(), l5.getHeight());
        l5Bounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.283), (int)(ToIdleIsSin.HEIGHT*0.562), l5.getWidth(), l5.getHeight() );

        game.batch.draw(l6, (int)(ToIdleIsSin.WIDTH*0.245), (int)(ToIdleIsSin.HEIGHT*0.678), l6.getWidth(), l6.getHeight());
        l6Bounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.245), (int)(ToIdleIsSin.HEIGHT*0.678), l6.getWidth(), l6.getHeight() );

        game.batch.draw(l7, (int)(ToIdleIsSin.WIDTH*0.353), (int)(ToIdleIsSin.HEIGHT*0.788), l7.getWidth(), l7.getHeight());
        l7Bounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.353), (int)(ToIdleIsSin.HEIGHT*0.788), l7.getWidth(), l7.getHeight() );

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
                    game.setScreen(new MenuScreen(game));
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
        l1.dispose();
        l2.dispose();
        l3.dispose();
        l4.dispose();
        l5.dispose();
        l6.dispose();
        l7.dispose();
    }
}
