package fr.zimzim.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import fr.zimzim.casestate.CaseBusy;
import fr.zimzim.casestate.CaseEmpty;
import fr.zimzim.meshe.GameItem;
import fr.zimzim.meshe.Puyo;
import fr.zimzim.observer.MyObservable;
import fr.zimzim.sound.SoundEngine;
import fr.zimzim.util.Settings;

public class GameEngine {


	private Map map;
	private boolean[][] hasBeenChecked;
	private List<GameItem> activeItems;
	private List<GameItem> nextFallingItem;
	private List<GameItem> toBeRemoved;
	private Random randomGenerator;
	private MyObservable observable;

	public GameEngine() {
		this.map = new Map(Settings.MAP_HEIGHT, Settings.MAP_WIDTH);
		this.hasBeenChecked = new boolean[Settings.MAP_HEIGHT][Settings.MAP_WIDTH];
		this.activeItems = new ArrayList<GameItem>();
		this.nextFallingItem = new ArrayList<GameItem>();
		this.toBeRemoved = new ArrayList<GameItem>();
		this.randomGenerator = new Random();
		this.observable = new MyObservable();
	}
	
	public void init() {
		this.activeItems.clear();
		this.map.clear();
		for(int i = 0; i<Settings.NB_FALLING_PUYOS; i++) {
			GameItem item = new Puyo(-1,i+2,randomGenerator.nextInt(4));
			nextFallingItem.add(item);
		}
		observable.setChanged();
		observable.notifyObservers(this);
	}
	public boolean addActiveItems() {
		activeItems.addAll(nextFallingItem);
		nextFallingItem.clear();
		for(int i=0; i<Settings.MAP_WIDTH; i++) {
			if(map.getCase(0, i).getState() instanceof CaseBusy) return false;
		}
		for(int i = 0; i<Settings.NB_FALLING_PUYOS; i++) {
			GameItem item = new Puyo(-1,i+2,randomGenerator.nextInt(4));
			nextFallingItem.add(item);
		}
		observable.setChanged();
		observable.notifyObservers(this);
		return true;
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
		observable.setChanged();
		observable.notifyObservers(this);
		return hit;
	}

	public void moveRight() {
		
		GameItem right = activeItems.get(0);
		for(int i=1; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getColumn() > right.getColumn()) right = item;
		}
		if(right.getLine() != -1 && right.getColumn()+1<Settings.MAP_WIDTH){
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
		if(left.getLine() != -1 && left.getColumn()>0) {
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
		boolean delete = false;
		for(int i=0; i<Settings.MAP_HEIGHT; i++) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				if(map.getCase(i, j).getState() instanceof CaseBusy && !hasBeenChecked[i][j]) {
					List<Case> toDelete = getCaseToDelete(map.getCase(i, j), new ArrayList<Case>());
					if(toDelete.size() >= Settings.COMBO_SIZE) {
						delete(toDelete);
						SoundEngine.KICK.play();
						delete = true;
						refreshMap();
					}
				}
			}
		}
		for(int i=0; i<Settings.MAP_HEIGHT; i++) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				hasBeenChecked[i][j] = false;
			}
		}
		return delete;
	}

	private void refreshMap() {
		for(int i=Settings.MAP_HEIGHT-1; i>0; i--) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				Case c = map.getCase(i, j);
				if(c.getState() instanceof CaseBusy) {
					int line = c.getLine();
					while(line+1<Settings.MAP_HEIGHT && map.getCase(line+1, c.getColumn()).getState() instanceof CaseEmpty) {
						line++;
					}
					if(line != c.getLine()) {
						map.getCase(line, c.getColumn()).setState(CaseBusy.getInstance());
						map.getCase(line, c.getColumn()).setItem(c.getItem());
						c.setItem(null);
						c.setState(CaseEmpty.getInstance());
					}
				}
			}
		}

	}

	private void delete(List<Case> toDelete) {
		for(int i=0; i<toDelete.size(); i++) {
			Case c = toDelete.get(i);
			c.setState(CaseEmpty.getInstance());
			c.setItem(null);
		}
	}

	private List<Case> getCaseToDelete(Case c, List<Case> toKick) {
		List<Case> successors = getSuccessors(c, toKick);
		if(!toKick.contains(c)) toKick.add(c);
		if(successors.size() == 0) {
			return toKick;
		}
		else{
			for(int i =0; i<successors.size(); i++) {
				Case succ = successors.get(i);
				if(!toKick.contains(succ)){
					hasBeenChecked[succ.getLine()][succ.getColumn()] = true;
					getCaseToDelete(succ, toKick);
				}
			}
		}
		return toKick;
	}

	private List<Case> getSuccessors(Case c, List<Case> l) {
		List<Case> result = new ArrayList<Case>();
		int line = c.getLine();
		int col = c.getColumn();

		if(line > 0) result.add(map.getCase(line-1, col));
		if(line < Settings.MAP_HEIGHT-1) result.add(map.getCase(line+1, col));
		if(col > 0) result.add(map.getCase(line, col-1));
		if(col < Settings.MAP_WIDTH-1) result.add(map.getCase(line, col+1));

		List<Case> candidates = new ArrayList<Case>();
		for(int i = 0; i<result.size(); i++) {
			Case current = result.get(i);
			if(!l.contains(current)
					&& current.getState() instanceof CaseBusy
					&& current.getItem().getType() == c.getItem().getType()) candidates.add(current);
		}
		return candidates;
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
	
	public List<GameItem> getNextItems(){
		return this.nextFallingItem;
	}
	
	public void addObserver(Observer o) {
		this.observable.addObserver(o);
	}
	
}
