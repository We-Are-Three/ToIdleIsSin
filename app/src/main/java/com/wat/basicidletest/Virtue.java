package com.wat.basicidletest;

public class Virtue {
	private String name;
	private int progress;
	private String dialoguePath;
	public Virtue(String name, int progress, String dialoguePath) {
		this.name = name;
		this.progress = progress;
		this.dialoguePath = dialoguePath;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public String getName() {
		return name;
	}
	public String getDialoguePath() {
		return dialoguePath;
	}
	
	
}
