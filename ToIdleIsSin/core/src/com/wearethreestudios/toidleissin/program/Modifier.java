package com.wearethreestudios.toidleissin.program;

public class Modifier {
	private String name;
	private String command;
	private double amount;
	public Modifier(String name, String command, double amount) {
		this.name = name;
		this.command = command;
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public String getCommand() {
		return command;
	}
	public double getAmount() {
		return amount;
	}
	
	protected Modifier copy() {
		Modifier clone = new Modifier(name, command, amount);
		return clone;
	}
	
	

	// "study"  "apustrength"  "recruitboth"  "knightstrength"  "defense"  "herostrength"  "apucooldown"
	// "apu1"   "apucooldown"   "apudoubleclick"  "apustrengthmultiplier"    "heroconversion"  "bossdamage"
	//  "thirdlinestrength"  "thirdlinestart"  "recruit"  "killrate"  "desertionrate"  "apu2"
	//  "mining"  "magestrength"  "trainkeep"
	
	//used for monks, nuns, and knights
	public static final String RECRUIT_RATE = "recruit";
	
	// monk to mage
	public static final String MAGE_RATE = "monktraining";
	
	// nun to physician
	public static final String PHYSICIAN_RATE = "nuntraining";
	
	// from village improvements, farming, and goodworks
	public static final String DESERTION_RATE = "desertion";
	
	// from perk to lower death rate among soldiers
	public static final String DEATH_RATE = "death";

	//chance that heroes are developed
	public static final String HERO_RATE = "herotraining";
	
	//from monk mining bonus
	public static final String MINING_RATE = "mining";
	
	//from nun studying
	public static final String STUDYING_RATE = "study";
	
	//from nun praying for all daily bonus line strength
	public static final String PRAYING_RATE = "praying";
	
	
	public static final String KNIGHT_STRENGTH = "knightstrength";
	public static final String MAGE_STRENGTH = "magestrength";
	public static final String PHYSICIAN_STRENGTH = "physicianstrength";
	public static final String HERO_STRENGTH = "herostrength";
	public static final String APU_STRENGTH = "apustrength";
	public static final String DEFENSE_STRENGTH = "defensestrength";
	public static final String AGAINST_BOSS_STRENGTH = "againstbossstrength";
	public static final String DAILY_STRENGTH = "thirdlinestrength";
	
	//small modifiers
	public static final String APU_COOLDOWN = "apucooldown";
	public static final String APU_DOUBLECLICK = "apudoubleclick";
	public static final String TRAIN = "keeptrainedunits";
	
	//one offs
	public static final String APU1 = "apu1";
	public static final String APU2 = "apu2";
	public static final String DAILY_START = "dailystart";
	
	
}
