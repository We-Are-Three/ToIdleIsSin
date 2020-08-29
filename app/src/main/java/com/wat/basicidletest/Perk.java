package com.wat.basicidletest;

import java.util.ArrayList;

public class Perk {
	private String name;
	private String finerName;
	private int currentLevel;
	int tier;
	private int MAXLEVEL;
	private String description;
	private ArrayList<Modifier> modifiers;
	public Perk(String name, String f, int tier, int currentLevel, int maxLevel, Modifier... ms) {
		this.name = name;
		finerName = f;
		this.tier = tier;
		this.currentLevel = currentLevel;
		this.MAXLEVEL = maxLevel;
		modifiers = new ArrayList<>();
		description = "";
		for(Modifier m : ms) {
			modifiers.add(m);
		}
	}
	
	public static void setUpPerks(ArrayList<Perk> perks) {
		perks.add(new Perk("one", "chastityperks", 1, 0, 10, 
				new Modifier("+0.2% study effeciency ", Modifier.STUDYING_RATE, 0.2)));
		perks.add(new Perk("two", "chastityperks", 2, 0, 12, 
				new Modifier("+0.1% apu strength ", Modifier.APU_STRENGTH, 0.1)));
		perks.add(new Perk("three", "chastityperks", 3, 0, 2, 
				new Modifier("+5% recruit effeciency ", Modifier.RECRUIT_RATE, 5)));
		perks.add(new Perk("four", "chastityperks", 3, 0, 10, 
				new Modifier("+1% knight power ", Modifier.KNIGHT_STRENGTH, 1)));

		perks.add(new Perk("one", "temperanceperks", 1, 0, 10, 
				new Modifier("+1% knight defense ", Modifier.DEFENSE_STRENGTH, 1)));
		perks.add(new Perk("two", "temperanceperks", 2, 0, 8, 
				new Modifier("+25% hero knight bonus ", Modifier.HERO_STRENGTH, 25)));
		perks.add(new Perk("three", "temperanceperks", 3, 0, 10, 
				new Modifier("-3 second cooldown for apu ", Modifier.APU_COOLDOWN, -300)));
		
		perks.add(new Perk("one", "charityperks", 1, 0, 1,
				new Modifier("apu auto click 60 second cooldown ", Modifier.APU1, 100)));
		perks.add(new Perk("two", "charityperks", 2, 0, 10,
				new Modifier("-2 second cooldown for apu ", Modifier.APU_COOLDOWN, -200)));
		perks.add(new Perk("three", "charityperks", 3, 0, 10,
				new Modifier("1% chance double apu auto click ", Modifier.APU_DOUBLECLICK, 1)));
		perks.add(new Perk("three", "charityperks", 3, 0, 2,
				new Modifier("5% apu auto click x5 power chance ", Modifier.APU_STRENGTH, 5)));
		
		perks.add(new Perk("one", "diligenceperks", 1, 0, 5,
				new Modifier("+2% Hero Knight conversion exp ", Modifier.HERO_RATE, 2)));
		perks.add(new Perk("two", "diligenceperks", 1, 0, 5,
				new Modifier("+1.5% damage to bosses ", Modifier.AGAINST_BOSS_STRENGTH, 1.5)));
		perks.add(new Perk("three", "diligenceperks", 2, 0, 15,
				new Modifier("+0.1% daily bonus increase rate ", Modifier.DAILY_STRENGTH, 0.1)));
		perks.add(new Perk("four", "diligenceperks", 3, 0, 1,
				new Modifier("Daily bonus starts at and never falls below 10% filled ", Modifier.DAILY_START, 100)));
		

		perks.add(new Perk("one", "kindnessperks", 1, 0, 10,
				new Modifier("+0.5% faster recruitment ", Modifier.RECRUIT_RATE, 0.5)));
		perks.add(new Perk("two", "kindnessperks", 2, 0, 10,
				new Modifier("+0.1% daily bonus negative growth ", Modifier.DEATH_RATE, 0.1)));
		perks.add(new Perk("three", "kindnessperks", 2, 0, 5,
				new Modifier("-1% desertion chance ", Modifier.DESERTION_RATE, 1)));
		perks.add(new Perk("four", "kindnessperks", 3, 0, 1,
				new Modifier("active power up ", Modifier.APU2, 100)));
		
		
		perks.add(new Perk("one", "patienceperks", 1, 0, 50,
				new Modifier("+0.5% physician healing factor ", Modifier.PHYSICIAN_STRENGTH, 0.5)));
		

		perks.add(new Perk("one", "humilityperks", 1, 0, 10,
				new Modifier("+0.2% mining bonus ", Modifier.MINING_RATE, 0.2)));
		perks.add(new Perk("two", "humilityperks", 2, 0, 10,
				new Modifier("+0.5% mage strength ", Modifier.MAGE_STRENGTH, 5)));
		perks.add(new Perk("three", "humilityperks", 3, 0, 12,
				new Modifier("+1% training effeciency ", Modifier.TRAIN, 1)));
		
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	public void increaseLevel() {
		currentLevel++;
	}
	public String getModifierName() {
		StringBuilder s = new StringBuilder();
		for(Modifier m : modifiers) {
			s.append(m.getName() + " ");
		}
		return s.toString();
		
	}
	public String getName(int justName) { 
		if(finerName == null || justName == 1) {
			return name;
		}
		if(justName == 2) return finerName;
		return "(" + name + ")" + " " + finerName;
	}
	
	public String getPrettyName() {
		StringBuilder s = new StringBuilder();
		for(Modifier m : modifiers) {
			s.append(" " + tier +"*x study points to get " + m.getName() + "[" + currentLevel + "/" + MAXLEVEL + "]");
		}
		return s.toString();
	}
	public int getMAXLEVEL() {
		return MAXLEVEL;
	}
	public ArrayList<Modifier> getModifiers() {
		ArrayList<Modifier> ms = new ArrayList<>();
		for(Modifier m : modifiers) {
			ms.add(m.copy());
		}
		return ms;
	}
	
	public int getCost() {
		return tier;
	}
	
}
