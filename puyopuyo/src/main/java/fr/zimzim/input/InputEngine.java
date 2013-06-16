package fr.zimzim.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.zimzim.game.IGame;
import fr.zimzim.model.GameEngine;
import fr.zimzim.sound.SoundEngine;

public class InputEngine implements KeyListener{

	private GameEngine engine;
	private IGame game;

	public InputEngine(GameEngine engine, IGame game) {
		this.engine = engine;
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent input) {
		switch(input.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			engine.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			engine.moveRight();
			
			break;
		case KeyEvent.VK_Q:
			SoundEngine.FLIP.play();
			engine.rotateLeft();
			break;
		case KeyEvent.VK_D:
			SoundEngine.FLIP.play();
			engine.rotateRight();
			break;
		case KeyEvent.VK_ESCAPE:
			game.pause();
			break;
		case KeyEvent.VK_SPACE:
			engine.drop();
			break;
		case KeyEvent.VK_M:
			break;
		case KeyEvent.VK_8:
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


}
