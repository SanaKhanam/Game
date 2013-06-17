package fr.zimzim.game;

/**
 * Define operations that all game must implement.
 * @author Simon Jambu
 *
 */
public interface IGame {
	
	public void init();
	public void start();
	public void pause();
	public void resume();
	public void stop();
	public void exit();
}
