package fr.zimzim.main;

import fr.zimzim.game.IGame;
import fr.zimzim.game.PuyoGame;

public class GameLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IGame game = new PuyoGame();
		game.init();
		game.start();
	}

}
