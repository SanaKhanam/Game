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
	private Random randomGenerator;
	private MyObservable observable;

	public GameEngine() {
		this.map = new Map(Settings.MAP_HEIGHT, Settings.MAP_WIDTH);
		this.activeItems = new ArrayList<GameItem>();
		this.nextFallingPuyos = new ArrayList<GameItem>();
		this.randomGenerator = new Random();
	}

	public void addActiveItems() {
		for(int i = 0; i<Settings.NB_FALLING_PUYOS; i++) {
			GameItem item = new Puyo(1,i+2,randomGenerator.nextInt(4)+1);
			activeItems.add(item);
		}
	}

	public boolean fall() {
		boolean wrong = true;
		GameItem tmp = null;
		for(int i=0; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getLine()+1 == Settings.MAP_HEIGHT || map.getCase(item.getLine()+1, item.getColumn()).getState() instanceof CaseBusy ) {
				wrong = false;
				tmp = item;
				activeItems.remove(item);
			}
		}
		if(!wrong) {
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
		else {
			for(int i=0; i<activeItems.size(); i++) {
				activeItems.get(i).setLine(activeItems.get(i).getLine()+1);
			}
		}

		return wrong;
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

	public Dimension getMapDimension() {
		return new Dimension(Settings.MAP_WIDTH,Settings.MAP_HEIGHT);
	}

	public Case getCase(int i, int j) {
		return this.map.getCase(i, j);
	}

	public void rotateRight() {
		// TODO Auto-generated method stub

	}

	public List<GameItem> getActiveItems(){
		return this.activeItems;
	}
}
