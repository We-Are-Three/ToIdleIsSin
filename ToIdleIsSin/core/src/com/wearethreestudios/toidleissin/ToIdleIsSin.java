package com.wearethreestudios.toidleissin;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.screens.MenuScreen;
import com.wearethreestudios.toidleissin.uihelpers.DialogueManager;

public class ToIdleIsSin extends Game  implements ApplicationListener {
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 1920;

	public static final String TITLE = "ToIdleIsSin";
	public static final float VERSION = 0.1f;
	public SpriteBatch batch;
	public static Program program;
	public AssetManager assets;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	public BitmapFont font;
	public Skin skin;
	public TextureAtlas atlas;
	public DialogueManager dialogue;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		program = new Program();
		assets = new AssetManager();
		initFonts();
		queueAssets();
		program.mainlibgdx();
		Preferences prefs = Gdx.app.getPreferences("ToIdleIsSin.save");
		if(prefs.contains("savegame")){
			ToIdleIsSin.program.run("restoregame");
		}else{
			ToIdleIsSin.program.run("start game");
		}
		dialogue = new DialogueManager();
		setScreen(new MenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
		generator.dispose();
		font.dispose();
		Program.print("Stop the mining");
		Program.stopMoneroMining();
	}

	@Override
	public void pause() {
		Program.print("BYe Byeeee");
		if(canClose());
		super.resume();
	}

	// Be sure to implement the logic that is in DesktopLauncher.java into AndroidLauncher.java too.
	// https://stackoverflow.com/questions/45537887/libgdx-prevent-app-from-closing-desktop
	// save file is in User/.prefs/file
	public boolean canClose()
	{
		program.saveGame();
		return true; //return true if you want the close to happen
	}

	public void queueAssets(){
		assets.load("atlas/atlassprites.atlas", TextureAtlas.class);
	}

	private void initFonts(){
		//https://developerover30.wordpress.com/2014/11/03/libgdx-drawing-text-using-truetype-fonts/
		//https://github.com/libgdx/libgdx/wiki/Gdx-freetype
		//https://libgdx.info/basic-label/
		//fonts
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BLKCHCRY.TTF"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
//        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
		font = generator.generateFont(parameter);
	}

	public void loadSkins(){
		skin = new Skin();
		this.skin.addRegions(assets.get("atlas/atlassprites.atlas", TextureAtlas.class));
		skin.add("default-font", font);
		skin.load(Gdx.files.internal("ui/uiskin.json"));
	}

}
