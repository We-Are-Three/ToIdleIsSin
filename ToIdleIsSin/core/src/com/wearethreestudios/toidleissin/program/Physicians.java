package com.wearethreestudios.toidleissin.program;

public class Physicians extends Groups{


	protected long fighting;
	private Nuns n;
	private double overflow;

	public Physicians(String name, double count, double growthRate, long r, long t, double o, Nuns n) {
		super(name, count, growthRate);
		fighting = r;
		idle = count - r - t;
		this.n = n;
	}
	
	public boolean addorsubtract(int x) {
		if( x < 0) {
			if(this.remove(-x)) {
				n.add(-x);
			}
		}else if(x > 0) {
			if(n.addTraining(-x) == -x) {
				n.remove(x);
				add(x);
				return true;
			}
		}
		return false;
	}

	@Override
	public  void update(GameState gs, long time) {
		if(!start) return;
		growthRate = gs.getValue(Modifier.TRAIN) * gs.getValue(Modifier.PHYSICIAN_RATE) * n.getTraining()/ 1000000.0;
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
