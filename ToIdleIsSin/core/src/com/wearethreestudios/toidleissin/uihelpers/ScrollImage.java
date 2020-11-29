package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Program;

public class ScrollImage {
    private Group scroll;

    public ScrollImage(ToIdleIsSin game, String location, int howmany){
        scroll = new Group();
        for(int i = 0; i < howmany; i++){
            Image a = new Image(game.skin, location + i);
            scroll.addActor(a);
            a.setPosition(scroll.getWidth(), 0);
            scroll.setWidth(scroll.getWidth()+ a.getWidth());
        }
        scroll.setVisible(false);
    }

    public Group get(){
        return scroll;
    }
}
