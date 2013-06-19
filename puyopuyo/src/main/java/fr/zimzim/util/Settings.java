/**
 * Contains useful informations and data structures
 */
package fr.zimzim.util;

/**
 * Defines all useful game settings
 * @author Simon Jambu
 *
 */
public class Settings {
	
	/**
	 * Game map height
	 */
	public static final int MAP_HEIGHT = 12;
	/**
	 * Game map width
	 */
	public static final int MAP_WIDTH = 6;
	/**
	 * Green puyo type
	 */
	public static final int PUYO_GREEN = 0;
	/**
	 * Yellow puyo type
	 */
	public static final int PUYO_YELLOW = 1;
	/**
	 * Red puyo type
	 */
	public static final int PUYO_RED = 2;
	/**
	 * Blue puyo type
	 */
	public static final int PUYO_BLUE = 3;
	/**
	 * Number of active puyos droped from top
	 */
	public static final int NB_FALLING_PUYOS = 2;
	/**
	 * Size of a an item image
	 */
	public static final int TAILLE_PIXELS = 32;
	/**
	 * Number of linked puyos to remove them from game map
	 */
	public static final int COMBO_SIZE = 4;
	/**
	 * Top rim size of the frame image
	 */
	public static final int TOP_RIM_CADRE = 20;
	/**
	 * Left (and right) rim size of the frame image
	 */
	public static final int LEFT_RIM_CADRE = 15;
	/**
	 * Game thread delay before proceeding to another step forward
	 */
	public static final int INITIAL_DELAY = 300;
	/**
	 * Green puyo image url
	 */
	public static final String IMG_PUYO_GREEN = "/img/p_green.png";
	/**
	 * Red puyo image url
	 */
	public static final String IMG_PUYO_RED = "/img/p_red.png";
	/**
	 * Blue puyo image url
	 */
	public static final String IMG_PUYO_BLUE = "/img/p_blue.png";
	/**
	 * Yellow puyo image url
	 */
	public static final String IMG_PUYO_YELLOW = "/img/p_yellow.png";
	/**
	 * Main frame image url
	 */
	public static final String IMG_CADRE = "/img/cadre.png";
	/**
	 * Small frame image
	 */
	public static final String IMG_SMALL_CADRE = "/img/cadre3.png";
	/**
	 * Ambiance sound
	 */
	public static final String AMBIANCE_URL = "/sounds/ambiance.wav";
	/**
	 * Pause sound
	 */
	public static final String PAUSE_URL = "/sounds/pause.wav";
	/**
	 * Puyo rotation sound
	 */
	public static final String FLIP_URL = "/sounds/flip.wav";
	/**
	 * Puyo removed sound
	 */
	public static final String KICK_URL = "/sounds/kick.wav";
	/**
	 * Game over sound
	 */
	public static final String FINISH_URL = "/sounds/finish.wav";
}
