package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.screens.LevelsScreen;
import com.wearethreestudios.toidleissin.screens.StoryScreen;
import com.wearethreestudios.toidleissin.screens.VillageScreen;

public class NavButtons {
    public static TextButton getVillage(final ToIdleIsSin game){
        TextButton village = new TextButton("", game.skin, "village");
        village.setPosition((int)(ToIdleIsSin.WIDTH*0.1), (int)(ToIdleIsSin.HEIGHT*0.01));
        village.setSize(200, 200);
        village.setOrigin(Align.center);
        village.setTransform(true);
        village.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                game.setScreen(new VillageScreen(game));
                ToIdleIsSin.program.run("village");
                super.clicked(event, x, y);
            }
        });
        return village;
    }

    public static TextButton getCampaign(final ToIdleIsSin game){
        TextButton campaign = new TextButton("", game.skin, "sword");
        campaign.setPosition((int)(ToIdleIsSin.WIDTH*0.4), (int)(ToIdleIsSin.HEIGHT*0.01));
        campaign.setSize(200, 200);
        campaign.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                game.setScreen(new LevelsScreen(game));
                ToIdleIsSin.program.run("battles");
                super.clicked(event, x, y);
            }
        });
        return campaign;
    }

    public static TextButton getStory(final ToIdleIsSin game){
        TextButton story = new TextButton("", game.skin, "scroll");
        story.setPosition((int)(ToIdleIsSin.WIDTH*0.7), (int)(ToIdleIsSin.HEIGHT*0.01));
        story.setSize(200, 200);
        story.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                game.setScreen(new StoryScreen(game));
                ToIdleIsSin.program.run("visual novel");
                super.clicked(event, x, y);
            }
        });
        return story;
    }
}
