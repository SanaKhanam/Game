package fr.zimzim.puyostate;

public class PuyoFixed implements PuyoState {
	
	private static PuyoState instance = new PuyoFixed();
	
	public static PuyoState getInstance() {
		return instance;
	}
}
