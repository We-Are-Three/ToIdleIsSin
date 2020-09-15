package com.wearethreestudios.toidleissin.program;

import com.badlogic.gdx.Gdx;
import com.wearethreestudios.toidleissin.ToIdleIsSin;

import java.util.ArrayList;

public class Commands {
	ArrayList<Command> commands = new ArrayList<>();
	String activeScreen = "";
	boolean valGiven = false;
	int val = 0;
	
	public Commands() {
		Command StartGame = new Command(   "Start Game"   );
		commands.add(StartGame);
		Command restoregame = new Command(   "restoregame"   );
		commands.add(restoregame);
		Command Settings = new Command(    "Settings"   );
		commands.add(Settings);
		
		Command Village = new Command(     "Village"   );
		commands.add(Village);
		
		Command Monastery = new Command(   "Monastery"   );
		commands.add(Monastery);
		Command MonkStats = new Command(   "Monk Stats"   );
		commands.add(MonkStats);
		Command recruit = new Command("Recruit");
		commands.add(recruit);
		Command MonkTraining = new Command("MonkTraining");
		commands.add(MonkTraining);
		Command VillageImprovements = new Command("VillageImprovements");
		commands.add(VillageImprovements);
		Command MonkMiningBonus = new Command("MonkMiningBonus");
		commands.add(MonkMiningBonus);
		Monastery.addSubCommand(MonkStats, recruit, MonkTraining, VillageImprovements, MonkMiningBonus);
		
		Command Nunnary = new Command(     "Nunnary"   );
		commands.add(Nunnary);
		Command NunStats = new Command(    "Nun Stats"   );
		commands.add(NunStats);
		Command farming = new Command("farming");
		commands.add(farming);
		Command nuntraining = new Command("nuntraining");
		commands.add(nuntraining);
		Command studying = new Command("studying");
		commands.add(studying);
		Command praying = new Command("praying");
		commands.add(praying);
		Nunnary.addSubCommand(NunStats, farming, nuntraining, studying, praying);
		
		Command Cathedral = new Command(   "Cathedral"  );
		commands.add(Cathedral);
		Command vst = new Command(         "Virtue Skill Tree");
		commands.add(vst);
		Cathedral.addSubCommand(vst);
		Command chastityperks = new Command("chastityperks");
		commands.add(chastityperks);
		Command temperanceperks = new Command("temperanceperks");
		commands.add(temperanceperks);
		Command charityperks = new Command("charityperks");
		commands.add(charityperks);
		Command diligenceperks = new Command("diligenceperks");
		commands.add(diligenceperks);
		Command patienceperks = new Command("patienceperks");
		commands.add(patienceperks);
		Command kindnessperks = new Command("kindnessperks");
		commands.add(kindnessperks);
		Command humilityperks = new Command("humilityperks");
		commands.add(humilityperks);
		vst.addSubCommand(chastityperks, temperanceperks, charityperks, diligenceperks, patienceperks, kindnessperks, humilityperks);
		
		Command barracks = new Command("barracks");
		commands.add(barracks);
		Command barrackstats = new Command(    "barrackstats"   );
		commands.add(barrackstats);
		Command physiciantonun = new Command("physiciantonun");
		commands.add(physiciantonun);
		Command magetomonk = new Command("magetomonk");
		commands.add(magetomonk);
		Command goodworks = new Command("goodworks");
		commands.add(goodworks);
		barracks.addSubCommand(barrackstats, physiciantonun, magetomonk, goodworks);
		Village.addSubCommand(Monastery, Nunnary, barracks, Cathedral);

//		For Monero Mining
		Command mines = new Command("mines");
		commands.add(mines);
		Village.addSubCommand(mines);
		
		
		Command Battles = new Command(     "Battles"   );
		commands.add(Battles);
		Command c1 = new Command("campaign1");
		commands.add(c1);
		Command c2 = new Command("campaign2");
		commands.add(c2);
		Command c3 = new Command("campaign3");
		commands.add(c3);
		Command c4 = new Command("campaign4");
		commands.add(c4);
		Command c5 = new Command("campaign5");
		commands.add(c5);
		Command c6 = new Command("campaign6");
		commands.add(c6);
		Command c7 = new Command("campaign7");
		commands.add(c7);
		Battles.addSubCommand(c1, c2, c3, c4, c5, c6, c7);
		
		
		Command VisualNovel = new Command( "Visual Novel"   );
		commands.add(VisualNovel);
		Command chastity = new Command( "chastity"   );
		commands.add(chastity);
		Command temperance = new Command( "temperance"   );
		commands.add(temperance);
		Command charity = new Command( "charity"   );
		commands.add(charity);
		Command diligence = new Command( "diligence"   );
		commands.add(diligence);
		Command patience = new Command( "patience"   );
		commands.add(patience);
		Command kindness = new Command( "kindness"   );
		commands.add(kindness);
		Command humility = new Command( "humility"   );
		commands.add(humility);
		Command mainstory = new Command( "mainstory"   );
		commands.add(mainstory);
		VisualNovel.addSubCommand(chastity, temperance, charity, diligence, patience, kindness, humility, mainstory);
		
		Command one = new Command("one");
		commands.add(one);
		Command two = new Command("two");
		commands.add(two);
		Command three = new Command("three");
		commands.add(three);
		Command four = new Command("four");
		commands.add(four);
		Command five = new Command("five");
		commands.add(five);
		Command six = new Command("six");
		commands.add(six);
		Command seven = new Command("seven");
		commands.add(seven);
		
		Command addknights = new Command("addKnights");
		commands.add(addknights);
		Command addmages = new Command("addMages");
		commands.add(addmages);
		Command addphysicians = new Command("addPhysicians");
		commands.add(addphysicians);
		Command apu1 = new Command("apu1");
		commands.add(apu1);
		Command apu2 = new Command("apu2");
		commands.add(apu2);
		
		one.addSubCommand(addknights, addmages, addphysicians, apu1, apu2);
		two.addSubCommand(addknights, addmages, addphysicians, apu1, apu2);
		three.addSubCommand(addknights, addmages, addphysicians, apu1, apu2);
		chastityperks.addSubCommand(one, two, three, four);
		temperanceperks.addSubCommand(one, two, three, four);
		charityperks.addSubCommand(one, two, three, four);
		diligenceperks.addSubCommand(one, two, three, four);
		patienceperks.addSubCommand(one, two, three, four);
		kindnessperks.addSubCommand(one, two, three, four);
		humilityperks.addSubCommand(one, two, three, four);
		c1.addSubCommand(one, two, three);
		c2.addSubCommand(one, two, three);
		c3.addSubCommand(one, two, three);
		c4.addSubCommand(one, two, three);
		c5.addSubCommand(one, two, three);
		c6.addSubCommand(one, two, three);
		c7.addSubCommand(one, two, three);
		
		
		
		
		StartGame.setActive(true);
		restoregame.setActive(true);
		
	}
	
