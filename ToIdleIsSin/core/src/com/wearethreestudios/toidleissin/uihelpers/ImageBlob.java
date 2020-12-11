package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Program;

import java.util.ArrayList;
import java.util.Random;

//https://stackoverflow.com/questions/16059578/libgdx-is-there-an-actor-that-is-animated
public class ImageBlob extends Image {
    private int currentState = 0;
    private ArrayList<Animation<TextureRegion>> states;
    private ArrayList<TextureRegion> textures;
    private ArrayList<Animation<TextureRegion>> flips;
    private ArrayList<Float> stateTimes;
    private ToIdleIsSin game;
    private Rectangle hitbox;
    private double percentOfHitbox;
    private boolean flip;
    private Random random;
    private float scalerX = 1.0f;
    private float scalerY = 1.0f;
    private float scalerHitX = 0f;
    private float scalerHitY = 0f;

    public ImageBlob(ToIdleIsSin s, double percentOfHitbox){
        super(s.atlas.findRegion("alpha"));
        states = new ArrayList<>();
        textures = new ArrayList<>();
        flips = new ArrayList<>();
        stateTimes = new ArrayList<>();
        game = s;
        flip = false;
        this.percentOfHitbox = percentOfHitbox;
        random = new Random();
    }


    @Override
    public void act(float delta)
    {
        ((TextureRegionDrawable)getDrawable()).setRegion(getTextureRegion());
        setWidth(states.get(currentState).getKeyFrame(stateTimes.get(currentState)).getRegionWidth() *scalerX);
        setHeight(states.get(currentState).getKeyFrame(stateTimes.get(currentState)).getRegionHeight() * scalerY);

        int hitx = (int)( getX() + getWidth()*scalerHitX + (getWidth()/2)*(1-percentOfHitbox));
        int hity = (int)( getY() + getHeight()*scalerHitY + (getHeight()/2)*(1-percentOfHitbox));
        int hitWidth = (int)( getWidth() - (getWidth()/2)*(1-percentOfHitbox)*2);
        int hitHeight = (int)( getHeight() - (getHeight()/2)*(1-percentOfHitbox)*2);
        if(hitbox == null){
            hitbox = new Rectangle(hitx, hity, hitWidth, hitHeight);
        } else if(hitbox.x != hitx || hitbox.y != hity || hitbox.width != hitWidth || hitbox.height != hitHeight){
            hitbox.setX(hitx).setY(hity).setWidth(hitWidth).setHeight(hitHeight);
        }

        super.act(delta);
    }

    public void setBlobScale(float x, float y){
        scalerX = x;
        scalerY = y;
    }

