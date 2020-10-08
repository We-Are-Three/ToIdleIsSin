package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class ImageBlob {
    private int currentState = 0;
    private ArrayList<Animation<TextureRegion>> states;
    private ArrayList<Texture> textures;
    private ArrayList<Float> stateTimes;

    public ImageBlob(){
        states = new ArrayList<>();
        textures = new ArrayList<>();
        stateTimes = new ArrayList<>();
    }

    public void addState(String texture_name, int rows, int cols, float animation_time){
        textures.add(new Texture(texture_name));
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(textures.get(textures.size()-1),
                textures.get(textures.size()-1).getWidth() / cols,
                textures.get(textures.size()-1).getHeight() / rows);

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
        stateTimes.add(0f);
    }

    public void addState(String texture_name, int rows, int cols, float animation_time, int lowerbound, int upperbound){
        textures.add(new Texture(texture_name));
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(textures.get(textures.size()-1),
                textures.get(textures.size()-1).getWidth() / cols,
                textures.get(textures.size()-1).getHeight() / rows);

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
        stateTimes.add(0f);
    }

    public TextureRegion getTextureRegion(){
        stateTimes.set(currentState, stateTimes.get(currentState) + Gdx.graphics.getDeltaTime());
        return states.get(currentState).getKeyFrame(stateTimes.get(currentState));
    }

    public void switchState(int state){
        stateTimes.set(currentState, 0f);
        currentState = state;
        stateTimes.set(currentState, 0f);
    }

    public boolean returnToState(int state){
        if(states.get(currentState).isAnimationFinished(stateTimes.get(currentState))){
            switchState(state);
            return true;
        }
        return false;
    }

    public void dispose(){
        for(Texture t : textures){
            t.dispose();
        }
    }
}
