package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Perk;
import com.wearethreestudios.toidleissin.program.Program;

import java.util.ArrayList;

public class Hints {
    private Table popup;

    private Image image;
    private Label name;

    private Image imtext;
    private Label text;

    private Label temp;

    public Hints(ToIdleIsSin game, int w, int h, String title, String message, String picture){
        popup = new Table(game.skin);
        popup.setTouchable(Touchable.enabled);
        int height = h;
        int width = w;

//        popup.debugAll();

        popup.row();
        name = new Label(title, game.skin, "hint");
        name.setAlignment(Align.center);
        popup.add(name).pad(25);
        height -=50;
        height -= name.getHeight();

        popup.row();
        image = new Image(game.skin, picture);
        image.setSize(100,50);
        popup.add(image);
        height -= image.getHeight();

        popup.row();
        Group messagegroup = new Group();
        messagegroup.setSize(width-40,400);

        imtext = new Image(game.skin, "ui/scroll-horizontal");
        messagegroup.addActor(imtext);
        imtext.setSize(messagegroup.getWidth(), messagegroup.getHeight());

        text = new Label(message, game.skin, "alpha");
        messagegroup.addActor(text);
        text.setSize(imtext.getWidth()-240,imtext.getHeight()-40);
        text.setWrap(true);
        text.setPosition(110,30);
        popup.add(messagegroup).width(messagegroup.getWidth());
        height -= messagegroup.getHeight();

        popup.row();
        temp = new Label("", game.skin, "alpha");
        popup.add(temp).height(height).width(width);
        popup.pack();
        popup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popup.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        popup.setVisible(false);
    }

    public Table getPopup() {
        return popup;
    }

    public void update(){
    }
}
