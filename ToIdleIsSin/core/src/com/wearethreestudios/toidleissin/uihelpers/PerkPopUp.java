package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Perk;
import com.wearethreestudios.toidleissin.program.Program;

import java.util.ArrayList;

public class PerkPopUp {
    private Table popup;
    private ArrayList<Perk> perks;

    private Label points;
    private Label name;
    private TextButton exit;

    private Label perk1;
    private TextButton perk1b;
    private Label perk1count;

    private Label perk2;
    private TextButton perk2b;
    private Label perk2count;

    private Label perk3;
    private TextButton perk3b;
    private Label perk3count;

    private Label perk4;
    private TextButton perk4b;
    private Label perk4count;
    private Label temp;

    public PerkPopUp(String virtue, final ToIdleIsSin game){
        perks = getPerks(virtue);
        popup = new Table(game.skin);
        popup.setTouchable(Touchable.enabled);
        int height = (int)(ToIdleIsSin.HEIGHT*0.75);
        int width = (int)(ToIdleIsSin.WIDTH*0.85);

        popup.setDebug(true);

        popup.row();
        name = new Label("" + virtue.toUpperCase(), game.skin, "perk");
        name.setAlignment(Align.center);
        popup.add(name).padBottom(50);
        points = new Label("Points: "+ (int)Program.gameState.getPerkPoint(), game.skin, "perk");
        points.setAlignment(Align.center);
        popup.add(points).center().padBottom(50);
        exit = new TextButton("X", game.skin, "perk");
        exit.getLabel().setAlignment(Align.center);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                game.sound.play();
                ToIdleIsSin.program.run("cathedral");
                popup.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        popup.add(exit).padBottom(50);
        height -= points.getHeight();
        height -=50;

        popup.row();
        perk1 = new Label(perks.get(0).getModifierName(), game.skin, "perk");
        perk1.setWrap(true);
        perk1.setAlignment(Align.center);
        popup.add(perk1).width((int)(width*0.5)).padBottom(25);
        perk1b = new TextButton("", game.skin, "perk");
        perk1b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!perk1b.isDisabled()){
                    game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                    game.sound.play();
                    ToIdleIsSin.program.run("one");
                }
                super.clicked(event, x, y);
            }
        });
        popup.add(perk1b).padBottom(25);
        perk1count = new Label(perks.get(0).getCurrentLevel() + "/" + perks.get(0).getMAXLEVEL(), game.skin, "perk");
        perk1count.setAlignment(Align.center);
        popup.add(perk1count).padBottom(25);
        height -=25;
        height -= perk1.getHeight();

        if(perks.size() >= 2) {
            popup.row();
            perk2 = new Label(perks.get(1).getModifierName(), game.skin, "perk");
            perk2.setWrap(true);
            perk2.setAlignment(Align.center);
            popup.add(perk2).width((int) (width * 0.5)).padBottom(25);
            perk2b = new TextButton("", game.skin, "perk");
            perk2b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!perk2b.isDisabled()){
                        game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                        game.sound.play();
                        ToIdleIsSin.program.run("two");
                    }
                    super.clicked(event, x, y);
                }
            });
            popup.add(perk2b).padBottom(25);
            perk2count = new Label(perks.get(1).getCurrentLevel() + "/" + perks.get(1).getMAXLEVEL(), game.skin, "perk");
            perk2count.setAlignment(Align.center);
            popup.add(perk2count).padBottom(25);
            height -= 25;
            height -= perk2.getHeight();
        }

        if(perks.size() >= 3) {
            popup.row();
            perk3 = new Label(perks.get(2).getModifierName(), game.skin, "perk");
            perk3.setWrap(true);
            perk3.setAlignment(Align.center);
            popup.add(perk3).width((int) (width * 0.5)).padBottom(25);
            perk3b = new TextButton("", game.skin, "perk");
            perk3b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!perk3b.isDisabled()){
                        game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                        game.sound.play();
                        ToIdleIsSin.program.run("three");
                    }
                    super.clicked(event, x, y);
                }
            });
            popup.add(perk3b).padBottom(25);
            perk3count = new Label(perks.get(2).getCurrentLevel() + "/" + perks.get(2).getMAXLEVEL(), game.skin, "perk");
            perk3count.setAlignment(Align.center);
            popup.add(perk3count).padBottom(25);
            height -= 25;
            height -= perk3.getHeight();
        }

        if(perks.size() >= 4){
            popup.row();
            perk4 = new Label(perks.get(3).getModifierName(), game.skin, "perk");
            perk4.setWrap(true);
            perk4.setAlignment(Align.center);
            popup.add(perk4).width((int)(width*0.5)).padBottom(25);
            perk4b = new TextButton("", game.skin, "perk");
            perk4b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!perk4b.isDisabled()){
                        game.sound = game.assets.get("sound/navigation.mp3", Sound.class);
                        game.sound.play();
                        ToIdleIsSin.program.run("four");
                    }
                    super.clicked(event, x, y);
                }
            });
            popup.add(perk4b).padBottom(25);
            perk4count = new Label(perks.get(3).getCurrentLevel() + "/" + perks.get(3).getMAXLEVEL(), game.skin, "perk");
            perk4count.setAlignment(Align.center);
            popup.add(perk4count).padBottom(25).fillY();
            height -=25;
            height -= perk4.getHeight();
        }
        popup.row();

        temp = new Label("", game.skin, "alpha");
        popup.add(temp).height(height).width(width).colspan(3);
    }

    private ArrayList<Perk> getPerks(String virtue){
        ArrayList<Perk> perks = new ArrayList<>();
        perks.add(Program.gameState.getPerk("(one) " + virtue + "perks"));
        Perk second = Program.gameState.getPerk("(two) " + virtue + "perks");
        if(second != null) perks.add(second);
        Perk third = Program.gameState.getPerk("(three) " + virtue + "perks");
        if(third != null) perks.add(third);
        Perk fourth = Program.gameState.getPerk("(four) " + virtue + "perks");
        if(fourth != null) perks.add(fourth);
        return perks;
    }

    public Table getPopup() {
        return popup;
    }

    public void update(){
        int perkPoints = (int)Program.gameState.getPerkPoint();
        points.setText("Points: "+ perkPoints + "\n" + (Program.gameState.perkTime() < 60 ? (Program.gameState.perkTime()+1) + " Sec" : (Program.gameState.perkTime()+1)/60 + " Min")  );
        perk1count.setText(perks.get(0).getCurrentLevel() + "/" + perks.get(0).getMAXLEVEL());
        perk1b.setText(perks.get(0).getTier() + " Points");
        if(perkPoints < perks.get(0).getTier()){
            perk1b.setDisabled(true);
        }else{
            perk1b.setDisabled(false);
        }
        if(perk2count != null){
            perk2count.setText(perks.get(1).getCurrentLevel() + "/" + perks.get(1).getMAXLEVEL());
            perk2b.setText(perks.get(1).getTier() + " Points");
            if(perkPoints < perks.get(1).getTier()){
                perk2b.setDisabled(true);
            }else{
                perk2b.setDisabled(false);
            }
        }
        if(perk3count != null) {
            perk3count.setText(perks.get(2).getCurrentLevel() + "/" + perks.get(2).getMAXLEVEL());
            perk3b.setText(perks.get(2).getTier() + " Points");
            if(perkPoints < perks.get(2).getTier()){
                perk3b.setDisabled(true);
            }else{
                perk3b.setDisabled(false);
            }
        }
        if(perk4count != null) {
            perk4count.setText(perks.get(3).getCurrentLevel() + "/" + perks.get(3).getMAXLEVEL());
            perk4b.setText(perks.get(3).getTier() + " Points");
            if(perkPoints < perks.get(3).getTier()){
                perk4b.setDisabled(true);
            }else{
                perk4b.setDisabled(false);
            }
        }
    }
}
