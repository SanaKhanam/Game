package fr.zimzim.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.zimzim.model.GameEngine;

public class InputEngine implements KeyListener{

	private GameEngine engine;

	public InputEngine(GameEngine engine) {
		this.engine = engine;
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
			engine.rotateLeft();
			break;
		case KeyEvent.VK_D:
			engine.rotateRight();
			break;
		case KeyEvent.VK_6:
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
