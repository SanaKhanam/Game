package fr.zimzim.main;

import fr.zimzim.game.IGame;
import fr.zimzim.game.PuyoGame;

/**
 * 
 * @author Simon Jambu
 *	Game Launcher
 */
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