    public void setHitOffset(float x, float y){
        scalerHitX = x;
        scalerHitY = y;
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && this.getTouchable() != Touchable.enabled) return null;
        if (!isVisible()) return null;
        return (x >= (getWidth()/2)*(1-percentOfHitbox) + getWidth()*scalerHitX &&
                x < (this.getWidth() - (getWidth()/2)*(1-percentOfHitbox)*2) + getWidth()*scalerHitX &&
                y >= (getHeight()/2)*(1-percentOfHitbox) + getWidth()*scalerHitY &&
                y < (this.getHeight() - (getHeight()/2)*(1-percentOfHitbox)*2) + getWidth()*scalerHitY )
                ? this : null;
    }

    public ImageBlob addState(String texture_name, int rows, int cols, float animation_time){
        textures.add(game.atlas.findRegion(texture_name));
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = textures.get(textures.size()-1).split(
                textures.get(textures.size()-1).getRegionWidth() / cols,
                textures.get(textures.size()-1).getRegionHeight() / rows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        states.add(new Animation<>(animation_time, walkFrames));
        states.get(states.size()-1).setPlayMode(Animation.PlayMode.LOOP);
        stateTimes.add(0f);


        TextureRegion[][] flipregion = textures.get(textures.size()-1).split(
                textures.get(textures.size()-1).getRegionWidth() / cols,
                textures.get(textures.size()-1).getRegionHeight() / rows);
        for(TextureRegion[] t : flipregion){
            for(TextureRegion l : t){
                l.flip(true, false);
            }
        }
        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] flipframes = new TextureRegion[cols * rows];
        index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipframes[index++] = flipregion[i][j];
            }
        }
        flips.add(new Animation<>(animation_time, flipframes));
        flips.get(flips.size()-1).setPlayMode(Animation.PlayMode.LOOP);
        stateTimes.add(0f);


        return this;
    }

    public ImageBlob addState(String texture_name, int rows, int cols, float animation_time, int lowerbound, int upperbound){
        textures.add(game.atlas.findRegion(texture_name));
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = textures.get(textures.size()-1).split(
                textures.get(textures.size()-1).getRegionWidth() / cols,
                textures.get(textures.size()-1).getRegionHeight() / rows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[upperbound - lowerbound + 1];
        int count = 0;
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                count++;
                if( count >= lowerbound && count <= upperbound)
                    walkFrames[index++] = tmp[i][j];
            }
        }
        states.add(new Animation<>(animation_time, walkFrames));
        states.get(states.size()-1).setPlayMode(Animation.PlayMode.LOOP);
        stateTimes.add(0f);


        TextureRegion[][] flipregion = textures.get(textures.size()-1).split(
                textures.get(textures.size()-1).getRegionWidth() / cols,
                textures.get(textures.size()-1).getRegionHeight() / rows);
        for(TextureRegion[] t : flipregion){
            for(TextureRegion l : t){
                l.flip(true, false);
            }
        }
        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] flipframes = new TextureRegion[upperbound - lowerbound + 1];
        count = 0;
        index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                count++;
                if( count >= lowerbound && count <= upperbound)
                    flipframes[index++] = flipregion[i][j];
            }
        }
        flips.add(new Animation<>(animation_time, flipframes));
        flips.get(flips.size()-1).setPlayMode(Animation.PlayMode.LOOP);
        stateTimes.add(0f);
        return this;
    }

    public void draw(int x, int y, int width, int height){
        int hitx = (int)( x + (width/2)*(1-percentOfHitbox));
        int hity = (int)( y + (height/2)*(1-percentOfHitbox));
        int hitWidth = (int)( width - (width/2)*(1-percentOfHitbox)*2);
        int hitHeight = (int)( height - (height/2)*(1-percentOfHitbox)*2);
        if(hitbox == null){
            hitbox = new Rectangle(hitx, hity, hitWidth, hitHeight);
        } else if(hitbox.x != hitx || hitbox.y != hity || hitbox.width != hitWidth || hitbox.height != hitHeight){
            hitbox.setX(hitx).setY(hity).setWidth(hitWidth).setHeight(hitHeight);
        }
//        Program.print("\n1: " + x + " " + y + " " + width  + " " + height +
//                      "\n2: " + hitbox.x + " " + hitbox.y + " " + hitbox.width  + " " + hitbox.height);
        game.batch.draw(getTextureRegion(),  x, y,  width, height);
    }

    public void draw(int x, int y, double widthPercentage, double heightPercentage){
        int width = (int) (getTextureRegion().getRegionWidth()*widthPercentage);
        int height = (int) (getTextureRegion().getRegionHeight()*heightPercentage);
        int hitx = (int)( x + (width/2)*(1-percentOfHitbox));
        int hity = (int)( y + (height/2)*(1-percentOfHitbox));
        int hitWidth = (int)( width - (width/2)*(1-percentOfHitbox)*2);
        int hitHeight = (int)( height - (height/2)*(1-percentOfHitbox)*2);
        if(hitbox == null){
            hitbox = new Rectangle(hitx, hity, hitWidth, hitHeight);
        } else if(hitbox.x != hitx || hitbox.y != hity || hitbox.width != hitWidth || hitbox.height != hitHeight){
            hitbox.setX(hitx).setY(hity).setWidth(hitWidth).setHeight(hitHeight);
        }
//        Program.print("\n1: " + x + " " + y + " " + width  + " " + height +
//                      "\n2: " + hitbox.x + " " + hitbox.y + " " + hitbox.width  + " " + hitbox.height);
        game.batch.draw(getTextureRegion(),  x, y,  width, height);
    }

    public void flip(){
        flip = !flip;
        Program.print("I have been called " + flip);
    }

    public boolean contains(float x, float y){
        return hitbox.contains(x, y);
    }

    public TextureRegion getTextureRegion(){
        stateTimes.set(currentState, stateTimes.get(currentState) + Gdx.graphics.getDeltaTime());
        return flip ? flips.get(currentState).getKeyFrame(stateTimes.get(currentState)) : states.get(currentState).getKeyFrame(stateTimes.get(currentState));
    }

    public void switchState(int state){
        if(currentState == state) return;
        stateTimes.set(currentState, 0f);
        currentState = state;
        stateTimes.set(currentState, 0f);
    }

    public boolean returnToState(int state){
        if (currentState == state) return true;
        if(currentState == state && states.get(currentState).isAnimationFinished(stateTimes.get(currentState))){
            return true;
        }
        if(states.get(currentState).isAnimationFinished(stateTimes.get(currentState))){
            switchState(state);
            return true;
        }
        return false;
    }

    public void random(){
        int choice = 0;
        if(states.size() > 1){
            int rand = random.nextInt(1000);
            choice = rand < 980 ? 0 : (int) ((rand-980)/(20f/(states.size()-1)) + 1);
        }
        returnToState(choice);
    }

    public void dispose(){
    }
}
