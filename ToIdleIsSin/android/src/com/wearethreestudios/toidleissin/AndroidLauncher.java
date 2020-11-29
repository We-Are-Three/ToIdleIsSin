package com.wearethreestudios.toidleissin;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wearethreestudios.toidleissin.ToIdleIsSin;
import com.wearethreestudios.toidleissin.program.Program;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ToIdleIsSin()
//				   {
//			@Override
//			public void pause() {
//				Program.print("BYe Byeeee");
//				if(((ToIdleIsSin)getApplicationListener()).canClose());
//				super.pause();
//			}
//		}
		, config);
	}
}
