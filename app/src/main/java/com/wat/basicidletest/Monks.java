package com.wat.basicidletest;

public class Monks extends Groups{
	protected long recruiting;//
	protected long training;//
	protected long improvements;//
	protected long mining;//

	public Monks(String name, double count, double growthRate, long r, long t, long e, long m) {
		super(name, count, growthRate);
		recruiting = r;
		training = t;
		improvements = e;
		mining = m;
		idle = count - r - t - e - m;
	}
	
	
	
	@Override
	public void update(GameState gs, long time) {
		if(!start) return;
		double g = growthRate * gs.getValue(Modifier.RECRUIT_RATE) * 5/12.0;
		double newUnits = g*time;
		if(count + newUnits > Double.MAX_VALUE) {
		}else {
			count = count + newUnits;
			idle = idle + newUnits;
		}
	}

	public void addRecruiting(int x) {
		if(x > 0) {
			//moving into recruiting
			if(idle - x >= 0) {
				recruiting += x;
				idle -= x;
			}
		}else {
			//moving out of recruiting
			if(recruiting + x >= 0) {
				recruiting += x;
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
	
	public void addImprovements(int x) {
		if(x > 0) {
			//moving into improvements
			if(idle - x >= 0) {
				improvements += x;
				idle -= x;
			}
		}else {
			//moving out of improvements
			if(improvements + x >= 0) {
				improvements += x;
				idle -=x;
			}
		}
	}
	
	public void addMining(int x) {
		if(x > 0) {
			//moving into mining
			if(idle - x >= 0) {
				mining += x;
				idle -= x;
			}
		}else {
			//moving out of mining
			if(mining + x >= 0) {
				mining  += x;
				idle -=x;
			}
		}
	}



	@Override
	public String stats() {
		String s = super.stats();
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		sb.append("recruiting: " + this.getRecruiting() + "\n");
		sb.append("training: " + this.getTraining() + "\n");
		sb.append("improvements: " + this.getImprovements() + "\n");
		sb.append("mining: " + this.getMining() + "\n");
		sb.append("idle: " + (int)this.idle + "\n");
		// TODO Auto-generated method stub
		return sb.toString();
	}



	public long getRecruiting() {
		return recruiting;
	}



	public void setRecruiting(long recruiting) {
		this.recruiting = recruiting;
	}



	public long getTraining() {
		return training;
	}



	public void setTraining(long training) {
		this.training = training;
	}



	public long getImprovements() {
		return improvements;
	}



	public void setImprovements(long improvements) {
		this.improvements = improvements;
	}



	public long getMining() {
		return mining;
	}



	public void setMining(long mining) {
		this.mining = mining;
	}


	
	

}
