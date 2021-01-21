package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.audio.Sound;
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

    public SlidePopUp(final ToIdleIsSin game, int w, int h, String title, int idlePeople, int workingPeople, final String command){
        value = 0;
        idleP = idlePeople;
        workingP = workingPeople;
        popup = new Table(game.skin);
        popup.setTouchable(Touchable.enabled);

//        popup.setDebug(true);
//        popup.debugAll();

        popup.row();
        name = new Label(title, game.skin, "hint");
        name.setAlignment(Align.center);
        name.setWrap(true);
        popup.add(name).width(300).height(h/3-50).pad(25).colspan(3);

        popup.row();
        idle = new Label("Idle\n" + idlePeople, game.skin, "hint");
        idle.setAlignment(Align.center);
        popup.add(idle).height(h/3-50);
        job = new Label("Working\n" + workingPeople, game.skin, "hint");
        job.setAlignment(Align.center);
        popup.add(job).height(h/3-50);
        popup.add().width(idle.getWidth()).height(h/3-50).pad(25);

        popup.row();
        slider = new Slider(-workingPeople, idlePeople, 1, false, game.skin);
        slider.getStyle().knob.setMinWidth(100);
        popup.add(slider).colspan(2).height(h/3-50).fillX().pad(25);
        slider.setWidth(slider.getWidth());
        submit = new TextButton("Submit", game.skin,  "job");
        submit.setSize(200,200);
        popup.add(submit).colspan(1).height(h/3-50).pad(25);

        popup.pack();
        slider.setValue(0f);

        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                value =  slider.getValue();
                idle.setText("Idle\n" + (int)(idleP - value) );
                job.setText("Working\n" + (int)(workingP + value));
            }
        });

        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
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
