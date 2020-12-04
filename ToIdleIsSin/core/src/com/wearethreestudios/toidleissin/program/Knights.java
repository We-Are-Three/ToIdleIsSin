package com.wearethreestudios.toidleissin.program;

public class Knights extends Groups {

	protected long fighting;//
	protected long goodworks;//
	protected long heroes;//
	protected double enemiesKilled;//
	private final double ENEMIES_KILLED_TO_HEROES = 0.0001;

	public Knights(String name, double count, double growthRate, long r, long t) {
		super(name, count, growthRate);
		fighting = r;
		goodworks = t;
		idle = count - r - t;
		heroes = 0;
	}	
	
	
	@Override
	public void update(GameState gs, long time) {
		if(!start) return;
		double g = growthRate * gs.getValue(Modifier.RECRUIT_RATE)* 3/12.0;
		double newUnits = g*time;
		if(newUnits > gs.getTOTAL_UNITS()) newUnits = gs.getTOTAL_UNITS();
		if(count + newUnits > Double.MAX_VALUE) {
		}else {
			count = count + newUnits;
			idle = idle + newUnits;
		}
		heroes = (long)(enemiesKilled * ENEMIES_KILLED_TO_HEROES * gs.getValue(Modifier.HERO_RATE));
	}
	
	public void addEnemiesKilled(double num) {
		enemiesKilled += num;
	}

	public int addFighting(int x) {
		if(x > 0) {
			//moving into fighting
			if(idle - x >= 0) {
				fighting += x;
				idle -= x;
				return x;
			}
		}else {
			//moving out of fighting
			if(fighting + x >= 0) {
				fighting += x;
				idle -=x;
				return x;
			}
		}
		return 0;
	}
	
	public void addGoodworks(int x) {
		if(x > 0) {
			//moving into goodworks
			if(idle - x >= 0) {
				goodworks += x;
				idle -= x;
			}
		}else {
			//moving out of goodworks
			if(goodworks + x >= 0) {
				goodworks += x;
				idle -=x;
			}
		}
	}

	@Override
	public String stats() {
		String s = super.stats();
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		sb.append("fighting: " + this.getFighting() + "\n");
		sb.append("goodworks: " + this.getGoodworks() + "\n");
		sb.append("idle: " + (int)this.idle + "\n");
		// TODO Auto-generated method stub
		return sb.toString();
	}
	
	public long getHeroes() { return heroes;}
	public void setHeroes(int num) { heroes = num;}
	
	public long getFighting() { return fighting; }

	public long getGoodworks() { return goodworks; }


	public double getEnemiesKilled() {
		return enemiesKilled;
	}


	public void setEnemiesKilled(double enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}


	public void setFighting(long fighting) {
		this.fighting = fighting;
	}


	public void setGoodworks(long goodworks) {
		this.goodworks = goodworks;
	}


	public void setHeroes(long heroes) {
		this.heroes = heroes;
	}
	

}
