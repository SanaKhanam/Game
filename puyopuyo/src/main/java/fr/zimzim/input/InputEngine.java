/**
 * Contains key-events management (all inputs from the player)
 */
package fr.zimzim.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.zimzim.game.IGame;
import fr.zimzim.model.GameEngine;
import fr.zimzim.sound.SoundEngine;

/**
 * This class handles all player's actions
 * @author Simon Jambu
 *
 */
public class InputEngine implements KeyListener{
	
	/**
	 * Game engine instance
	 */
	private GameEngine engine;
	
	/**
	 * Boolean used for calling pause() or resume() method
	 * @see IGame#pause()
	 * @see IGame#resume()
	 */
	private boolean pause;
	
	/**
	 * Game instance
	 */
	private IGame game;
	
	/**
	 * Constructor
	 * @param engine: Game engine (logic) instance
	 * @param game: Game instance
	 */
	public InputEngine(GameEngine engine, IGame game) {
		this.engine = engine;
		this.game = game;
	}
	
	/**
	 * Automatically called when a key event occurs. Calls the method linked to a key
	 */
	@Override
	public void keyPressed(KeyEvent input) {
		switch(input.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(!pause)engine.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			if(!pause)engine.moveRight();
			break;
		case KeyEvent.VK_Q:
			if(!pause) {
				SoundEngine.FLIP.play();
				engine.rotateLeft();
			}
			break;
		case KeyEvent.VK_D:
			if(!pause) {
				SoundEngine.FLIP.play();
				engine.rotateRight();
			}
			break;
		case KeyEvent.VK_ESCAPE:
			pause = !pause;
			if(pause) game.pause();
			else game.resume();
			break;
		case KeyEvent.VK_SPACE:
			if(!pause) engine.drop();
			break;
		case KeyEvent.VK_M:
			SoundEngine.AMBIANCE.mute();
			break;
		case KeyEvent.VK_8:
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
