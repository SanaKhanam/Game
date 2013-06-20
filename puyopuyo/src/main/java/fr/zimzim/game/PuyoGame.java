package fr.zimzim.game;


import fr.zimzim.frame.MainFrame;
import fr.zimzim.input.InputEngine;
import fr.zimzim.model.GameEngine;
import fr.zimzim.render.GraphicEngine;
import fr.zimzim.sound.SoundEngine;
import fr.zimzim.util.Settings;

/**
 * Game controler, holds all engines and define the main loop of the game
 * 
 * @author Simon Jambu
 *
 */
public class PuyoGame implements IGame, Runnable {

	/**
	 * Difficulty range that represents the percentage of difficulty to add
	 * @see PuyoGame#increaseDifficulty()
	 */
	private static int DIFFICULTY_RANGE = 5;

	/**
	 * Thread waiting delay before adding new Puyos on the map
	 * Decreases if difficulty increase
	 * @see PuyoGame#sleep(int)
	 */
	private int delay;

	/**
	 * Instance of the GraphicEngine (holds all graphic subcomponents)
	 */
	private GraphicEngine graphicEngine;

	/**
	 * Instance of the GameEngine (holds all the logic of the game)
	 */
	private GameEngine engine;

	/**
	 * Instance of the InputEngine (handle all key events)
	 */
	private InputEngine input;

	/**
	 * Instance of the main frame of the game (holds graphic components)
	 */
	private MainFrame frame;

	/**
	 * Game thread
	 * @see PuyoGame#run()
	 */
	private Thread gameThread;

	/**
	 * End condition of the game loop
	 */
	private boolean isRunning;

	/**
	 * The game loop does nothing if <code>pause</code> is true (the frame freezes)
	 */
	private boolean pause = false;


	/**
	 * Initialization of the game components
	 */
	@Override
	public void init() {
		this.engine = new GameEngine();
		this.input = new InputEngine(this.engine, this);	
		this.graphicEngine = new GraphicEngine(this.engine);
		this.engine.addObserver(this.graphicEngine);
		this.engine.addObserver(this.input);
		this.frame = new MainFrame(this.graphicEngine);
		this.delay = Settings.INITIAL_DELAY;

		this.frame.addKeyListener(input);
	}

	/**
	 * Starts the game (create a new Thread)
	 */
	@Override
	public void start() {
		this.frame.setVisible(true);
		this.isRunning = true;
		this.engine.init();
		SoundEngine.volume = SoundEngine.Volume.LOW;
		SoundEngine.AMBIANCE.play();
		SoundEngine.AMBIANCE.setInfiniteLoop();
		this.gameThread = new Thread(this);
		this.gameThread.start();
	}

	/**
	 * Pause the game
	 */
	@Override
	public void pause() {
		pause = !pause;
		SoundEngine.AMBIANCE.pause();
		SoundEngine.PAUSE.play();

	}

	/**
	 * Instructions when the game resumes
	 */
	@Override
	public void resume() {
		SoundEngine.PAUSE.play();
		SoundEngine.AMBIANCE.pause();
		pause = !pause;
	}

	/**
	 * Stops the game loop and causes game thread termination
	 */
	@Override
	public void stop() {
		this.isRunning = false;

	}

	/**
	 * Kills the main frame and stops the program
	 */
	@Override
	public void exit() {
		this.frame.dispose();
		System.exit(0);

	}

	/**
	 * Game loop
	 */
	@Override
	public void run() {
		engine.addActiveItems();
		while(isRunning) {
			//System.out.println("Game paused "+pause);
			if(!pause) {
				sleep(delay);
				if(engine.fall()) {
					if(engine.checkMap()) increaseDifficulty();
					if(!engine.addActiveItems()){
						gameOver();
					}
				}
			}
		}
		exit();
	}

	/**
	 * Increases difficulty of the game (lower thread delay)
	 */
	private void increaseDifficulty() {
		int value = (this.delay*DIFFICULTY_RANGE)/100;
		this.delay = this.delay-value;

	}

	/**
	 * Make the game sleep before adding new items on the map
	 * @param time
	 * 			Sleeping time
	 */
	@SuppressWarnings("static-access")
	public void sleep(int time) {
		try {
			gameThread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls the game-over window and asks the player to replay
	 * @see MainFrame#endGame(int)
	 */
	public void gameOver() {
		SoundEngine.AMBIANCE.pause();
		SoundEngine.FINISH.play();
		boolean replay = this.frame.endGame(engine.getScore());

		if(!replay) stop();
		else {
			this.engine.init();
			this.graphicEngine.clear();
			SoundEngine.AMBIANCE.play();
			SoundEngine.AMBIANCE.setInfiniteLoop();
			this.engine.addActiveItems();
			this.delay = Settings.INITIAL_DELAY;
		}
	}





}
