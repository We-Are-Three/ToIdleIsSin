package com.wearethreestudios.toidleissin.uihelpers;

import com.badlogic.gdx.Gdx;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wearethreestudios.toidleissin.program.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DialogueManager {
    private HashMap<String, JsonObject> characters;
    private Random rand;
    public DialogueManager(){
        characters = new HashMap<>();
        queueDialogue();
        rand = new Random();
    }

    public void queueDialogue(){
        String helloworld = Gdx.files.internal("dialogue/charity.json").readString();
        JsonObject hellojson = new JsonParser().parse(helloworld).getAsJsonObject();
        hellojson.getAsJsonArray("main").get(0);
        for(int i = 0 ; i < hellojson.getAsJsonArray("main").size(); i++){
            JsonElement j = hellojson.getAsJsonArray("main").get(i);
        }
        characters.put("charity", hellojson);

        helloworld = Gdx.files.internal("dialogue/chastity.json").readString();
        hellojson = new JsonParser().parse(helloworld).getAsJsonObject();
        hellojson.getAsJsonArray("main").get(0);
        for(int i = 0 ; i < hellojson.getAsJsonArray("main").size(); i++){
            JsonElement j = hellojson.getAsJsonArray("main").get(i);
        }
        characters.put("chastity", hellojson);

        helloworld = Gdx.files.internal("dialogue/diligence.json").readString();
        hellojson = new JsonParser().parse(helloworld).getAsJsonObject();
        hellojson.getAsJsonArray("main").get(0);
        for(int i = 0 ; i < hellojson.getAsJsonArray("main").size(); i++){
            JsonElement j = hellojson.getAsJsonArray("main").get(i);
        }
        characters.put("diligence", hellojson);

        helloworld = Gdx.files.internal("dialogue/humility.json").readString();
        hellojson = new JsonParser().parse(helloworld).getAsJsonObject();
        hellojson.getAsJsonArray("main").get(0);
        for(int i = 0 ; i < hellojson.getAsJsonArray("main").size(); i++){
            JsonElement j = hellojson.getAsJsonArray("main").get(i);
        }
        characters.put("humility", hellojson);

        helloworld = Gdx.files.internal("dialogue/kindness.json").readString();
        hellojson = new JsonParser().parse(helloworld).getAsJsonObject();
        hellojson.getAsJsonArray("main").get(0);
        for(int i = 0 ; i < hellojson.getAsJsonArray("main").size(); i++){
            JsonElement j = hellojson.getAsJsonArray("main").get(i);
        }
        characters.put("kindness", hellojson);

        helloworld = Gdx.files.internal("dialogue/temperance.json").readString();
        hellojson = new JsonParser().parse(helloworld).getAsJsonObject();
        hellojson.getAsJsonArray("main").get(0);
        for(int i = 0 ; i < hellojson.getAsJsonArray("main").size(); i++){
            JsonElement j = hellojson.getAsJsonArray("main").get(i);
        }
        characters.put("temperance", hellojson);

        helloworld = Gdx.files.internal("dialogue/patienceman.json").readString();
        hellojson = new JsonParser().parse(helloworld).getAsJsonObject();
        hellojson.getAsJsonArray("main").get(0);
        for(int i = 0 ; i < hellojson.getAsJsonArray("main").size(); i++){
            JsonElement j = hellojson.getAsJsonArray("main").get(i);
        }
        characters.put("patience", hellojson);
    }

    public Dialogue characterMainDialogue(String character){
        if(!characters.containsKey(character)) return null;
        JsonArray block = characters.get(character).getAsJsonArray("main");
        for(int pos = 0; pos < block.size(); pos++){
            JsonObject path = block.get(pos).getAsJsonObject();
            if(path.get("progress").getAsInt() == Program.gameState.getVirtue(character).getProgress()){
                return new Dialogue(path);
            }
        }
        return null;
    }

    public Dialogue characterMainDialogue(String character, String event){
        if(!characters.containsKey(character)) return null;
        JsonArray block = characters.get(character).getAsJsonArray("main");
        for(int pos = 0; pos < block.size(); pos++){
            JsonObject path = block.get(pos).getAsJsonObject();
            if(event.equals(path.get("title").getAsString()) ){
                return new Dialogue(path);
            }
        }
        return null;
    }

    public Dialogue characterRandom(String character){
        if(!characters.containsKey(character)) return null;
        JsonArray block = characters.get(character).getAsJsonArray("random");
        ArrayList<Integer> possible = new ArrayList<>();
        for(int pos = 0; pos < block.size(); pos++){
            JsonObject path = block.get(pos).getAsJsonObject();
            if(path.get("progress").getAsInt() <= Program.gameState.getVirtue(character).getProgress()){
                possible.add(pos);
            }
        }
        int chosen = possible.get(rand.nextInt(possible.size()));
        return new Dialogue(block.get(chosen).getAsJsonObject());
    }
}
