package com.wearethreestudios.toidleissin.program;

import java.util.ArrayList;

public class Lines {
	private String name;
	private int WHICH_LINE;
	private int numberOfEnemies;
	private double enemiesKilled;
	private ArrayList<Modifier> negativeModifiers;
	private ArrayList<Modifier> rewardModifiers;
	private int knights;
	private int mages;
	private int physicians;
	private Knights knight;
	private Mages mage;
	private Physicians physician;
	private boolean cleared;
	private int current_loop_iteration = 0;
	private double BOSS_STRENGTH_MULTIPLIER = 3;
	private double last_enemy_deaths = 0;
	private double last_unit_deaths = 0;
	private double knightsToBeKilled = 0;
	private long currentdelta = 0;
	
	public Lines(String name, int wHICH_LINE, int numberOfEnemies, double enemiesKilled,
			ArrayList<Modifier> negativeModifiers, ArrayList<Modifier> rewardModifiers, int knights, int mages,
			int physicians, Knights knight, Mages mage, Physicians physician, boolean cleared) {
		this.name = name;
		WHICH_LINE = wHICH_LINE;
		this.numberOfEnemies = numberOfEnemies;
		this.enemiesKilled = enemiesKilled;
		this.negativeModifiers = negativeModifiers;
		this.rewardModifiers = rewardModifiers;
		this.knights = knights;
		this.mages = mages;
		this.physicians = physicians;
		this.knight = knight;
		this.mage = mage;
		this.physician = physician;
		this.cleared = cleared;
	}
	
	public String stats() {
		StringBuilder s = new StringBuilder();
		s.append(name + " " + WHICH_LINE + " " + (int)enemiesKilled + "/" + (int)numberOfEnemies + " (" + ((int)(10000*enemiesKilled/numberOfEnemies))/100.0 + "%)" + "\n"
				+ "knigts: " + knights + "/" + (int)knight.getIdle() + "\n"
						+ "mages: " + mages + "/" + (int)mage.getIdle() + "\n"
								+ "physicians: " + physicians + "/" + (int)physician.getIdle() + "\n"
										+ "heroes: " + knight.getHeroes() + "\n"
												+ "UD: " + last_unit_deaths + " ED: " + last_enemy_deaths + "\n");
		return s.toString();
	}
	
	public boolean updateLine(GameState gs, long time) {
		currentdelta += time;
		if(currentdelta < 5000) return false;
		currentdelta = 0;
		if(cleared) return true;
		double liveEnemies = numberOfEnemies - enemiesKilled;
		int k = knights;
		double enemiesToBeKilled = 0;
		
		double enemyPower = liveEnemies * gs.getValue(Modifier.DEATH_RATE) - physicians *gs.getValue(Modifier.PHYSICIAN_STRENGTH) - knight.getHeroes() * gs.getValue(Modifier.HERO_STRENGTH);
		double playerPower = 0;
		
		if(this.WHICH_LINE >= 1 && this.WHICH_LINE < 3) {
			playerPower = k * gs.getValue(Modifier.KNIGHT_STRENGTH) + mages * gs.getValue(Modifier.MAGE_STRENGTH) + knight.getHeroes() * gs.getValue(Modifier.HERO_STRENGTH);
			if(enemiesKilled/numberOfEnemies > 0.85 && this.WHICH_LINE == 1) {
				// final boss for the front line
				playerPower *= gs.getValue(Modifier.AGAINST_BOSS_STRENGTH);
				enemyPower = numberOfEnemies * gs.getValue(Modifier.DEATH_RATE) * this.BOSS_STRENGTH_MULTIPLIER - physicians *gs.getValue(Modifier.PHYSICIAN_STRENGTH) - knight.getHeroes() * gs.getValue(Modifier.HERO_STRENGTH);
				
			}
			knightsToBeKilled += enemyPower * gs.ALL_UNITS_DEATH_RATE;
			enemiesToBeKilled = playerPower * gs.ALL_UNITS_DEATH_RATE;
			setEnemiesKilled(enemiesToBeKilled);
			knightsToBeKilled -= killKnights((int)knightsToBeKilled);
			if(enemiesKilled >= numberOfEnemies){
				setCleared();
				// For line 1 you get 3 perk points, For line 2 you get 2 perk points
				Program.gameState.setPerkPoints(Program.gameState.getPerkPoint() + (WHICH_LINE == 1 ? 3 : 2));
			}
			
		}else if(this.WHICH_LINE == 3){
			if(enemiesKilled/numberOfEnemies <= 0.1) {
				if(gs.getValue(Modifier.DAILY_START) >= 0.5) {
					enemiesKilled = numberOfEnemies/10;
				}
			}
			playerPower = (k * gs.getValue(Modifier.KNIGHT_STRENGTH) + mages * gs.getValue(Modifier.MAGE_STRENGTH) + knight.getHeroes() * gs.getValue(Modifier.HERO_STRENGTH)) * gs.getValue(Modifier.DAILY_STRENGTH);
			enemiesToBeKilled = playerPower * gs.ALL_UNITS_DEATH_RATE;
			setEnemiesKilled(enemiesToBeKilled);
			knightsToBeKilled += enemyPower * gs.ALL_UNITS_DEATH_RATE;
			knightsToBeKilled -= killKnights((int)knightsToBeKilled);
			if(enemiesKilled >= numberOfEnemies){
				// Give a bonus
				setCleared();
				// Add the equivalent enemies that were killed as new knights
				knight.add((int)enemiesKilled);
				Program.gameState.setPerkPoints(Program.gameState.getPerkPoint() + 1);
			}
			
		}

		last_enemy_deaths = enemiesToBeKilled;
		last_unit_deaths = knightsToBeKilled;
		if(knights <= 0) {
			retreat();
			knightsToBeKilled = 0;
		}
		if(knightsToBeKilled < 0){
			knightsToBeKilled = 0;
		}
		return true;
	}

