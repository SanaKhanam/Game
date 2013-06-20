/**
 * Contains key-events management (all inputs from the player)
 */
package fr.zimzim.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import fr.zimzim.game.IGame;
import fr.zimzim.model.GameEngine;
import fr.zimzim.sound.SoundEngine;
import fr.zimzim.util.Settings;

/**
 * This class handles all player's actions
 * @author Simon Jambu
 *
 */
public class InputEngine implements KeyListener, Observer{
	
	/**
	 * Game engine instance
	 */
	private GameEngine engine;
	
	/**
	 * Boolean used to call pause() or resume() method and avoid user actions while game is paused
	 * @see IGame#pause()
	 * @see IGame#resume()
	 */
	private boolean pause;
	
	/**
	 * Game instance
	 */
	private IGame game;
	
	/**
	 * Input locker
	 */
	private boolean lock;
	
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
	 * Automatically called when a key event occurs. Calls the method linked to a key if the game is not paused
	 */
	@Override
	public void keyPressed(KeyEvent input) {
		switch(input.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(!pause && !lock){
				this.lock = true;
				engine.moveLeft();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(!pause && !lock){
				this.lock = true;
				engine.moveRight();
			}
			break;
		case KeyEvent.VK_A:
			if(!pause) {
				SoundEngine.FLIP.play();
				engine.rotateLeft();
			}
			break;
		case KeyEvent.VK_D:
			if(!pause && !lock) {
				this.lock = true;
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
			if(!pause && !lock){
				this.lock = true;
				engine.drop();
			}
			break;
		case KeyEvent.VK_M:
			SoundEngine.AMBIANCE.mute(Settings.AMBIANCE_URL);
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.lock = false;
	}
}
