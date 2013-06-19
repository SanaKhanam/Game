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
	
	public int getLine();
	public void setLine(int line);
	public int getColumn();
	public void setColumn(int column);
	public int getType();
	public void setType(int type);

}
