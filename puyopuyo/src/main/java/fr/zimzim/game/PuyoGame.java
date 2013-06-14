package fr.zimzim.game;

import fr.zimzim.frame.MainFrame;
import fr.zimzim.input.InputEngine;
import fr.zimzim.model.GameEngine;
import fr.zimzim.render.RenderEngine;

public class PuyoGame implements IGame, Runnable {

	private static final int SLEEP_TIME = 300;
	private GameEngine engine;
	private RenderEngine render;
	private InputEngine input;
	private MainFrame frame;
	private Thread gameThread;
	private boolean isRunning;
	private boolean pause = false;


	@Override
	public void init() {
		this.engine = new GameEngine();
		this.render = new RenderEngine(engine);
		this.input = new InputEngine(engine, this);	
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
		pause = !pause;
	}

	@Override
	public void resume() {
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
			if(!pause) {
				render.repaint();
				sleep(SLEEP_TIME);
				render.repaint();
				if(engine.fall()) {
					engine.addActiveItems();
				}
				else {
					engine.checkMap();
				}
			}
		}

	}

	public void sleep(int time) {
		try {
			gameThread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}




}
