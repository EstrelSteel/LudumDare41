package com.estrelsteel.ld41.ai;

import java.util.ArrayList;
import java.util.HashMap;

import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.AbstractedRectangle;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.ld41.actors.Ball;
import com.estrelsteel.ld41.actors.Mine;

public class AI {
	
	private HashMap<Integer, TargetRegion> targets;
	private Difficulty d;
	private int mines;
	private int totalMines;
	
	public AI(Difficulty d) {
		this.targets = new HashMap<Integer, TargetRegion>();
		this.d = d;
		mines = 0;
		loadTargetRegions();
	}
	
	public AI loadTargetRegions() {
		targets.put(0, new TargetRegion(0, QuickRectangle.location(840, 210, 40, 180), AIPriority.HIGH));
		targets.put(1, new TargetRegion(1, QuickRectangle.location(640, 160, 200, 280), AIPriority.MEDIUM));
		targets.put(2, new TargetRegion(2, QuickRectangle.location(480, 80, 380, 100), AIPriority.LOW));
		targets.put(3, new TargetRegion(3, QuickRectangle.location(480, 42, 380, 100), AIPriority.LOW));
		return this;
	}
	
	private AIPriority cyclePriority(AIPriority p) {
		if(p == AIPriority.HIGH) {
			return AIPriority.MEDIUM;
		}
		else if(p == AIPriority.MEDIUM) {
			return AIPriority.LOW;
		}
		return AIPriority.HIGH;
	}
	
	private Mine getNewMine(AbstractedRectangle loc, int aiLoc) {
		mines++;
		Mine m = new Mine("AIMine" + totalMines, QuickRectangle.location(loc.getX() + loc.getWidth() * Math.random() - 32, loc.getY() + loc.getHeight() * Math.random() - 32, 64, 64));
		m.setAILocation(aiLoc);
		m.setRunningAnimationNumber(1);
		return m;
	}
	
	public ArrayList<Renderable> runAI(ArrayList<Renderable> fieldMines, Ball b) {
		ArrayList<Renderable> newMines = new ArrayList<Renderable>();
		int[] placedMines = new int[targets.size()];
		mines = 0;
		placedMines[0] = 0;
		placedMines[1] = 0;
		placedMines[2] = 0;
		placedMines[3] = 0;
		for(int i = 0; i < fieldMines.size(); i++) {
			if(((Mine) fieldMines.get(i)).getAILocation() >= 0) {
				placedMines[((Mine) fieldMines.get(i)).getAILocation()]++;
				mines++;
			}
		}
		AIPriority p = AIPriority.HIGH;
		int c = 0;
		int t;
		int nm = 0;
		System.out.println(placedMines[0] + "\t" + placedMines[1] + "\t" + placedMines[2] + "\t" + placedMines[3]);
		if((int) (Math.random() * 2) == 0) {
			newMines.add(getNewMine(QuickRectangle.location(b.getLocation().getX() + 48, b.getLocation().getY(), b.getLocation().getWidth(), b.getLocation().getHeight()), -2));
			nm++;
		}
		while(mines < d.getMaxMines() && nm < d.getMinesPerRound()) { //TODO: REAL SKETCHY HOT CODE
			if(placedMines[0] - (placedMines[1] + placedMines[2] + placedMines[3]) > 2 && p == AIPriority.HIGH) {
				p = cyclePriority(p);
				c = 0;
			}
			if(p == AIPriority.HIGH) {
				if(c < 2) {
					c++;
					t = 0;
					newMines.add(getNewMine(targets.get(t).getLocation(), t));
					placedMines[t]++;
					nm++;
				}
				else {
					p = cyclePriority(p);
					c = 0;
				}
			}
			if(p == AIPriority.MEDIUM) {
				if(c < 1) {
					c++;
					t = 1;
					newMines.add(getNewMine(targets.get(t).getLocation(), t));
					placedMines[t]++;
					nm++;
				}
				else {
					p = cyclePriority(p);
					c = 0;
				}
			}
			if(p == AIPriority.LOW) {
				if(c < 1) {
					c++;
					t = (int) (Math.random() * 2 + 2);
					newMines.add(getNewMine(targets.get(t).getLocation(), t));
					placedMines[t]++;
					nm++;
				}
				else {
					p = cyclePriority(p);
					c = 0;
				}
			}
		}
		return newMines;
	}

	public HashMap<Integer, TargetRegion> getTargetRegions() {
		return targets;
	}

	public Difficulty getDifficulty() {
		return d;
	}

	public int getMines() {
		return mines;
	}

	public void setTargetRegions(HashMap<Integer, TargetRegion> targets) {
		this.targets = targets;
	}

	public void setDifficulty(Difficulty d) {
		this.d = d;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}
}
