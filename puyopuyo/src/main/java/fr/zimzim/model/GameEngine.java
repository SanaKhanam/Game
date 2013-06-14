package fr.zimzim.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.zimzim.casestate.CaseBusy;
import fr.zimzim.casestate.CaseEmpty;
import fr.zimzim.meshe.GameItem;
import fr.zimzim.meshe.Puyo;
import fr.zimzim.observer.MyObservable;
import fr.zimzim.util.Settings;

public class GameEngine {


	private Map map;
	private List<GameItem> activeItems;
	private List<GameItem> nextFallingPuyos;
	private List<GameItem> toBeRemoved;
	private Random randomGenerator;
	private MyObservable observable;

	public GameEngine() {
		this.map = new Map(Settings.MAP_HEIGHT, Settings.MAP_WIDTH);
		this.activeItems = new ArrayList<GameItem>();
		this.nextFallingPuyos = new ArrayList<GameItem>();
		this.toBeRemoved = new ArrayList<GameItem>();
		this.randomGenerator = new Random();
	}

	public void addActiveItems() {
		for(int i = 0; i<Settings.NB_FALLING_PUYOS; i++) {
			//GameItem item = new Puyo(0,i+2,3);
			GameItem item = new Puyo(0,i+2,randomGenerator.nextInt(4)+1);
			activeItems.add(item);
		}
	}

