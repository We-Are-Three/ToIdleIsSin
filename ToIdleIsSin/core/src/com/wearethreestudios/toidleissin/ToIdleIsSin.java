package com.wearethreestudios.toidleissin;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wearethreestudios.toidleissin.program.Program;
import com.wearethreestudios.toidleissin.screens.MenuScreen;

public class ToIdleIsSin extends Game  implements ApplicationListener {
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 1920;

	public static final String TITLE = "To Idle Is Sin";
	public SpriteBatch batch;
	public static Program program;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		program = new Program();
		program.mainlibgdx();
		Preferences prefs = Gdx.app.getPreferences("ToIdleIsSin.save");
		if(prefs.contains("savegame")){
			ToIdleIsSin.program.run("restoregame");
		}else{
			ToIdleIsSin.program.run("start game");
		}
		setScreen(new MenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	// Be sure to implement the logic that is in DesktopLauncher.java into AndroidLauncher.java too.
	// https://stackoverflow.com/questions/45537887/libgdx-prevent-app-from-closing-desktop
	// save file is in User/.prefs/file
	public boolean canClose()
	{
		program.saveGame();
		return true; //return true if you want the close to happen
	}

}
