package com.wearethreestudios.toidleissin.program;

public class Groups {
	protected String name;

	protected  double count;
	protected  double idle;
	protected  double growthRate;
	protected  boolean start;
	
	public Groups(String name, double count, double growthRate) {
		this.name = name;
		this.count = count;
		this.growthRate = growthRate;
		start = false;
	}
	
	public String stats() {
		StringBuilder s = new StringBuilder();
		s.append("Total " + this.getName() + ": " + this.getCount() + "\n"
				+ "growth Rate (s): " + this.getGrowthRate()*1000 + "\n");
		return s.toString();
	}
	
	public  void start() { start = true;}
	
	public  void update(GameState gs, long time) {
		if(!start) return;
		if(count + (long)(growthRate*time) > Long.MAX_VALUE) {
		}else {
			count = count + growthRate*time;
			idle = idle + growthRate*time;
		}
	}
	
	public boolean remove(int x) {
		if(idle >= x) {
			count -= x;
			idle -= x;
			return true;
		}
		return false;
	}
	
	public void add(int x) {
		count += x;
		idle += x;
	}
	
	public  void addToGrowthRate(long addMe) {
		growthRate = growthRate + addMe;
	}

	public  long getCount() {
		return (long)count;
	}
	
	public  String getName() {
		return name;
	}

	public double getCountnormal() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public double getIdle() {
		return idle;
	}

	public void setIdle(double idle) {
		this.idle = idle;
	}

	public double getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	
	
	
	
	
}
