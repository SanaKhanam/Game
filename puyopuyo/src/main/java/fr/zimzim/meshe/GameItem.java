/**
 * Contains all elements manipulated in the game (items like puyos)
 */
package fr.zimzim.meshe;

/**
 * Defines all operations that a Game item (Puyos and other possible items) must implement
 * @author Simon Jambu
 *
 */
public interface GameItem {
	
	/**
	 * Returns current item's line
	 * @return
	 */
	public int getLine();
	
	/**
	 * Sets item's new line
	 * @param line
	 */
	public void setLine(int line);
	
	/**
	 * Returns current item's column
	 * @return
	 */
	public int getColumn();
	
	/**
	 * Sets item's new column
	 * @param column
	 */
	public void setColumn(int column);
	
	/**
	 * Returns current item's type
	 * @return
	 */
	public int getType();
	
	/**
	 * Sets item's type
	 * @param type
	 */
	public void setType(int type);

}
