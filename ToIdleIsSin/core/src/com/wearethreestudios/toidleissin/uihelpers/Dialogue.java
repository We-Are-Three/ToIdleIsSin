package com.wearethreestudios.toidleissin.uihelpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Dialogue {
    JsonObject block;
    JsonArray conversation;
    JsonObject line;
    int current;

    public Dialogue(JsonObject block){
        this.block = block;
        conversation = block.getAsJsonArray("dialogue");
        current = -1;
    }

    public String getCharacter(){
        return line.get("character").getAsString();
    }

    public String getMessage(){
        return line.get("text").getAsString();
    }

    public boolean next(){
        if(!hasNext()) return false;
        current++;
        line = conversation.get(current).getAsJsonObject();
        return true;
    }

    public boolean hasNext(){
        return (current + 1) < conversation.size();
    }

}
