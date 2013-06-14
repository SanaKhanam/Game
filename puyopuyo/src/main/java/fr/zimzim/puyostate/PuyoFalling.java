package fr.zimzim.puyostate;

public class PuyoFalling implements PuyoState {
	
	private static PuyoState instance = new PuyoFalling();
	
	public static PuyoState getInstance() {
		return instance;
	}

}
