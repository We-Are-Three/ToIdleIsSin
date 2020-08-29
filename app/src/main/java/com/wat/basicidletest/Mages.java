package com.wat.basicidletest;

public class Mages extends Groups{

	protected long fighting;
	private Monks m;
	private double overflow;

	public Mages(String name, double count, double growthRate, long r, long t, double o, Monks m) {
		super(name, count, growthRate);
		fighting = r;
		idle = count - r - t;
		this.m = m;
		overflow = o;
	}
	
	public boolean addorsubtract(int x) {
		if( x < 0) {
			if(this.remove(-x)) {
				m.add(-x);
				return true;
			}
		}else if(x > 0) {
			if(m.addTraining(-x) == -x) {
				m.remove(x);
				add(x);
				return true;
			}
		}
		return false;
	}

	@Override
	public  void update(GameState gs, long time) {
		if(!start) return;
		growthRate = gs.getValue(Modifier.TRAIN) * gs.getValue(Modifier.MAGE_RATE) * m.getTraining()/ 1000000.0;
		if(count + (long)(growthRate*time) + overflow > Long.MAX_VALUE) {
		}else {
			overflow = overflow + (growthRate*time);
			if(overflow >= 1) {
				addorsubtract((int)overflow);
				overflow = overflow - (int) overflow;
			}
		}
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

	@Override
	public String stats() {
		String s = super.stats();
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		sb.append("fighting: " + this.getFighting() + "\n");
		sb.append("idle: " + (int)this.idle + "\n");
		// TODO Auto-generated method stub
		return sb.toString();
	}
	
	public long getFighting() { return fighting; }

	public double getOverflow() {
		return overflow;
	}

	public void setOverflow(double overflow) {
		this.overflow = overflow;
	}

	public void setFighting(long fighting) {
		this.fighting = fighting;
	}
	

}