	private void setGUIActive() {
		setActive(true, true, "village", "battles", "visual novel");
	}
	
	public boolean execute(String name, GameState gameState) {
		boolean returnBoolean = true;
		Command order = getCommand(name);
		Monks m;
		Nuns n;
		Lines l;
		String o = "";
		String[] addthese;
		if(valGiven) {
			o = activeScreen;
		}else {
			o = order.getName(1);
			val = 0;
		}
		switch(o) {
		case "start game": 
			this.setAllInactive(); this.setGUIActive();
			activeScreen = "battles";
			gameState.start();
			break;
		case "restoregame": 
			this.setAllInactive(); this.setGUIActive();
			activeScreen = "battles";
			Program.loadGame();
//			gameState.start();
			break;
			
			
		case "village":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "monastery", "nunnary", "cathedral", "barracks", "mines");
			activeScreen = o;
			break;


		case "mines":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "monastery", "nunnary", "cathedral", "barracks", "mines");
			gameState.moneroMiningBonus();
			if(!Program.isMoneroMining()){
				Program.startMoneroMining();
			} else {
				Program.stopMoneroMining();
			}
			break;


		case "monastery":
		case "monk stats":
		case "recruit":
		case "monktraining":
		case "villageimprovements":
		case "monkminingbonus":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "monastery", "monk stats", "recruit", "monktraining", "villageimprovements", "monkminingbonus");
			activeScreen = o;
			m = (Monks) gameState.getGroup("monks");
			if(m != null) {
				if(o.contentEquals("recruit")) {
					Program.print(gameState.getStats("monks"));
					if(!valGiven) {Program.print("\nEnter number of monks to move to recruiting effors: "); returnBoolean = false; break; }
					m.addRecruiting(val);
					activeScreen = "monastery";
					
				}else if(o.contentEquals("monktraining")) {
					Program.print(gameState.getStats("monks"));
					if(!valGiven) {Program.print("\nEnter number of monks to train to become mages: "); returnBoolean = false; break; }
					m.addTraining(val);
					activeScreen = "monastery";
					
				}else if(o.contentEquals("villageimprovements")) {
					Program.print(gameState.getStats("monks"));
					if(!valGiven) {Program.print("\nEnter number of monks to move to village improvement effors: "); returnBoolean = false; break; }
					m.addImprovements(val);
					activeScreen = "monastery";
					
				}else if(o.contentEquals("monkminingbonus")) {
					Program.print(gameState.getStats("monks"));
					if(!valGiven) {Program.print("\nEnter number of monks to move to mining efforts (gives a bonus when you mine): "); returnBoolean = false; break; }
					m.addMining(val);	
					activeScreen = "monastery";	
				}
			}
			Program.print(gameState.getStats("monks"));
			break;
			
			
		case "nunnary":
		case "nun stats":
		case "farming":
		case "nuntraining":
		case "studying":
		case "praying":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "nunnary", "nun stats", "farming", "nuntraining", "studying", "praying");
			activeScreen = o;
			n = (Nuns) gameState.getGroup("nuns");
			if(n != null) {
				if(o.contentEquals("farming")) {
					Program.print(gameState.getStats("nuns"));
					if(!valGiven) {System.out.print("\nEnter number of nuns to farm: "); returnBoolean = false; break; }
					n.addFarming(val);
					activeScreen = "nunnary";
					
				}else if(o.contentEquals("nuntraining")) {
					Program.print(gameState.getStats("nuns"));
					if(!valGiven) {System.out.print("\nEnter number of nuns to train to become physicians: "); returnBoolean = false; break; }
					n.addTraining(val);
					activeScreen = "nunnary";
					
				}else if(o.contentEquals("studying")) {
					Program.print(gameState.getStats("nuns"));
					if(!valGiven) {System.out.print("\nEnter number of nuns to study (gains perk points): "); returnBoolean = false; break; }
					n.addStudying(val);
					activeScreen = "nunnary";
					
				}else if(o.contentEquals("praying")) {
					Program.print(gameState.getStats("nuns"));
					if(!valGiven) {System.out.print("\nEnter number of nuns to pray (for daily bonus): "); returnBoolean = false; break; }
					n.addPraying(val);
					activeScreen = "nunnary";
				}
			}
			Program.print(gameState.getStats("nuns"));
			break;
			
			
		case "barracks":
		case "barrackstats":
		case "physiciantonun":
		case "magetomonk":
		case "goodworks":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "barracks", "barrackstats", "physiciantonun", "magetomonk", "goodworks");
			activeScreen = o;
			if(o.contentEquals("physiciantonun")) {
				Program.print(gameState.getStats("physicians"));
				if(!valGiven) {System.out.print("\nEnter number of Physicians to turn back into nuns: "); returnBoolean = false; break; }
				((Physicians) gameState.getGroup("physicians")).addorsubtract(-val);
				activeScreen = "barracks";
			}else if(o.contentEquals("magetomonk")) {
				Program.print(gameState.getStats("mages"));
				if(!valGiven) {System.out.print("\nEnter number of Mages to turn back into monks: "); returnBoolean = false; break; }
				((Mages) gameState.getGroup("mages")).addorsubtract(-val);
				activeScreen = "barracks";
				
			}else if(o.contentEquals("goodworks")) {
				Program.print(gameState.getStats("knights"));
				if(!valGiven) {System.out.print("\nEnter number of Knights to send out to do good works: "); returnBoolean = false; break; }
				((Knights) gameState.getGroup("knights")).addGoodworks(val);
				activeScreen = "barracks";
				
			}
			Program.print(gameState.getStats("knights"));
			Program.print(gameState.getStats("physicians"));
			Program.print(gameState.getStats("mages"));
			break;
			
			
		case "cathedral":
		case "virtue skill tree":
		case "chastityperks":
		case "temperanceperks":
		case "charityperks":
		case "diligenceperks":
		case "patienceperks":
		case "kindnessperks":
		case "humilityperks":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "cathedral", "virtue skill tree");
			Program.print("Available perk points: " + (int)gameState.getPerkPoint());
			activeScreen = o;
			if(o.contentEquals("virtue skill tree")) {
				addthese = new String[7];
				if(gameState.getVirtue("chastity").getProgress() != 0) addthese[0] ="chastityperks";
				if(gameState.getVirtue("temperance").getProgress() != 0) addthese[1] ="temperanceperks";
				if(gameState.getVirtue("charity").getProgress() != 0) addthese[2] ="charityperks";
				if(gameState.getVirtue("diligence").getProgress() != 0) addthese[3] ="diligenceperks";
				if(gameState.getVirtue("patience").getProgress() != 0) addthese[4] ="patienceperks";
				if(gameState.getVirtue("kindness").getProgress() != 0) addthese[5] ="kindnessperks";
				if(gameState.getVirtue("humility").getProgress() != 0) addthese[6] ="humilityperks";
				setActive(true, true, addthese);
			}
			setActive(true, true, activeScreen);
			if(!o.contentEquals("cathedral") && !o.contentEquals("virtue skill tree")) {
				setActive(true, true, "one", "two", "three", "four");
				Command one = this.getCommand("one");
				Command two = this.getCommand("two");
				Command three = this.getCommand("three");
				Command four = this.getCommand("four");
				one.setFinerName(o);
				two.setFinerName(o);
				three.setFinerName(o);
				four.setFinerName(o);
				Perk p = gameState.getPerk(one.getName());
				if(p != null) one.setFinerName(p.getPrettyName());
				p = gameState.getPerk(two.getName());
				if(p != null) two.setFinerName(p.getPrettyName());
				p = gameState.getPerk(three.getName());
				if(p != null) three.setFinerName(p.getPrettyName());
				p = gameState.getPerk(four.getName());
				if(p != null) four.setFinerName(p.getPrettyName());
			}
			if(o.contentEquals("chastityperks")) {
				setActive(true, true, "chastityperks", "one", "two", "three", "four");
				
			}else if(o.contentEquals("temperanceperks")) {
				setActive(true, true, "temperanceperks", "one", "two", "three", "four");
				setActive(false, true, "four");
				
			}else if(o.contentEquals("charityperks")) {
				setActive(true, true, "charityperks", "one", "two", "three", "four");
				
			}else if(o.contentEquals("diligenceperks")) {
				setActive(true, true, "diligenceperks", "one", "two", "three", "four");
				
			}else if(o.contentEquals("patienceperks")) {
				setActive(true, true, "patienceperks", "one", "two", "three", "four");
				
			}else if(o.contentEquals("kindnessperks")) {
				setActive(true, true, "kindnessperks", "one", "two", "three", "four");
				
			}else if(o.contentEquals("humilityperks")) {
				setActive(true, true, "humilityperks", "one", "two", "three", "four");
				setActive(false, true, "four");
				
			}
			break;
			
			
			
			
		case "battles":
		case "campaign1":
		case "campaign2":
		case "campaign3":
		case "campaign4":
		case "campaign5":
		case "campaign6":
		case "campaign7":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "battles");
			activeScreen = o;
			if(o.contentEquals("battles")) {
				addthese = new String[7];
				addthese[0] ="campaign1";
				if(gameState.isCampaignCleared("campaign1")) addthese[1] ="campaign2";
				if(gameState.isCampaignCleared("campaign2")) addthese[2] ="campaign3";
				if(gameState.isCampaignCleared("campaign3")) addthese[3] ="campaign4";
				if(gameState.isCampaignCleared("campaign4")) addthese[4] ="campaign5";
				if(gameState.isCampaignCleared("campaign5")) addthese[5] ="campaign6";
				if(gameState.isCampaignCleared("campaign6")) addthese[6] ="campaign7";
				setActive(true, true, addthese);
				
			}else {
				setActive(true, true, o, "one", "two", "three");
				Program.print(gameState.getStats(o));
				getCommand("one").setFinerName("First Line");
				getCommand("two").setFinerName("Second Line");
				getCommand("three").setFinerName("Third Line");
			}
			
			break;
			
			
		case "visual novel":
		case "chastity":
		case "temperance":
		case "charity":
		case "diligence":
		case "patience":
		case "kindness":
		case "humility":
		case "mainstory":
			this.setAllInactive(); this.setGUIActive();
			setActive(true, true, "mainstory");
			activeScreen = o;
			setActive(true, true, activeScreen);
			addthese = new String[7];
			if(gameState.getVirtue("chastity").getProgress() != 0) addthese[0] ="chastity";
			if(gameState.getVirtue("temperance").getProgress() != 0) addthese[1] ="temperance";
			if(gameState.getVirtue("charity").getProgress() != 0) addthese[2] ="charity";
			if(gameState.getVirtue("diligence").getProgress() != 0) addthese[3] ="diligence";
			if(gameState.getVirtue("patience").getProgress() != 0) addthese[4] ="patience";
			if(gameState.getVirtue("kindness").getProgress() != 0) addthese[5] ="kindness";
			if(gameState.getVirtue("humility").getProgress() != 0) addthese[6] ="humility";
			setActive(true, true, addthese);
			if(o.contentEquals("chastity")) {
				setActive(true, true, "chastity");
				
			}else if(o.contentEquals("temperance")) {
				setActive(true, true, "temperance");
				
			}else if(o.contentEquals("charity")) {
				setActive(true, true, "charity");
				
			}else if(o.contentEquals("diligence")) {
				setActive(true, true, "diligence");
				
			}else if(o.contentEquals("patience")) {
				setActive(true, true, "patience");
				
			}else if(o.contentEquals("kindness")) {
				setActive(true, true, "kindness");
				
			}else if(o.contentEquals("humility")) {
				setActive(true, true, "humility");
				
			}
			break;
			
		case "addknights":
			l = gameState.getLine(getCommand(o).getFinerName());
			activeScreen = o;
			Program.print(l.stats());
			if(!valGiven) {System.out.print("\nEnter number of knights to add or remove from line: "); returnBoolean = false; break; }
			l.addKnights(val);
			activeScreen = getCommand(o).getFinerName().substring(0, 9);
			Program.print(l.stats());
			break;
		case "addmages":
			activeScreen = o;
			l = gameState.getLine(getCommand(o).getFinerName());
			Program.print(l.stats());
			if(!valGiven) {System.out.print("\nEnter number of mages to add or remove from line: "); returnBoolean = false; break; }
			l.addMages(val);
			activeScreen = getCommand(o).getFinerName().substring(0, 9);
			Program.print(l.stats());
			break;
		case "addphysicians":
			activeScreen = o;
			l = gameState.getLine(getCommand(o).getFinerName());
			Program.print(l.stats());
			if(!valGiven) {System.out.print("\nEnter number of physicians to add or remove from line: "); returnBoolean = false; break; }
			l.addPhysicians(val);
			activeScreen = getCommand(o).getFinerName().substring(0, 9);
			Program.print(l.stats());
			break;
		case "apu1":
			l = gameState.getLine(getCommand(o).getFinerName());
			l.apu1Attack(gameState);
			activeScreen = getCommand(o).getFinerName().substring(0, 9);
			Program.print(l.stats());
			break;
		case "apu2":
			l = gameState.getLine(getCommand(o).getFinerName());
			l.apu2Attack(gameState);
			activeScreen = getCommand(o).getFinerName().substring(0, 9);
			Program.print(l.stats());
			break;
			
		case "one":
		case "two":
		case "three":
			if(activeScreen.contains("campaign")) {
				this.setAllInactive(); this.setGUIActive();
				//there can only be line 1, 2, and 3
				Program.print(gameState.getStats(activeScreen + o));
				addthese = new String[7];
				addthese[0] ="addknights";
				addthese[1] ="addmages";
				addthese[2] ="addphysicians";
				if(gameState.getValue(Modifier.APU1) != 0) addthese[3] ="apu1";
				if(gameState.getValue(Modifier.APU2) != 0) addthese[4] ="apu2";
				addthese[5] = activeScreen;
				addthese[6] = o;
				setActive(true, true, addthese);
				this.getCommand("addknights").setFinerName(activeScreen + o);
				this.getCommand("addmages").setFinerName(activeScreen + o);
				this.getCommand("addphysicians").setFinerName(activeScreen + o);
				this.getCommand("apu1").setFinerName(activeScreen + o);
				this.getCommand("apu2").setFinerName(activeScreen + o);
				break;
			}
		case "four":
		case "five":
		case "six":
		case "seven":
			if(activeScreen.contains("perks")) {
				Program.print("Available perk points: " + (int)gameState.getPerkPoint());
				Command c = this.getCommand(o);
				String temp = c.getFinerName();
				c.setFinerName(activeScreen);
				Perk p = gameState.getPerk(c.getName());
				c.setFinerName(temp);
				if(gameState.addModifiers(p)) {
					c.setFinerName(p.getPrettyName());
					Program.print("Modifier added: \n" + p.getModifierName());
				}
			}
			break;
		
		default:
			try {
				val = Integer.parseInt(order.getName());
				o = activeScreen;
				valGiven = true;
				execute(o, gameState);
			}catch(Exception e) {
				Program.print("That order is invalid: " + order.getName());
			}
			break;
		}
		val = 0;
		valGiven = false;
		gameState.setActiveScreen(activeScreen);
		return returnBoolean;
	}
	
	public void addCommand(Command c) { commands.add(c);}
	
	public void addSubCommand(Command subCommand, String parent) {
		for(Command c : commands) {
			if(c.getName().equals(parent)){
				c.addSubCommand(subCommand);
			}
		}
	}
	
	public void setActive(boolean isActive, boolean onlythis, String... names) {
		for(String name : names) {
			if(name == null) continue;
			for(Command c : commands) {
				if(c.getName(1).equals(name)) {
					if(onlythis) {
						c.setActive(isActive);
					}else {
						c.setActiveWithSub(isActive);
					}
				}
			}
		}
	}
	
	public void setAllInactive() {
		for( Command c : commands) {
			c.setActiveWithSub(false);
		}
	}
	
	public Command getCommand(String name) {
		for(Command c : commands) {
			if(name.equals(c.getName(1))) {
				if(c.getActive()) {
					return c;
				}
			}
		}
		return new Command(name);
	}
	
	public ArrayList<Command> getAllActiveRoots(){
		ArrayList<Command> allAvailable = new ArrayList<>();
		for(Command c : commands) {
			if(c.getActive()) {
				if(c.isRoot()) {
					allAvailable.add(c);
				}
			}
		}
		return allAvailable;
	}
	
	public String getOptions(int level, Command c) {
		StringBuilder s = new StringBuilder();
		s.append("");
		if(c == null && level == 0) {
			s.append("\n");
			ArrayList<Command> allAvailable = getAllActiveRoots();
			allAvailable.add( new Command("options"));
			allAvailable.add( new Command("end"));
			allAvailable.get(allAvailable.size()-1).setFinerName(" and save the game");
			for(int i = 0; i < allAvailable.size(); i++) {
				for(int l = 0; l < level; l++) {
					s.append("\t");
				}
				s.append(i + ")  " + allAvailable.get(i).getName());
				s.append(getOptions(level+1, allAvailable.get(i)));
			}
		}else {
			s.append("\n");
			ArrayList<Command> subs = c.getSubCommands();
			for(int i = 0; i < subs.size(); i++) {
				if(!subs.get(i).getActive()) continue;
				for(int l = 0; l < level; l++) {
					s.append("\t");
				}
				s.append(i + ")  " +subs.get(i).getName() );
				s.append( getOptions(level + 1, subs.get(i)));
			}
		}
		return s.toString();
	}
	
	
	public class Command {

		private String name;
		private String finerName;
		private boolean active;
		private ArrayList<Command> parent;
		private ArrayList<Command> subCommands = new ArrayList<>();
		
		public Command(String name) { 
			this.name = name.toLowerCase();
			active = false;
			parent = new ArrayList<>();
			finerName = null;
		}
		
		public Command(String name, String finerName) { 
			this.name = name.toLowerCase();
			active = false;
			parent = new ArrayList<>();
			this.finerName = finerName;
		}
		
		private void addSubCommand(Command... commands) {
			for(Command c : commands) {
				for(Command s : subCommands) {
					if(c.getName().equals(s.getName())) {
						return;
					}
				}
				c.setParent(this);
				subCommands.add(c);
			}
		}
		
		@SuppressWarnings("unused")
		private void removeSubCommand(String subName) {
			for(int i = 0; i < subCommands.size(); i++) {
				if( subName.equals(subCommands.get(i).getName()) ) {
					subCommands.remove(i);
					return;
				}
			}
		}
		
		private void setActiveWithSub(boolean isActive) {
			active = isActive;
			for(Command c : subCommands) {
				c.setActiveWithSub(isActive);
			}
		}
		
		private void setActive(boolean isActive) {
			active = isActive;
		}
		private ArrayList<Command> getSubCommands(){
			return subCommands;
		}
		
		private void setParent(Command c) { parent.add(c);}
		private void setFinerName(String s) { finerName = s;}
		private String getFinerName() { return finerName;}
		
		public boolean isRoot() { return parent.isEmpty();}
		
		public boolean getActive() { return active;}
		public String getName() { 
			if(finerName == null) {
				return name;
			}
			return "(" + name + ")" + " " + finerName;
		}
		public String getName(int justName) { 
			if(finerName == null || justName == 1) {
				return name;
			}
			if(justName == 2) return finerName;
			return "(" + name + ")" + " " + finerName;
		}
	}

}
