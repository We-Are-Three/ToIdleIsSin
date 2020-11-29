package com.wearethreestudios.toidleissin.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wearethreestudios.toidleissin.ToIdleIsSin;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ToIdleIsSin.TITLE + " v" + ToIdleIsSin.VERSION;
		config.width = ToIdleIsSin.WIDTH;
		config.height = ToIdleIsSin.HEIGHT;
		config.title = ToIdleIsSin.TITLE;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
//		config.resizable = false;
		new LwjglApplication(new ToIdleIsSin(), config)
		{
//			@Override
//			public void exit() {
//				if(((ToIdleIsSin)getApplicationListener()).canClose());
//				super.exit();
//			}
		};
	}
}
