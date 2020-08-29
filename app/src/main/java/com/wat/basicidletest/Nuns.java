package com.wat.basicidletest;

public class Nuns extends Groups{

	protected long farming;//
	protected long training;//
	protected long studying;//
	protected long praying;//

	public Nuns(String name, double count, double growthRate, long r, long t, long e, long m) {
		super(name, count, growthRate);
		farming = r;
		training = t;
		studying = e;
		praying = m;
		idle = count - r - t - e - m;
	}
	
	
	
	@Override
	public void update(GameState gs, long time) {
		if(!start) return;
		double g = growthRate * gs.getValue(Modifier.RECRUIT_RATE) * 4/12.0;
		double newUnits = g*time;
		if(count + newUnits > Double.MAX_VALUE) {
		}else {
			count = count + newUnits;
			idle = idle + newUnits;
		}
	}

	public void addFarming(int x) {
		if(x > 0) {
			//moving into farming
			if(idle - x >= 0) {
				farming += x;
				idle -= x;
			}
		}else {
			//moving out of farming
			if(farming + x >= 0) {
				farming += x;
				idle -=x;
			}
		}
	}
	
	public int addTraining(int x) {
		if(x > 0) {
			//moving into training
			if(idle - x >= 0) {
				training += x;
				idle -= x;
				return x;
			}
		}else {
			//moving out of training
			if(training + x >= 0) {
				training += x;
				idle -=x;
				return x;
			}
		}
		return 0;
	}
	
	public void addStudying(int x) {
		if(x > 0) {
			//moving into studying
			if(idle - x >= 0) {
				studying += x;
				idle -= x;
			}
		}else {
			//moving out of studying
			if(studying + x >= 0) {
				studying += x;
				idle -=x;
			}
		}
	}
	
	public void addPraying(int x) {
		if(x > 0) {
			//moving into praying
			if(idle - x >= 0) {
				praying += x;
				idle -= x;
			}
		}else {
			//moving out of praying
			if(praying + x >= 0) {
				praying  += x;
				idle -=x;
			}
		}
	}



	@Override
	public String stats() {
		String s = super.stats();
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		sb.append("farming: " + this.getFarming() + "\n");
		sb.append("training: " + this.getTraining() + "\n");
		sb.append("studying: " + this.getStudying() + "\n");
		sb.append("praying: " + this.getPraying() + "\n");
		sb.append("idle: " + (int)this.idle + "\n");
		// TODO Auto-generated method stub
		return sb.toString();
	}



	public long getFarming() {
		return farming;
	}



	public void setFarming(long farming) {
		this.farming = farming;
	}



	public long getTraining() {
		return training;
	}



	public void setTraining(long training) {
		this.training = training;
	}



	public long getStudying() {
		return studying;
	}



	public void setStudying(long studying) {
		this.studying = studying;
	}



	public long getPraying() {
		return praying;
	}



	public void setPraying(long praying) {
		this.praying = praying;
	}
	
	
	

}
