package fr.zimzim.game;

import fr.zimzim.frame.MainFrame;
import fr.zimzim.input.InputEngine;
import fr.zimzim.model.GameEngine;
import fr.zimzim.render.RenderEngine;

public class PuyoGame implements IGame, Runnable {
	
	private GameEngine engine;
	private RenderEngine render;
	private InputEngine input;
	private MainFrame frame;
	private Thread gameThread;
	private boolean isRunning;


	@Override
	public void init() {
		this.engine = new GameEngine();
		this.render = new RenderEngine(engine);
		this.input = new InputEngine(engine);	
		this.frame = new MainFrame(render);
		
		frame.addKeyListener(input);
		
		
	}

	@Override
	public void start() {
		frame.setVisible(true);
		isRunning = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void pause() {
		try {
			gameThread.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void resume() {
		gameThread.notify();
	}

	@Override
	public void stop() {
		this.isRunning = false;
		
	}

	@Override
	public void exit() {
		this.frame.dispose();
		System.exit(0);
		
	}

	@Override
	public void run() {
		engine.addActiveItems();
		while(isRunning) {
			render.repaint();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			render.repaint();
			if(!engine.fall()) {
				engine.addActiveItems();
			}
			
		}
		
	}
	

	

}
