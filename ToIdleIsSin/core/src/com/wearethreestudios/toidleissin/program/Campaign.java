package com.wearethreestudios.toidleissin.program;

import java.util.ArrayList;

public class Campaign {
	private String name;
	private ArrayList<Modifier> rewards;
	private Lines firstLine;
	private Lines secondLine;
	private Lines thirdLine;
	
	public Campaign(String name, ArrayList<Modifier> rewards, Lines firstLine, Lines secondLine, Lines thridLine) {
		super();
		this.name = name;
		this.rewards = rewards;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.thirdLine = thridLine;
	}
//	public Lines(String name, int wHICH_LINE, int numberOfEnemies, double enemiesKilled,
//			ArrayList<Modifier> negativeModifiers, ArrayList<Modifier> rewardModifiers, int knights, int mages,
//			int physicians, Knights knight, Mages mage, Physicians physician, boolean cleared) {
	public static ArrayList<Campaign> constructCampaigns(GameState gs) {
		ArrayList<Campaign> campaigns = new ArrayList<>();
		Knights k = (Knights) gs.getGroup("knights");
		Mages m = (Mages) gs.getGroup("mages");
		Physicians p = (Physicians)gs.getGroup("physicians");
		campaigns.add(new Campaign("campaign1", null, 
				new Lines("campaign1one", 1, 1000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign1two", 2, 500, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign1three", 3, 100, 0, null, null, 0, 0, 0, k, m, p, false)));
		campaigns.add(new Campaign("campaign2", null,
				new Lines("campaign2one", 1, 2000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign2two", 2, 1000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign2three", 3, 200, 0, null, null, 0, 0, 0, k, m, p, false)));
		campaigns.add(new Campaign("campaign3", null,
				new Lines("campaign3one", 1, 4000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign3two", 2, 2000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign3three", 3, 400, 0, null, null, 0, 0, 0, k, m, p, false)));
		campaigns.add(new Campaign("campaign4", null,
				new Lines("campaign4one", 1, 8000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign4two", 2, 4000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign4three", 3, 800, 0, null, null, 0, 0, 0, k, m, p, false)));
		campaigns.add(new Campaign("campaign5", null,
				new Lines("campaign5one", 1, 16000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign5two", 2, 8000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign5three", 3, 1600, 0, null, null, 0, 0, 0, k, m, p, false)));
		campaigns.add(new Campaign("campaign6", null,
				new Lines("campaign6one", 1, 32000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign6two", 2, 16000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign6three", 3, 3200, 0, null, null, 0, 0, 0, k, m, p, false)));
		campaigns.add(new Campaign("campaign7", null,
				new Lines("campaign7one", 1, 64000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign7two", 2, 32000, 0, null, null, 0, 0, 0, k, m, p, false),
				new Lines("campaign7three", 3, 6400, 0, null, null, 0, 0, 0, k, m, p, false)));
		return campaigns;
	}
	
	public String stats() {
		StringBuilder s = new StringBuilder();
		if(firstLine != null) s.append(firstLine.stats() + "\n");
		if(secondLine != null) s.append(secondLine.stats() + "\n");
		if(thirdLine != null) s.append(thirdLine.stats() + "\n");
		return s.toString();
	}
	
	public boolean update(GameState gs, long time) {
		firstLine.updateLine(gs, time);
		secondLine.updateLine(gs, time);
		thirdLine.updateLine(gs, time);
		return true;
	}
	
	public ArrayList<Modifier> getRewards() {
		return rewards;
	}
	public void setRewards(ArrayList<Modifier> rewards) {
		this.rewards = rewards;
	}
	public Lines getFirstLine() {
		return firstLine;
	}
	public void setFirstLine(Lines firstLine) {
		this.firstLine = firstLine;
	}
	public Lines getSecondLine() {
		return secondLine;
	}
	public void setSecondLine(Lines secondLine) {
		this.secondLine = secondLine;
	}
	public Lines getThirdLine() {
		return thirdLine;
	}
	public void setThirdLine(Lines thridLine) {
		this.thirdLine = thridLine;
	}
	public String getName() {
		return name;
	}
	
	public boolean isCleared() {
		if(firstLine != null) 
			if(!firstLine.isCleared()) return false;
		if(secondLine != null) 
			if(!secondLine.isCleared()) return false;
//		if(thirdLine != null)
//			if(!thirdLine.isCleared()) return false;
		return true;
	}
	
	
	
}
