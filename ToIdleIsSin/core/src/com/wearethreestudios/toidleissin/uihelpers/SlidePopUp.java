package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Perk;
import com.wearethreestudios.toidleissin.program.Program;

import java.util.ArrayList;

public class SlidePopUp {
    private Table popup;

    private Label name;

    private Label idle;
    private Label job;
    private Slider slider;
    private TextButton submit;

    private Label temp;

    private float value;
    private int idleP;
    private int workingP;

    public SlidePopUp(ToIdleIsSin game, int w, int h, String title, int idlePeople, int workingPeople, final String command){
        value = 0;
        idleP = idlePeople;
        workingP = workingPeople;
        popup = new Table(game.skin);
        popup.setTouchable(Touchable.enabled);
        int height = h;
        int width = w;

        popup.setDebug(true);
        popup.debugAll();

        popup.row();
        name = new Label(title, game.skin, "hint");
        name.setAlignment(Align.center);
        popup.add(name).pad(25).colspan(3);
        height -=50;
        height -= name.getHeight();

        popup.row();
        idle = new Label("Idle\n" + idlePeople, game.skin, "hint");
        idle.setAlignment(Align.center);
        popup.add(idle);
        job = new Label("Working\n" + workingPeople, game.skin, "hint");
        job.setAlignment(Align.center);
        popup.add(job);
        popup.add().width(idle.getWidth());
        height -= idle.getHeight();

        popup.row();
        slider = new Slider(-workingPeople, idlePeople, 1, false, game.skin);
//        slider.setSize(w, 300);
//        slider.getStyle().knob.setMinHeight(50);
        slider.getStyle().knob.setMinWidth(50);
//        slider
//        slider.getStyle().background.setMinWidth(w);
        popup.add(slider).colspan(2).fillX().pad(25);
        slider.setWidth(slider.getWidth());
        submit = new TextButton("Submit", game.skin,  "idle");
        submit.setSize(200,200);
        popup.add(submit).colspan(1).fillX().pad(25);
        height -= (slider.getHeight() + 50);

        popup.row();
        temp = new Label("", game.skin, "alpha");
        popup.add(temp).height(height).width(width).colspan(3);
        popup.pack();
        slider.setValue(0f);

        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                value =  slider.getValue();
                idle.setText("Idle\n" + (int)(idleP - value) );
                job.setText("Working\n" + (int)(workingP + value));
            }
        });

        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Program.run(command);
                Program.run(String.valueOf((int)value));
                remove();
                super.clicked(event, x, y);
            }
        });
        TextureRegionDrawable d = new TextureRegionDrawable(game.atlas.findRegion("village/cathedral/perk"));
        popup.background(d);
    }

    public Table getPopup() {
        return popup;
    }

    public void remove(){
        popup.setVisible(false);
        popup.remove();
        Program.gameState.resume();
    }

    public boolean hit(float x, float y){
        return x >= popup.getX() && x <= popup.getX()+popup.getWidth() && y >= popup.getY() && y <= popup.getY()+popup.getHeight();
    }
}
