package com.wat.basicidletest;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
// https://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object

public class GameState {
	private ArrayList<Groups> groups;
	private ArrayList<Virtue> virtues;
	private ArrayList<Perk> perks;
	private ArrayList<Modifier> activeModifiers;
	private ArrayList<Campaign> campaigns;
	private String activeScreen = "";
	protected Hashtable<String, Double> values;
	private boolean canGo;
	private double perkPoints;
	protected  long lastUpdate;
	protected long totalDeserted;
	public static final int APU1_BASE = 100;
	public static final int APU2_BASE = 500;
	private int APU_COOLDOWN = 60;//seconds
	private long last_apu1_strike = 0;
	private long last_apu2_strike = 0;
	
	@SuppressWarnings("unused")
	public GameState(Database d) {
		groups = new ArrayList<>();
		virtues = new ArrayList<>();
		perks = new ArrayList<>();
		activeModifiers = new ArrayList<>();
		values = new Hashtable<>();
		canGo = false;

		totalDeserted = 0;
		perkPoints = 1;
		//add the groups
		groups.add(new Monks("monks", 500, 0.0001, 0, 0, 0, 0));
		groups.add(new Nuns("nuns", 500, 0.0001, 0, 0, 0, 0 ));
		groups.add(new Knights("knights", 500, 0.00003, 0, 0));
		groups.add(new Physicians("physicians", 500, 0, 0, 0, 0, (Nuns)getGroup("nuns")));
		groups.add(new Mages("mages", 500, 0, 0, 0, 0, (Monks)getGroup("monks")));
		
		// add the virtues
		virtues.add(new Virtue("chastity", 0, ""));
		virtues.add(new Virtue("temperance", 0, ""));
		virtues.add(new Virtue("charity", 1, ""));
		virtues.add(new Virtue("diligence", 0, ""));
		virtues.add(new Virtue("patience", 0, ""));
		virtues.add(new Virtue("kindness", 0, ""));
		virtues.add(new Virtue("humility", 0, ""));

		//add the Perks
		Perk.setUpPerks(perks);
		
		//set up Campaigns
		campaigns = Campaign.constructCampaigns(this);

		values.put(Modifier.RECRUIT_RATE, 100.d);//
		values.put(Modifier.MAGE_RATE, 100.d);//
		values.put(Modifier.PHYSICIAN_RATE, 100.d);//
		values.put(Modifier.DESERTION_RATE, 6.d);//
		values.put(Modifier.DEATH_RATE, 100.d);//
		values.put(Modifier.HERO_RATE, 100.d);//
		values.put(Modifier.MINING_RATE, 200.d);//two times speed
		values.put(Modifier.STUDYING_RATE, 100.d);//
		values.put(Modifier.PRAYING_RATE, 100.d);//
		
		values.put(Modifier.KNIGHT_STRENGTH, 100.d);//
		values.put(Modifier.MAGE_STRENGTH, 100.d);//
		values.put(Modifier.PHYSICIAN_STRENGTH, 100.d);//
		values.put(Modifier.HERO_STRENGTH, 100.d);//
		values.put(Modifier.APU_STRENGTH, 100.d);//
		values.put(Modifier.DEFENSE_STRENGTH, 100.d);//
		
		values.put(Modifier.AGAINST_BOSS_STRENGTH, 100.d);//
		values.put(Modifier.DAILY_STRENGTH, 100.d);//
		
		values.put(Modifier.APU_COOLDOWN, 0.d);//
		values.put(Modifier.APU_DOUBLECLICK, 0.d);//
		values.put(Modifier.TRAIN, 100.d);//
		
		values.put(Modifier.APU1, 0.d);//
		values.put(Modifier.APU2, 0.d);//
		values.put(Modifier.DAILY_START, 0.d);//
		
		//if new game
		if(d == null) {
		}else {
			//else old game so load up the state from memory
			d.restore(this);
			
		}
	}
	
	public void moneroMiningBonus() {
		Program.changeSpeed(getValue(Modifier.MINING_RATE), Program.isMoneroMining());
	}
	
	public int apu1CanStrike() {
		int howManyStrikes = 0;
		if( (Program.time() - this.last_apu1_strike)/1000 >= (APU_COOLDOWN + getValue(Modifier.APU_COOLDOWN)) ) {
			howManyStrikes++;
			Random rand = new Random();
			if(rand.nextInt(100) < getValue(Modifier.APU_DOUBLECLICK)*100) howManyStrikes++;
			last_apu1_strike = Program.time();
		}
		return howManyStrikes;
	}
	
	public int apu2CanStrike() {
		int howManyStrikes = 0;
		if( (Program.time() - this.last_apu2_strike)/1000 >= (APU_COOLDOWN + getValue(Modifier.APU_COOLDOWN)) ) {
			howManyStrikes++;
			Random rand = new Random();
			if(rand.nextInt(100) <= getValue(Modifier.APU_DOUBLECLICK)*100) howManyStrikes++;
			last_apu2_strike = Program.time();
		}
		return howManyStrikes;
	}
	
	public double getValue(String s) {
		if (s != null && s.contains("strength")) {
			double PRAYING_NUMBER = ((Nuns) getGroup("nuns")).getPraying() /10000.0;
			double praying_factor = getValue(Modifier.PRAYING_RATE)* PRAYING_NUMBER;
			return ((values.get(s) + getModifiersOfType(s))/100) + praying_factor;
		}
		else if( s != null && s.contains("recruit")) {
			double RECRUITING_FACTOR = ((Monks) getGroup("monks")).getRecruiting()/ 100.0;
			return ((values.get(s) + getModifiersOfType(s))/100) + RECRUITING_FACTOR;
		}
		return (values.get(s) + getModifiersOfType(s))/100;
	}
	
