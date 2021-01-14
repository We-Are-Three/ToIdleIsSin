package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.wearethreestudios.toidleissin.ToIdleIsSin;

public class DialoguePopUp {
    private Group popup;

    private Label who;
    private Label words;

    public DialoguePopUp(final ToIdleIsSin game){
        int width = (int)(ToIdleIsSin.WIDTH*2/3.0);
        int height = ToIdleIsSin.HEIGHT/6;
        popup = new Group();
        Image a = new Image(game.skin, "ui/scroll-horizontal");
        a.setSize(width, height);
        popup.addActor(a);
        a.setPosition(0,0);

        who = new Label("", game.skin, "alpha");
        who.setAlignment(Align.top);
        who.setSize(width/3-30, height -40);
        who.setWrap(true);
        popup.addActor(who);
        who.setPosition(15, 20);

        words = new Label("", game.skin, "alpha");
        words.setAlignment(Align.topLeft);
        words.setSize(2*width/3-250, height -40);
        words.setWrap(true);
        popup.addActor(words);
        words.setPosition(width/3 + 15, 20);

        popup.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                super.clicked(event, x, y);
            }
        });

    }

    public DialoguePopUp(final ToIdleIsSin game, int width, int height){
        popup = new Group();
        popup.setDebug(true, true);
        Image a = new Image(game.skin, "ui/scroll-horizontal");
        a.setSize(width, height);
        popup.addActor(a);
        a.setPosition(0,0);

        who = new Label("", game.skin, "alpha");
        who.setAlignment(Align.top);
        who.setSize(width/3-185, height -80);
        who.setWrap(true);
        popup.addActor(who);
        who.setPosition(165, 20);

        words = new Label("", game.skin, "alpha");
        words.setAlignment(Align.topLeft);
        words.setSize(2*width/3-175, height -80);
        words.setWrap(true);
        popup.addActor(words);
        words.setPosition(width/3, 20);

        popup.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                super.clicked(event, x, y);
            }
        });

    }

    public void setWho(String w){
        who.setText(w);
    }

    public void setWords(String w){
        words.setText(w);
    }

    public Group getPopup() {
        return popup;
    }

    public void update(){
    }

}
