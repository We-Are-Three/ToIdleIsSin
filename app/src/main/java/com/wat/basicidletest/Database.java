package com.wat.basicidletest;
import java.util.ArrayList;

public class Database{
		public double perkPoints;
		public long lastUpdate;
		public long totalDeserted;
		public long last_apu1_strike;
		public long last_apu2_strike;

		public double monkcount;
		public double monkidle;
		public double monkgrowthrate;
		public long monkrecruit;
		public long monktraining;
		public long monkimprovements;
		public long monkmining;

		public double nuncount;
		public double nunidle;
		public double nungrowthrate;
		public long nunfarming;
		public long nuntraining;
		public long nunstudying;
		public long nunpraying;

		public double knightcount;
		public double knightidle;
		public double knightgrowthrate;
		public long knightfighting;
		public long goodworks;
		public long heroes;
		public double knightenemieskilled;

		public double magecount;
		public double mageidle;
		public double magegrowthrate;
		public long magefighting;
		public double mageoverflow;

		public double phycount;
		public double phyidle;
		public double phygrowthrate;
		public long phyfighting;
		public double phyoverflow;
		
		int[] virtueprogress = new int[7];
		String[] perknames = new String[30];
		int[] perkscurrentlevel = new int[30];//only 23 perks
		
		double[] linesenemieskilled = new double[7*3];
		double[] lastenemydeaths = new double[7*3];
		double[] lastunitdeaths = new double[7*3];
		int[] lineknights = new int[7*3];
		int[] linemages = new int[7*3];
		int[] linephy = new int[7*3];
		int[] currentloopiteration = new int[7*3];
		boolean[] cleared = new boolean[7*3];
		
		public Database(GameState gs) {
			Monks m = (Monks)gs.getGroup("monks");
			monkcount = m.getCountnormal();
			monkidle = m.getIdle();
			this.monkgrowthrate = m.getGrowthRate();
			this.monkrecruit = m.getRecruiting();
			this.monktraining = m.getTraining();
			this.monkimprovements = m.getImprovements();
			this.monkmining = m.getMining();
			Nuns n = (Nuns) gs.getGroup("nuns");
			this.nuncount = n.getCountnormal();
			this.nunidle = n.getIdle();
			this.nungrowthrate = n.getGrowthRate();
			this.nuntraining = n.getTraining();
			this.nunfarming = n.getFarming();
			this.nunstudying = n.getStudying();
			this.nunpraying = n.getPraying();
			Knights k = (Knights) gs.getGroup("knights");
			this.knightcount = k.getCountnormal();
			this.knightidle = k.getIdle();
			this.knightgrowthrate = k.getGrowthRate();
			this.knightfighting = k.getFighting();
			this.goodworks = k.getGoodworks();
			this.heroes = k.getHeroes();
			this.knightenemieskilled = k.getEnemiesKilled();
			Mages mag = (Mages) gs.getGroup("mages");
			this.magecount = mag.getCountnormal();
			this.mageidle = mag.getIdle();
			this.magegrowthrate = mag.getGrowthRate();
			this.magefighting = mag.getFighting();
			this.mageoverflow = mag.getOverflow();
			Physicians p = (Physicians) gs.getGroup("physicians");
			this.phycount = p.getCountnormal();
			this.phyidle = p.getIdle();
			this.phygrowthrate = p.getGrowthRate();
			this.phyfighting = p.getFighting();
			this.phyoverflow = p.getOverflow();
			ArrayList<Virtue> vs = gs.getVirtues();
			for(int i = 0; i < vs.size(); i++) {
				virtueprogress[i] = vs.get(i).getProgress();
			}
			ArrayList<Perk> ps = gs.getPerks();
			for(int i = 0; i < ps.size(); i++) {
				perknames[i] = ps.get(i).getName(0);
				perkscurrentlevel[i] = ps.get(i).getCurrentLevel();
			}
			ArrayList<Campaign> cs = gs.getCampaigns();
			for(int i = 0; i < cs.size(); i++) {
				for(int j = 0; j < 3; j++) {
					Lines l = new Lines("", 0, 0, 0, null, null, 0, 0, 0, null, null, null, false);
					if(j == 0) l = cs.get(i).getFirstLine();
					else if(j == 1) l = cs.get(i).getSecondLine();
					else if(j == 2) l = cs.get(i).getThirdLine();
					linesenemieskilled[i*3 + j] = l.getEnemiesKilled();
					lastenemydeaths[i*3 + j] = l.getLast_enemy_deaths();
					lastunitdeaths[i*3 + j] = l.getLast_unit_deaths();
					lineknights[i*3 + j] = l.getKnights();
					linemages[i*3 + j] = l.getMages();
					linephy[i*3 + j] = l.getPhysicians();
					currentloopiteration[i*3 + j] = l.getCurrent_loop_iteration();
					cleared[i*3 + j] = l.isCleared();
				}
			}
			perkPoints = gs.getPerkPoint();
			lastUpdate = gs.getLastUpdate();
			totalDeserted = gs.getTotalDeserted();
			last_apu1_strike = gs.getLast_apu1_strike();
			last_apu2_strike = gs.getLast_apu2_strike();
			
		}
		