	public boolean fall() {
		boolean hit = false;
		GameItem tmp = null;
		for(int i=0; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getLine()+1 == Settings.MAP_HEIGHT || map.getCase(item.getLine()+1, item.getColumn()).getState() instanceof CaseBusy ) {
				hit = true;
				tmp = item;
				activeItems.remove(item);
			}
		}
		if(!hit) {
			for(int i=0; i<activeItems.size(); i++) {
				activeItems.get(i).setLine(activeItems.get(i).getLine()+1);
			}
		}
		else {
			map.getCase(tmp.getLine(), tmp.getColumn()).setState(CaseBusy.getInstance());
			map.getCase(tmp.getLine(), tmp.getColumn()).setItem(tmp);
			for(int j=0; j<activeItems.size(); j++) {
				GameItem other = activeItems.get(j);
				int line = other.getLine();
				
				while(line+1 < Settings.MAP_HEIGHT && map.getCase(line+1, other.getColumn()).getState() instanceof CaseEmpty) {
					line++;
				}
				other.setLine(line);
				map.getCase(other.getLine(), other.getColumn()).setState(CaseBusy.getInstance());
				map.getCase(other.getLine(), other.getColumn()).setItem(other);
				activeItems.remove(other);
			}
		}
		return hit;
	}

	public void moveRight() {
		GameItem right = activeItems.get(0);
		for(int i=1; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getColumn() > right.getColumn()) right = item;
		}
		if(right.getColumn()+1<Settings.MAP_WIDTH) {
			Case adjacent = map.getCase(right.getLine(), right.getColumn()+1);
			if(adjacent.getState() instanceof CaseEmpty) {
				for(int j=0; j< activeItems.size(); j++) {
					GameItem item = activeItems.get(j);
					item.setColumn(item.getColumn()+1);
				}
			}
		}

	}

	public void moveLeft() {
		GameItem left = activeItems.get(0);
		for(int i=1; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getColumn() < left.getColumn()) left = item;
		}
		if(left.getColumn()-1>=0) {
			Case adjacent = map.getCase(left.getLine(), left.getColumn()-1);
			if(adjacent.getState() instanceof CaseEmpty) {
				for(int j=0; j< activeItems.size(); j++) {
					GameItem item = activeItems.get(j);
					item.setColumn(item.getColumn()-1);
				}
			}
		}
	}

	public void rotateLeft(){
		if(activeItems.size() > 1) {
			GameItem item = activeItems.get(activeItems.size()-1);
			GameItem axe = activeItems.get(0);
			
			// xy
			//y
			//x
			if(axe.getColumn()<item.getColumn() && axe.getLine() > 0){
				item.setLine(axe.getLine()-1);
				item.setColumn(axe.getColumn());
			}
			// yx
			//x
			//y
			else if(axe.getColumn()>item.getColumn()
					&& axe.getLine() < Settings.MAP_HEIGHT-1
					&& map.getCase(axe.getLine()+1, axe.getColumn()).getState() instanceof CaseEmpty){
				item.setLine(axe.getLine()+1);
				item.setColumn(axe.getColumn());
			}

			else if(axe.getColumn()==item.getColumn()){
				//y
				//x
				//yx
				if(axe.getLine() > item.getLine()
						&& axe.getColumn() > 0
						&& map.getCase(axe.getLine(), axe.getColumn()-1).getState() instanceof CaseEmpty) {
					item.setLine(axe.getLine());
					item.setColumn(axe.getColumn()-1);
				}
				//x
				//y
				//xy
				else if(axe.getLine() < item.getLine()
						&& axe.getColumn() < Settings.MAP_WIDTH-1
						&& map.getCase(axe.getLine(), axe.getColumn()+1).getState() instanceof CaseEmpty){
					item.setLine(axe.getLine());
					item.setColumn(axe.getColumn()+1);
				}
			}
		}
		
	}
	
	public void rotateRight() {
		if(activeItems.size() > 1) {
			GameItem item = activeItems.get(activeItems.size()-1);
			GameItem axe = activeItems.get(0);
			
			// xy
			//x
			//y
			if(axe.getColumn()<item.getColumn()
					&& axe.getLine() < Settings.MAP_HEIGHT-1
					&& map.getCase(axe.getLine()+1, axe.getColumn()).getState() instanceof CaseEmpty){
				item.setLine(axe.getLine()+1);
				item.setColumn(axe.getColumn());
			}
			// yx
			//y
			//x
			else if(axe.getColumn()>item.getColumn()
					&& axe.getLine() > 0){
				item.setLine(axe.getLine()-1);
				item.setColumn(axe.getColumn());
			}

			else if(axe.getColumn()==item.getColumn()){
				//y
				//x
				//xy
				if(axe.getLine() > item.getLine()
						&& axe.getColumn() < Settings.MAP_WIDTH-1
						&& map.getCase(axe.getLine(), axe.getColumn()+1).getState() instanceof CaseEmpty) {
					item.setLine(axe.getLine());
					item.setColumn(axe.getColumn()+1);
				}
				//x
				//y
				//yx
				else if(axe.getLine() < item.getLine()
						&& axe.getColumn() > 0
						&& map.getCase(axe.getLine(), axe.getColumn()-1).getState() instanceof CaseEmpty){
					item.setLine(axe.getLine());
					item.setColumn(axe.getColumn()-1);
				}
			}
		}

	}
	
	public boolean checkMap() {
		System.out.println("////Check");
		for(int i=0; i<Settings.MAP_HEIGHT; i++) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				if(map.getCase(i, j).getState() instanceof CaseBusy) {
					System.out.println(" studiying globally i "+i+"j "+j);
					//System.out.println(map.getCase(i, j).getItem().getType());
					List<Case> toDelete = getCaseToDelete(map.getCase(i, j), new ArrayList<Case>());
					if(toDelete.size() >= Settings.COMBO_SIZE) {
						delete(toDelete);
						refreshMap();
					}
				}
			}
		}
		return true;
	}
	
	private void refreshMap() {
		for(int i=0; i<Settings.MAP_HEIGHT; i++) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				Case c = map.getCase(i, j);
				//TODO
			}
		}
		
	}

	private void delete(List<Case> toDelete) {
		System.out.println("/////////////Remove Ya");
		for(int i=0; i<toDelete.size(); i++) {
			Case c = toDelete.get(i);
			System.out.println(c.getLine()+" "+c.getColumn());
			c.setState(CaseEmpty.getInstance());
			c.setItem(null);
		}
//		if(toDelete.size() == 0) {
//			c.setState(CaseEmpty.getInstance());
//			c.setItem(null);
//		}
//		else {
//			c.setState(CaseEmpty.getInstance());
//			c.setItem(null);
//			toDelete.remove(c);
//			delete(toDelete.get(0), toDelete);
//		}
	}

	private List<Case> getCaseToDelete(Case c, List<Case> l) {
		List<Case> successors = getSuccessors(c);
		System.out.println("current "+c.getLine()+" "+c.getColumn());
		System.out.println("succ before resized "+successors.size());
		List<Case> candidates = new ArrayList<Case>();
		for(int i = 0; i<successors.size(); i++) {
			Case current = successors.get(i);
			if(!l.contains(current)
					&& current.getState() instanceof CaseBusy
					&& current.getItem().getType() == c.getItem().getType()) candidates.add(current);
//			if(!l.contains(current)
//					&& !(current.getState() instanceof CaseEmpty)
//					&& current.getItem().getType() == c.getItem().getType()) {
//				successors.remove(current);
//				System.out.println(current.getLine()+" "+current.getColumn()+" is removed");
//			}
				
				
		}
		if(candidates.size() == 0 && l.size() >= Settings.COMBO_SIZE-1) {
			l.add(c);
			return l;
		}
		System.out.println("candidates size = "+candidates.size());
		for(int i = 0; i<candidates.size(); i++) {
			System.out.println("new i"+candidates.get(i).getLine()+" new j"+candidates.get(i).getColumn());
		}
		if(candidates.size() > 0) {
			for(int i =0; i<candidates.size(); i++) {
				Case succ = candidates.get(i);
				l.add(c);
				getCaseToDelete(succ, l);
			}
			return l;
		}
		else return l;
	}

	private List<Case> getSuccessors(Case c) {
		List<Case> result = new ArrayList<Case>();
		int line = c.getLine();
		int col = c.getColumn();
		
		if(line > 0) result.add(map.getCase(line-1, col));
		if(line < Settings.MAP_HEIGHT-1) result.add(map.getCase(line+1, col));
		if(col > 0) result.add(map.getCase(line, col-1));
		if(col < Settings.MAP_WIDTH-1) result.add(map.getCase(line, col+1));
		return result;
	}

	public Dimension getMapDimension() {
		return new Dimension(Settings.MAP_WIDTH,Settings.MAP_HEIGHT);
	}

	public Case getCase(int i, int j) {
		return this.map.getCase(i, j);
	}

	public List<GameItem> getActiveItems(){
		return this.activeItems;
	}
}
