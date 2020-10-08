package com.wearethreestudios.toidleissin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.uihelpers.ImageBlob;

public class StoryScreen extends ScreenAdapter {
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

    private Texture door;
    private Texture doorNormal;
    private Texture doorClick;
    private Rectangle doorBounds;

    private ImageBlob walkingMan;

    public StoryScreen(ToIdleIsSin game) {

        walkingMan = new ImageBlob();
        walkingMan.addState("sprite-animation4.png", 5, 6, 0.025f, 1, 1);
        walkingMan.addState("sprite-animation4.png", 5, 6, 0.025f);

        this.game = game;
        background = new Texture("story/cottage.png");

        doorNormal = new Texture("story/door.png");
        doorClick = new Texture("story/door-click.png");
        door = doorNormal;

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
        
        // Levels
        game.batch.draw(door, (int)(ToIdleIsSin.WIDTH*0.74), (int)(ToIdleIsSin.HEIGHT*0.49), door.getWidth(), door.getHeight());
        doorBounds = new Rectangle( (int)(ToIdleIsSin.WIDTH*0.74), (int)(ToIdleIsSin.HEIGHT*0.49), door.getWidth(), door.getHeight() );

        game.batch.draw(walkingMan.getTextureRegion(), 50, 50);

        game.batch.end();
        walkingMan.returnToState(0);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                gamePort.unproject(touch.set(screenX, screenY, 0));
                if(doorBounds.contains(touch.x, touch.y)){
                    door = doorClick;
                } else if(!doorBounds.contains(touch.x, touch.y)){
                    door = doorNormal;
                }
                return super.mouseMoved(screenX, screenY);
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                gamePort.unproject(touch.set(screenX, screenY, 0));
                walkingMan.switchState(1);
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
        doorNormal.dispose();
        doorClick.dispose();
        door.dispose();
        walkingMan.dispose();
    }
}