		public void restore(GameState gs) {
			Monks m = (Monks)gs.getGroup("monks");
			m.setCount(monkcount);
			m.setIdle(monkidle);
			m.setGrowthRate(monkgrowthrate);
			m.setRecruiting(monkrecruit);
			m.setImprovements(monkimprovements);
			m.setMining(monkmining);
			m.setTraining(monktraining);
			Nuns n = (Nuns) gs.getGroup("nuns");
			n.setCount(nuncount);
			n.setIdle(nunidle);
			n.setGrowthRate(nungrowthrate);
			n.setTraining(nuntraining);
			n.setFarming(nunfarming);
			n.setPraying(nunpraying);
			n.setStudying(nunstudying);
			Knights k = (Knights) gs.getGroup("knights");
			k.setCount(knightcount);
			k.setIdle(knightidle);
			k.setGrowthRate(knightgrowthrate);
			k.setFighting(knightfighting);
			k.setGoodworks(goodworks);
			k.setHeroes(heroes);
			k.setEnemiesKilled(knightenemieskilled);
			Mages mag = (Mages) gs.getGroup("mages");
			mag.setCount(magecount);
			mag.setIdle(mageidle);
			mag.setGrowthRate(magegrowthrate);
			mag.setFighting(magefighting);
			mag.setOverflow(mageoverflow);
			Physicians p = (Physicians) gs.getGroup("physicians");
			p.setCount(phycount);
			p.setIdle(phyidle);
			p.setGrowthRate(phygrowthrate);
			p.setFighting(phyfighting);
			p.setOverflow(phyoverflow);
			ArrayList<Virtue> vs = gs.getVirtues();
			for(int i = 0; i < vs.size(); i++) {
				vs.get(i).setProgress(virtueprogress[i]);
			}
			
			gs.setPerkPoints(Double.MAX_VALUE);
			for(int i = 0; i < perknames.length; i++) {
				if(perknames[i] == null) break;
				Perk perk = gs.getPerk(perknames[i]);
				if(perk != null) {
					//this adds all the modifiers to the active modifier list
					for(int j = 0; j < perkscurrentlevel[i]; j++) {
						gs.addModifiers(perk);
					}
				}
			}
			ArrayList<Campaign> cs = gs.getCampaigns();
			for(int i = 0; i < cs.size(); i++) {
				for(int j = 0; j < 3; j++) {
					Lines l = new Lines("", 0, 0, 0, null, null, 0, 0, 0, null, null, null, false);
					if(j == 0) l = cs.get(i).getFirstLine();
					else if(j == 1) l = cs.get(i).getSecondLine();
					else if(j == 2) l = cs.get(i).getThirdLine();
					l.setEnemiesKilledNormal(linesenemieskilled[i*3 + j]);
					l.setLast_enemy_deaths(lastenemydeaths[i*3 + j]);
					l.setLast_unit_deaths(lastunitdeaths[i*3 + j]);
					l.setKnights(lineknights[i*3 + j]);
					l.setMages(linemages[i*3 + j]);
					l.setPhysicians(linephy[i*3 + j]);
					l.setCurrent_loop_iteration(currentloopiteration[i*3 + j]);
					l.setCleared(cleared[i*3 + j]);
				}
			}
			gs.setPerkPoints(perkPoints);
			gs.setLastUpdate(lastUpdate);
			gs.setTotalDeserted(totalDeserted);
			gs.setLast_apu1_strike(last_apu1_strike);
			gs.setLast_apu2_strike(last_apu2_strike);
		}
	}