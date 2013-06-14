package fr.zimzim.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.zimzim.observer.MyObservable;
import fr.zimzim.puyostate.PuyoFalling;
import fr.zimzim.puyostate.PuyoFixed;
import fr.zimzim.ressource.Case;
import fr.zimzim.ressource.Map;
import fr.zimzim.ressource.Puyo;
import fr.zimzim.util.Settings;

public class GameEngine {


	private Map map;
	private List<Puyo> fallingPuyos;
	private List<Puyo> nextFallingPuyos;
	private Random randomGenerator;
	private MyObservable observable;

	public GameEngine() {
		this.map = new Map(Settings.MAP_HEIGHT, Settings.MAP_WIDTH);
		this.fallingPuyos = new ArrayList<Puyo>();
		this.nextFallingPuyos = new ArrayList<Puyo>();
		this.randomGenerator = new Random();
	}

	public void addFallingPuyos() {
		for(int i = 0; i<Settings.NB_FALLING_PUYOS; i++) {
			Puyo puyo = new Puyo(1,i+2,randomGenerator.nextInt(4)+1);
			puyo.setState(PuyoFalling.getInstance());
			fallingPuyos.add(puyo);
			map.getCase(1, i+2).setPuyo(puyo);
		}
	}

	public boolean fall() {
		
//		for(int i=0; i<fallingPuyos.size(); i++) {
//			Puyo puyo = fallingPuyos.get(i);
//			if(puyo.getLine()+1 < Settings.MAP_HEIGHT) {
//				if(!this.fallingPuyos.contains(map.getCase(puyo.getLine()+1, puyo.getColumn()).getPuyo())) {
//				map.getCase(puyo.getLine(), puyo.getColumn()).setPuyo(null);
//				map.getCase(puyo.getLine()+1, puyo.getColumn()).setPuyo(puyo);
//				puyo.setLine(puyo.getLine()+1);
//				}
//			}
//		}
		
		for(int i=0; i<fallingPuyos.size(); i++) {
			Puyo puyo = fallingPuyos.get(i);
			if(puyo.getLine()+1 == Settings.MAP_HEIGHT ||
					(map.getCase(puyo.getLine()+1, puyo.getColumn()).getPuyo() != null)) {
				puyo.setLine(puyo.getLine());
				puyo.setState(PuyoFixed.getInstance());
				map.getCase(puyo.getLine()-1, puyo.getColumn()).setPuyo(null);
				map.getCase(puyo.getLine(), puyo.getColumn()).setPuyo(puyo);
				fallingPuyos.remove(i);
				for(int j=0; j<fallingPuyos.size(); j++) {
					Puyo other = fallingPuyos.get(j);
					int line = other.getLine();
					map.getCase(line, other.getColumn()).setPuyo(null);
					line++;
					while(line < Settings.MAP_HEIGHT && map.getCase(line, other.getColumn()).getPuyo() == null) {
						line++;
						map.getCase(line-1, other.getColumn()).setPuyo(null);
					}
					other.setLine(line-1);
					other.setState(PuyoFixed.getInstance());
					map.getCase(line-1, other.getColumn()).setPuyo(other);
					map.getCase(line-2, other.getColumn()).setPuyo(null);
					fallingPuyos.remove(j);

				}
				return false;
			}
			else {
				map.getCase(puyo.getLine(), puyo.getColumn()).setPuyo(null);
				map.getCase(puyo.getLine()+1, puyo.getColumn()).setPuyo(puyo);
				puyo.setLine(puyo.getLine()+1);
			}
		}
		return true;

	}

	public void moveRight() {
		Puyo right = fallingPuyos.get(0);
		for(int i=1; i<fallingPuyos.size(); i++) {
			Puyo puyo = fallingPuyos.get(i);
			if(puyo.getColumn() > right.getColumn()) right = puyo;
		}
		if(right.getColumn()+1<Settings.MAP_WIDTH) {
			Puyo adjacent = map.getCase(right.getLine(), right.getColumn()+1).getPuyo();
			if(adjacent == null || !(adjacent.getState() instanceof PuyoFixed)) {
				for(int j=0; j< fallingPuyos.size(); j++) {
					Puyo puyo = fallingPuyos.get(j);
					map.getCase(puyo.getLine(), puyo.getColumn()+1).setPuyo(puyo);
					puyo.setColumn(puyo.getColumn()+1);
					map.getCase(puyo.getLine(), puyo.getColumn()-1).setPuyo(null);
				}
			}
		}

	}

	public void moveLeft() {
		Puyo left = fallingPuyos.get(0);
		for(int i=1; i<fallingPuyos.size(); i++) {
			Puyo puyo = fallingPuyos.get(i);
			if(puyo.getColumn() < left.getColumn()) left = puyo;
		}
		if(left.getColumn()-1>=0) {
			Puyo adjacent = map.getCase(left.getLine(), left.getColumn()-1).getPuyo();
			if(adjacent == null || !(adjacent.getState() instanceof PuyoFixed)) {
				for(int j=0; j< fallingPuyos.size(); j++) {
					Puyo puyo = fallingPuyos.get(j);
					map.getCase(puyo.getLine(), puyo.getColumn()-1).setPuyo(puyo);
					puyo.setColumn(puyo.getColumn()-1);
					map.getCase(puyo.getLine(), puyo.getColumn()+1).setPuyo(null);
				}
			}
		}
	}

	public void rotateLeft(){
		if(fallingPuyos.size() > 1) {
			Puyo puyo = fallingPuyos.get(fallingPuyos.size()-1);
			Puyo axe = fallingPuyos.get(0);

			if(axe.getColumn()<puyo.getColumn()){
				map.getCase(axe.getLine()-1, axe.getColumn()).setPuyo(puyo);
				map.getCase(puyo.getLine(), puyo.getColumn()).setPuyo(null);
				puyo.setLine(axe.getLine()-1);
				puyo.setColumn(axe.getColumn());
			}
			else if(axe.getColumn()>puyo.getColumn()){
				map.getCase(axe.getLine()+1, axe.getColumn()).setPuyo(puyo);
				map.getCase(puyo.getLine(), puyo.getColumn()).setPuyo(null);
				puyo.setLine(axe.getLine()+1);
				puyo.setColumn(axe.getColumn());
			}

			else if(axe.getColumn()==puyo.getColumn()){
				if(axe.getLine() > puyo.getLine()) {
					map.getCase(axe.getLine(), axe.getColumn()-1).setPuyo(puyo);
					map.getCase(puyo.getLine(), puyo.getColumn()).setPuyo(null);
					puyo.setLine(axe.getLine());
					puyo.setColumn(axe.getColumn()-1);
				}
				else {
					map.getCase(axe.getLine(), axe.getColumn()+1).setPuyo(puyo);
					map.getCase(puyo.getLine(), puyo.getColumn()).setPuyo(null);
					puyo.setLine(axe.getLine());
					puyo.setColumn(axe.getColumn()+1);
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

	public List<Puyo> getFallers(){
		return this.fallingPuyos;
	}
}
