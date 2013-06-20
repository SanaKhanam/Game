/**
 * Contains the Game class.
 */
package fr.zimzim.game;

/**
 * Define operations that all game must implement.
 * @author Simon Jambu
 *
 */
public interface IGame {
	
	/**
	 * Operations at initialization phase
	 */
	public void init();
	
	/**
	 * Operations when the game starts
	 */
	public void start();
	
	/**
	 * Operations to do when the game is paused
	 */
	public void pause();
	
	/**
	 * Operations to do when the game resumes
	 */
	public void resume();
	
	/**
	 * Operations to do when the game is stopped
	 */
	public void stop();
	
	/**
	 * Operations to shutdown the game
	 */
	public void exit();
}
