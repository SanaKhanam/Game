package fr.zimzim.model;

import fr.zimzim.casestate.CaseEmpty;
import fr.zimzim.casestate.CaseState;
import fr.zimzim.meshe.GameItem;

/**
 * Represents an element of the game map. Holds a unique game item
 * @author Simon Jambu
 *
 */
public class Case {
	
	private GameItem item;
	private CaseState state;
	private int line;
	private int col;
	
	public Case(GameItem item, int line, int col) {
		this.item = item;
		this.line = line;
		this.col = col;
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
	public int getLine() {
		return this.line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getColumn() {
		return this.col;
	}
	public void setColumn(int col) {
		this.col = col;
	}

}
