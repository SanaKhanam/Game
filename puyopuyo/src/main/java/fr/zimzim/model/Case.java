package fr.zimzim.model;

import fr.zimzim.casestate.CaseEmpty;
import fr.zimzim.casestate.CaseState;
import fr.zimzim.meshe.GameItem;


public class Case {
	
	private GameItem item;
	private CaseState state;
	private int posX;
	private int posY;
	
	public Case(GameItem item, int posX, int posY) {
		this.item = item;
		this.posX = posX;
		this.posY = posY;
		this.state = CaseEmpty.getInstance();
	}
	
	
	public CaseState getState() {
		return state;
	}
	public void setState(CaseState state) {
		this.state = state;
	}
	public GameItem getItem() {
		return this.item;
	}
	public void setItem(GameItem item) {
		this.item = item;
	}
	public int getPosX() {
		return this.posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return this.posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}

}
