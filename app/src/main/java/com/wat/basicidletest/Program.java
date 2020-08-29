package com.wat.basicidletest;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

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
	public static TextView text;
	public static SharedPreferences mPrefs;
	
	public static void mainl(TextView te, SharedPreferences p) {
		text = te;
		mPrefs = p;
		//read in previous save state
		Thread s1;
		try {
			if(!ANDROID_EDITION){
				s1 = new Thread(new ThreadedSend());
				s1.start();
			}
			
			commands = new Commands();
			endGame = false;
			//display current options
			print("Type the name of the command to select it\n" + commands.getOptions(0, null));
			gameState = new GameState(database);
			lastUpdate = time();
			//await for input
//			if(!ANDROID_EDITION){
				do {
				}while(!runGame(c));
//			}
		}finally {
			if(!ANDROID_EDITION){
				s1.interrupt();
			}
		}
	}

	public static boolean runGame(String command) {
		long time = time() - lastUpdate;
		if(time < FRAME_TIME) return endGame;
		lastUpdate = time();
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
		return endGame;
	}
	
	private static String filename = "C:/tmp/";
	public static boolean saveGame() {
		Gson gson = new Gson();
		Database t = new Database(gameState);
		String saveme = gson.toJson(t);

		if(ANDROID_EDITION) {
			//use logcat code
//			print(saveme);
			SharedPreferences.Editor prefsEditor = mPrefs.edit();
			prefsEditor.putString("savegame", saveme);
			print("commit: " + prefsEditor.commit());
			print("does it contain: " + mPrefs.contains("savegame"));
		}else {
			
			File f = new File("C:/tmp");
			if(!f.mkdir()) {
				print("failed making folder.");
				return false;
			}
			try (Writer teamwriter = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("C:/tmp/teams.txt"), "utf-8"))) {
				
				teamwriter.write(saveme);
				teamwriter.close();
				return true;
			}
			catch (Exception e) {
				e.printStackTrace();
				print("could not save file");
				return false;
			}
		}
		return false;
	}
	
	public static boolean loadGame() {

		if(ANDROID_EDITION) {
			//use logcat code
			Gson gson = new Gson();
			String fileAsString = mPrefs.getString("savegame", "");
//			print(fileAsString);
			Database buba = gson.fromJson(fileAsString, Database.class);
			gameState = new GameState(buba);
			gameState.start();

			return true;

		}else {
			try {
				InputStream is = new FileInputStream("C:/tmp/teams.txt");
				BufferedReader buf = new BufferedReader(new InputStreamReader(is));
				        
				String line = buf.readLine();
				StringBuilder sb = new StringBuilder();
				        
				while(line != null){
				   sb.append(line).append("\n");
				   line = buf.readLine();
				}
				        
				String fileAsString = sb.toString();
				System.out.println("Contents : " + fileAsString);
				Gson gson = new Gson();
				Database buba = gson.fromJson(fileAsString, Database.class);
				gameState = new GameState(buba);
				gameState.start();
				
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				print("could not load file");
				return false;
			}
		}
	}
	
	
	
	public static boolean startMoneroMining() {
		try {
			// TODO: code to start monero mining
			IS_MONERO_MINING = true;
		}catch(Exception e) {
			Program.print("Cannnot begin Monero Mining because: " + e.toString());
			IS_MONERO_MINING = false;
		}
		return IS_MONERO_MINING;
	}
	
	public static boolean stopMoneroMining() {
		try {
			// TODO: code to stop monero mining
			IS_MONERO_MINING = false;
		}catch(Exception e) {
			Program.print("Cannnot stop Monero Mining because: " + e.toString());
			IS_MONERO_MINING = true;
		}
		return !IS_MONERO_MINING;
	}
	
	
	public static boolean isMoneroMining() {
		//eventually will check if monero is actually mining if so then
		return true;
	}
	
	public static void changeSpeed(double val, boolean reset) {
		if(reset) {
			SPEED_MODIFIER = 1.0;
			return;
		}
		SPEED_MODIFIER *= val;
	}
	
	public static long time() {
		return (long) (System.currentTimeMillis() * SPEED_MODIFIER);
	}
	
	public static long realTime() {
		return System.currentTimeMillis();
	}
	
	public static void print(String s) {
		if(ANDROID_EDITION) {
			//use logcat code\
			MainActivity.addString(s, text);
			Log.d(TAG, "loadGame: \n" + s);
		}else {
			System.out.println(s);
		}
	}
	
	public static class ThreadedSend implements Runnable {
		Program p;
	    public String command;
	    public ThreadedSend() {
	    }

	    public void run() {
	        Scanner sc = new Scanner(System.in);
	        while(sc.hasNextLine()) {
	            // blocks for input, but won't block the main thread
	        	command = sc.nextLine();
	        	c = command;
	        	
	        }
	    }

	    
	}
}
