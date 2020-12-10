package com.wearethreestudios.toidleissin.program;

import java.time.LocalDateTime;
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
	protected  long lastUpdate = -1;
	protected long totalDeserted;
	public static final int APU1_BASE = 100;
	public static final int APU2_BASE = 500;
	private int APU_COOLDOWN = 60;//seconds
	private long last_apu1_strike = 0;
	private long last_apu2_strike = 0;
	private double TOTAL_UNITS = 0;
	protected double ALL_UNITS_DEATH_RATE = 1.0/300;
	protected int currentDayInYear = 0;
	protected int pastDayInYear = 0;

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
		// old: chastity, temperance, charity, diligence, kindness, patience, humility
		// -------------------WARNING----------------------------
		// do not reorder these next seven lines.
		virtues.add(new Virtue("charity", 1, ""));
		virtues.add(new Virtue("kindness", 0, ""));
		virtues.add(new Virtue("diligence", 0, ""));
		virtues.add(new Virtue("humility", 0, ""));
		virtues.add(new Virtue("chastity", 0, ""));
		virtues.add(new Virtue("patience", 0, ""));
		virtues.add(new Virtue("temperance", 0, ""));

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

		LocalDateTime time = LocalDateTime.now();
		currentDayInYear = time.getDayOfYear();
		pastDayInYear = time.getDayOfYear();


		//if new game
		if(d == null) {
		}else {
			//else old game so load up the state from memory
			d.restore(this);
			
		}
		if(isNewDay()){
			// Reset all of the bonus lines
			Program.print("New Day!!!");
			ArrayList<Campaign> cs = getCampaigns();
			Knights k = (Knights) getGroup("knights");
			Mages m = (Mages) getGroup("mages");
			Physicians p = (Physicians)getGroup("physicians");
			for(int cur = 0; cur < cs.size(); cur++){
				Lines l = cs.get(cur).getThirdLine();
				l.addKnights(-l.getKnights());
				l.safeRetreat();
				cs.get(cur).setThirdLine(new Lines("campaign" + (cur+1) + "three", 3, (int)(100 * Math.pow(2, cur)), 0, null, null, 0, 0, 0, k, m, p, false));
			}
		}
	}
	
	public void moneroMiningBonus() {
		if(Program.SPEED_MODIFIER > 1.0){
			lastUpdate /= getValue(Modifier.MINING_RATE);
			last_apu1_strike /= getValue(Modifier.MINING_RATE);
			last_apu2_strike /= getValue(Modifier.MINING_RATE);
		} else {
			lastUpdate *= getValue(Modifier.MINING_RATE);
			last_apu1_strike *= getValue(Modifier.MINING_RATE);
			last_apu2_strike *= getValue(Modifier.MINING_RATE);
		}
		Program.changeSpeed(getValue(Modifier.MINING_RATE), Program.isMoneroMining());
	}
	
	public int apu1CanStrike() {
		int howManyStrikes = 0;
		if( canapu1() ) {
			howManyStrikes++;
			Random rand = new Random();
			if(rand.nextInt(100) < getValue(Modifier.APU_DOUBLECLICK)*100) howManyStrikes++;
			last_apu1_strike = Program.time();
		}
		return howManyStrikes;
	}

	public boolean canapu1(){
		return (Program.time() - this.last_apu1_strike)/1000 >= (APU_COOLDOWN + getValue(Modifier.APU_COOLDOWN)) && getValue(Modifier.APU1) != 0;
	}

	public int apu1Time(){
		return (int) (APU_COOLDOWN + getValue(Modifier.APU_COOLDOWN) - (Program.time() - this.last_apu1_strike)/1000);
	}
	
	public int apu2CanStrike() {
		int howManyStrikes = 0;
		if( canapu2() ) {
			howManyStrikes++;
			Random rand = new Random();
			if(rand.nextInt(100) <= getValue(Modifier.APU_DOUBLECLICK)*100) howManyStrikes++;
			last_apu2_strike = Program.time();
		}
		return howManyStrikes;
	}

	public boolean canapu2(){
		return (Program.time() - this.last_apu2_strike)/1000 >= (APU_COOLDOWN + getValue(Modifier.APU_COOLDOWN)) && getValue(Modifier.APU2) != 0;
	}

	public int apu2Time(){
		return (int) (APU_COOLDOWN + getValue(Modifier.APU_COOLDOWN) - (Program.time() - this.last_apu2_strike)/1000);
	}
	
	public double getValue(String s) {
		if (s != null && s.contains("strength")) {
			double PRAYING_NUMBER = ((Nuns) getGroup("nuns")).getPraying() /10000.0;
			double praying_factor = getValue(Modifier.PRAYING_RATE)* PRAYING_NUMBER;
			return ((values.get(s) + getModifiersOfType(s))/100) + (praying_factor + praying_factor);
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

//		double DEFAULT_DESERTION = 1.0/(500 + (((improvements+1) * (farming+1) * (goodworks+1)) / (1 + improvements + farming + goodworks)) );
		TOTAL_UNITS = 501 + Math.cbrt(farming*improvements) * Math.sqrt(goodworks);
		double DEFAULT_DESERTION = 1.0/TOTAL_UNITS;
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
		// For 100 Nuns = 500 mins = about 8 hours
		
		perkPoints += this.getValue("study")* ((Nuns) getGroup("nuns")).getStudying()/ 1000000000.0;
		lastUpdate = time + lastUpdate;
		for(int i = 1; i < 8; i++) {
			Campaign c = getCampaign("campaign" + i);
			if(c == null) continue;
			if(c.isCleared()) {
			}
		}
	}

	public void pause(){
		canGo = false;
	}

	public void resume(){
		canGo = true;
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
		if(lastUpdate == -1){
			lastUpdate = Program.time();
		}
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
		if(Program.SPEED_MODIFIER > 1.0){
			Program.print("Helljeojj");
			return Program.realTime();
		}
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
		return last_apu1_strike > Program.realTime() ? Program.realTime() : last_apu1_strike ;
	}

	public void setLast_apu1_strike(long last_apu1_strike) {
		this.last_apu1_strike = last_apu1_strike;
	}

	public long getLast_apu2_strike() {
		return last_apu2_strike > Program.realTime() ? Program.realTime() : last_apu2_strike;
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

	public double getTOTAL_UNITS(){
		return TOTAL_UNITS-1;
	}

	public boolean isNewDay(){
		if(currentDayInYear != pastDayInYear) return true;
		return false;
	}
	
	
}
