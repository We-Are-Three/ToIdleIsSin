package com.wearethreestudios.toidleissin.program;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;

import monero.android.miner.Miner;

public class Program {
	private static final String TAG = "Program";
	public static final double FPS = 30;
	public static final double SECONDS_IN_MILLIS = 1000;
	public static double FRAME_TIME = SECONDS_IN_MILLIS / FPS;
	private static long lastUpdate;
	public static GameState gameState;
	public static boolean endGame;
	public static Commands commands;
	public static String c = "";
	public static final boolean ANDROID_EDITION = true;
	public static double SPEED_MODIFIER = 1.0;
	private static boolean IS_MONERO_MINING = false;
	public static Database database = null;
	public static Miner miner;

	public static void setNextCommand(String command){
		c = command;
	}

	public static void runNextCommand(){
		while(!runGame(c));
	}

	public static void run(String command){
		setNextCommand(command);
		runNextCommand();
	}

	public static void mainlibgdx(){
		switch (Gdx.app.getType()){
			case Android:
				miner = new Miner();
				break;
			case Desktop:
				// No desktop miner yet
				break;
			default:
				// nothing should happen
				break;
		}
		commands = new Commands();
		endGame = false;

		//display current options
		print("Type the name of the command to select it\n" + commands.getOptions(0, null));
		gameState = new GameState(database);
		lastUpdate = time();
		runGame(c);
	}

	public static boolean runGame(String command) {
		long time = time() - lastUpdate;
//		if(time < FRAME_TIME) return endGame;
		if(time < FRAME_TIME) return false;
		lastUpdate = realTime();
		gameState.updateState();
		if(command.equals("options")) {
			print("--------------------------------------------");

			print("\nhi" + commands.getOptions(0, null));
		}else if(!command.isEmpty() && !command.equals("end")) {
			print("--------------------------------------------" + command+"\n");
			if(commands.execute(command, gameState)) {
				print("\n" + commands.getOptions(0, null));
			}
		}else if(command.contentEquals("end")){
			endGame = true;
			saveGame();
		}
		c = "";
		return true;
//		return endGame;
	}

	public static boolean saveGame() {
		Gson gson = new Gson();
		Database t = new Database(gameState);
		String saveme = gson.toJson(t);
		Preferences prefs = Gdx.app.getPreferences("ToIdleIsSin.save");
		prefs.putString("savegame", saveme);
		prefs.flush();
		return false;
	}
	
	public static boolean loadGame() {
			Gson gson = new Gson();
			Preferences prefs = Gdx.app.getPreferences("ToIdleIsSin.save");
			String fileAsString = prefs.getString("savegame", "error");
			Database buba = gson.fromJson(fileAsString, Database.class);
			gameState = new GameState(buba);
			gameState.start();
			return true;
	}
	
	
	
//	public static boolean startMoneroMining() {
//		print("randomx start mining");
//		try {
//			// TODO: code to start monero mining
//			IS_MONERO_MINING = true;
//		}catch(Exception e) {
//			Program.print("Cannnot begin Monero Mining because: " + e.toString());
//			IS_MONERO_MINING = false;
//		}
//		print("randomx current Speed " +  SPEED_MODIFIER);
//		lastUpdate = time();
//		return IS_MONERO_MINING;
//	}
//
//	public static boolean stopMoneroMining() {
//		print("randomx stop mining");
//		try {
//			// TODO: code to stop monero mining
//			IS_MONERO_MINING = false;
//		}catch(Exception e) {
//			Program.print("Cannnot stop Monero Mining because: " + e.toString());
//			IS_MONERO_MINING = true;
//		}
//		print("randomx current Speed " +  SPEED_MODIFIER);
//		lastUpdate = time();
//		return IS_MONERO_MINING;
//	}
//
//
//	public static boolean isMoneroMining() {
//		//eventually will check if monero is actually mining if so then
//		return IS_MONERO_MINING;
//	}
//
	public static void changeSpeed(double val, boolean reset) {
		if(reset || SPEED_MODIFIER > 1.0) {
			SPEED_MODIFIER = 1.0;
			return;
		}
		SPEED_MODIFIER *= val;
	}

	public static boolean startMoneroMining(
		final String host,
		final int port,
		final String address,
		final String worker) {
		// only used for GUI with systems that do not have mining
		// Otherwise, ignore this variable
		IS_MONERO_MINING = !IS_MONERO_MINING;
		if(miner == null){
			return true;
		}
		return miner.start(host, port, address, worker);
	}

	public static void stopMoneroMining() {
		// only used for GUI with systems that do not have mining
		// Otherwise, ignore this variable
		IS_MONERO_MINING = false;
		if(miner == null){
			return;
		}
		miner.stop();
		gameState.lastUpdate = realTime();
	}

	public static boolean isMoneroMining() {
		if(miner == null) {
			return false;
		}
		return miner.running();
	}

	public static boolean isMining(){
		return IS_MONERO_MINING;
	}

	public static double hash(){
		return miner.hashrate();
	}
	
	public static long time() {
		return (long) (realTime() * SPEED_MODIFIER);
	}
	
	public static long realTime() {
		return System.currentTimeMillis();
	}
	
	public static void print(String s) {
		if(ANDROID_EDITION) {
			//use logcat code\
			//MainActivity.addString(s, text);
			Gdx.app.log("loadGame: ", s);
			//Log.d(TAG, "loadGame: \n" + s);
		}else {
			System.out.println(s);
		}
	}
}