	public void updateState() {
		if(!canGo) return;
		Random rand = new Random();
		long improvements = ((Monks)getGroup("monks")).getImprovements();
		long farming = ((Nuns)getGroup("nuns")).getFarming();
		long goodworks = ((Knights)getGroup("knights")).getGoodworks();
		double DEFAULT_DESERTION = 1.0/(500 + (((improvements+1) * (farming+1) * (goodworks+1)) / (1 + improvements + farming + goodworks)) );
		long time = Program.time() - lastUpdate;
		//groups updated
		for(Groups g : groups) {
			g.update(this, time);
			if(rand.nextInt(100) < getValue(Modifier.DESERTION_RATE)*100) {
				int deserts =(int)( g.getIdle() * DEFAULT_DESERTION);
//				if(deserts >= 1) Program.print(deserts + " " + g.getName() + " deserted");
				g.remove(deserts);
				
			}
		}
		for(Campaign c : campaigns) {
			c.update(this, time);
		}
		
		//perform additional updates based off of groups stats + modifiers
		
		perkPoints += this.getValue("study")* ((Nuns) getGroup("nuns")).getStudying()/ 10000.0;
		lastUpdate = time + lastUpdate;
		for(int i = 1; i < 8; i++) {
			Campaign c = getCampaign("campaign" + i);
			if(c == null) continue;
			if(c.isCleared()) {
			}
		}
	}
	
	public double getModifiersOfType(String command) {
		double result = 0;
		for(Modifier m : activeModifiers) {
			if(m.getCommand().contentEquals(command)) {
				result = result + m.getAmount();
			}
		}
		return result;
	}
	
	public void start() {
		for(Groups g : groups) {
			g.start();
		}
		canGo = true;
		lastUpdate = Program.time();
	}
	
	public Groups getGroup(String name) {
		for(Groups g : groups) {
			if(name.equals(g.getName())) {
				return g;
			}
		}
		return null;
	}
	
	public Perk getPerk(String name) {
		for(Perk p : perks) {
			if(name.equals(p.getName(0))) {
				return p;
			}
		}
		return null;
		
	}
	
	public Campaign getCampaign(String name) {
		if(name == null) return null;
		for(Campaign c : campaigns) {
			if(name.contentEquals(c.getName())) return c;
		}
		return null;
	}
	
	public boolean isCampaignCleared(String name) {
		Campaign c = getCampaign(name);
		if(c == null) return false;
		return c.isCleared();
	}
	
	public Lines getLine(String name) {
		if(name == null) return null;
		for(Campaign c : campaigns) {
			if(name.contentEquals(c.getFirstLine().getName())) {
				return c.getFirstLine();
			}
			if(name.contentEquals(c.getSecondLine().getName())) {
				return c.getSecondLine();
			}
			if(name.contentEquals(c.getThirdLine().getName())) {
				return c.getThirdLine();
			}
		}
		return null;
	}
	
	public String getStats(String name) {
		for(Groups g : groups) {
			if(name.equals(g.getName())) {
				return g.stats();
			}
		}
		for(Campaign c : campaigns) {
			if(name.contentEquals(c.getName())) {
				return c.stats();
			}
			if(name.contentEquals(c.getFirstLine().getName())) {
				return c.getFirstLine().stats();
			}
			if(name.contentEquals(c.getSecondLine().getName())) {
				return c.getSecondLine().stats();
			}
			if(name.contentEquals(c.getThirdLine().getName())) {
				return c.getThirdLine().stats();
			}
		}
		return "No Stats Available for " + name;
	}
	
	public double getPerkPoint() {
		return perkPoints;
	}
	public void addPerkPoints(int x) {
		perkPoints += x;
	}
	
	public boolean addModifiers(Perk p) {
		if(p == null) return false;
		if(getPerkPoint() >= p.getCost() && p.getCurrentLevel() < p.getMAXLEVEL()) {
			addPerkPoints(-p.getCost());
			ArrayList<Modifier> ms = p.getModifiers();
			for(Modifier m : ms) {
				activeModifiers.add(m);
			}
			p.increaseLevel();
			return true;
		}
		return false;
	}
	
	public void setActiveScreen(String s) {
		activeScreen = s;
	}
	
	public Virtue getVirtue(String name) {
		if(name == null) return null;
		for(Virtue v : virtues) {
			if(name.contentEquals(v.getName())) return v;
		}
		return null;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public long getTotalDeserted() {
		return totalDeserted;
	}

	public void setTotalDeserted(long totalDeserted) {
		this.totalDeserted = totalDeserted;
	}

	public long getLast_apu1_strike() {
		return last_apu1_strike;
	}

	public void setLast_apu1_strike(long last_apu1_strike) {
		this.last_apu1_strike = last_apu1_strike;
	}

	public long getLast_apu2_strike() {
		return last_apu2_strike;
	}

	public void setLast_apu2_strike(long last_apu2_strike) {
		this.last_apu2_strike = last_apu2_strike;
	}

	public void setPerkPoints(double perkPoints) {
		this.perkPoints = perkPoints;
	}

	public ArrayList<Virtue> getVirtues() {
		return virtues;
	}

	public ArrayList<Perk> getPerks() {
		return perks;
	}

	public ArrayList<Campaign> getCampaigns() {
		return campaigns;
	}
	
	
}