	public String getOurPercent(){
		if(enemiesKilled/numberOfEnemies > 0.85  && enemiesKilled/numberOfEnemies < 1.0 && this.WHICH_LINE == 1){
			return "Boss";
		}else if(isCleared()) {
			return "Cleared";
		}else{
			int num = (int)(numberOfEnemies-enemiesKilled);
			return "Enemies\n" + (num == 0 ? 1 : num) ;
		}
	}
	
	public void apu1Attack(GameState gs) {
		double apuPower = gs.getValue(Modifier.APU1) * gs.getValue(Modifier.APU_STRENGTH) * GameState.APU1_BASE * gs.apu1CanStrike();
		setEnemiesKilled(apuPower);
	}
	
	public void apu2Attack(GameState gs) {
		double apuPower = gs.getValue(Modifier.APU2) * gs.getValue(Modifier.APU_STRENGTH) * GameState.APU2_BASE * gs.apu2CanStrike();
		setEnemiesKilled(apuPower);
	}
	
	public void setNumberOfEnemies(int num) {
		this.numberOfEnemies = numberOfEnemies + num;
	}
	
	public void setEnemiesKilled(double num) {
		if(num + enemiesKilled > numberOfEnemies) {
			enemiesKilled = numberOfEnemies;
			knight.addEnemiesKilled(numberOfEnemies - enemiesKilled); 
			return;
		}
		knight.addEnemiesKilled(num);
		this.enemiesKilled = enemiesKilled + num;
	}
	public int killKnights(int num) {
		if(knights <= 0 || num <= 0) return 0;
		if(knights >= num) {
			knights -= num;
			knight.addFighting(-num);
			knight.remove(num);
			return num;
		}
		else {
			knight.addFighting(-knights);
			knight.remove(knights);
			int temp = knights;
			knights = 0;
			return temp;
		}
	}
	
	public void retreat() {
		int m = mages;
		addMages(-m);
		mage.remove(m/2);
		int p = physicians;
		addPhysicians(-p);
		physician.remove(p/2);
		
	}

	public void safeRetreat() {
		int m = mages;
		addMages(-m);
		int p = physicians;
		addPhysicians(-p);

	}

	public void addKnights(int num) {
		this.knights += knight.addFighting(num);
	}
	public void addMages(int num) {
		if(knights <= 0 && num >= 0) return;
		this.mages += mage.addFighting(num);
	}
	public void addPhysicians(int num) {
		if(knights <= 0 && num >= 0) return;
		this.physicians += physician.addFighting(num);
	}
	public String getName() {
		return name;
	}
	public int getWHICH_LINE() {
		return WHICH_LINE;
	}
	public ArrayList<Modifier> getNegativeModifiers() {
		return negativeModifiers;
	}
	public ArrayList<Modifier> getRewardModifiers() {
		return rewardModifiers;
	}
	public void setCleared() {
		addKnights(-knights);
		addMages(-mages);
		addPhysicians(-physicians);
		cleared = true;
	}
	
	public boolean isCleared() {
		return cleared;
	}
	public int getKnights() {
		return knights;
	}
	public int getMages() {
		return mages;
	}
	public int getPhysicians() {
		return physicians;
	}
	
	public int getNumberOfEnemies() {
		return numberOfEnemies;
	}
	public double getEnemiesKilled() {
		return enemiesKilled;
	}
	public void setEnemiesKilledNormal(double num) {
		this.enemiesKilled = num;
	}

	public int getCurrent_loop_iteration() {
		return current_loop_iteration;
	}

	public void setCurrent_loop_iteration(int current_loop_iteration) {
		this.current_loop_iteration = current_loop_iteration;
	}

	public double getLast_enemy_deaths() {
		return last_enemy_deaths;
	}

	public void setLast_enemy_deaths(double last_enemy_deaths) {
		this.last_enemy_deaths = last_enemy_deaths;
	}

	public double getLast_unit_deaths() {
		return last_unit_deaths;
	}

	public void setLast_unit_deaths(double last_unit_deaths) {
		this.last_unit_deaths = last_unit_deaths;
	}

	public void setKnights(int knights) {
		this.knights = knights;
	}

	public void setMages(int mages) {
		this.mages = mages;
	}

	public void setPhysicians(int physicians) {
		this.physicians = physicians;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}
	
	
}
