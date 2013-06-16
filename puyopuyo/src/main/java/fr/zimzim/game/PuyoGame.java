package fr.zimzim.game;

import javax.swing.JOptionPane;

import fr.zimzim.frame.MainFrame;
import fr.zimzim.input.InputEngine;
import fr.zimzim.model.GameEngine;
import fr.zimzim.render.NextItemDisplayer;
import fr.zimzim.render.RenderEngine;
import fr.zimzim.render.ScoreDisplayer;
import fr.zimzim.sound.SoundEngine;

public class PuyoGame implements IGame, Runnable {
	
	private static int DIFFICULTY_RANGE = 10;
	private int delay;
	private GameEngine engine;
	private RenderEngine render;
	private InputEngine input;
	private MainFrame frame;
	private NextItemDisplayer itemDisplayer;
	private ScoreDisplayer scoreDisplayer;
	private Thread gameThread;
	private boolean isRunning;
	private boolean pause = false;


	@Override
	public void init() {
		this.engine = new GameEngine();
		this.render = new RenderEngine(engine);
		this.input = new InputEngine(engine, this);	
		this.itemDisplayer = new NextItemDisplayer();
		this.scoreDisplayer = new ScoreDisplayer();
		this.engine.addObserver(itemDisplayer);
		this.engine.addObserver(render);
		this.engine.addObserver(scoreDisplayer);
		this.frame = new MainFrame(render, itemDisplayer, scoreDisplayer);
		

		frame.addKeyListener(input);


	}

	@Override
	public void start() {
		this.frame.setVisible(true);
		this.isRunning = true;
		this.delay = 300;
		this.engine.init();
		SoundEngine.volume = SoundEngine.Volume.LOW;
		//SoundEngine.AMBIANCE.play();
		//SoundEngine.AMBIANCE.setInfiniteLoop();
		this.gameThread = new Thread(this);
		this.gameThread.start();
	}

	@Override
	public void pause() {
		pause = !pause;
		if(pause) {
			SoundEngine.AMBIANCE.pause();
			SoundEngine.PAUSE.play();
		}
		else {
			SoundEngine.PAUSE.play();
			SoundEngine.AMBIANCE.pause();
			
		}
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
				//render.repaint();
				sleep(delay);
				//render.repaint();
				if(engine.fall()) {
					if(engine.checkMap()) increaseDifficulty();
					if(!engine.addActiveItems()){
						//SoundEngine.AMBIANCE.pause();
						SoundEngine.FINISH.play();
						boolean replay = this.frame.endGame(engine.getScore());
						
						if(!replay) stop();
						else {
							this.engine.init();
							this.scoreDisplayer.clear();
							this.engine.addActiveItems();
						}
					}
					
					
				}
			}
		}
		exit();

	}
	
	private void increaseDifficulty() {
		int value = (this.delay*DIFFICULTY_RANGE)/100;
		this.delay = this.delay-value;
		// TODO Auto-generated method stub
		
	}

	public void sleep(int time) {
		try {
			gameThread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}





}
