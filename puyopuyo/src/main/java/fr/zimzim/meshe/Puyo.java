package fr.zimzim.meshe;

/**
 * Class that implements all game item operations
 * @author Simon Jambu
 *
 */
public class Puyo implements GameItem{
	
	/**
	 * Current line (on the main game map) of a Puyo
	 */
	private int line;
	
	/**
	 * Current column of a Puyo
	 */
	private int col;
	
	/**
	 * Puyo's type (can be one of the 4 possible Puyo's type)
	 * @see Settings
	 */
	private int type;
	
	/**
	 * Constructor of a Puyo
	 * @param x: current line
	 * @param y: current column
	 * @param type: current type
	 */
	public Puyo(int x, int y, int type) {
		this.line = x;
		this.col = y;
		this.type = type;
	}
	
	/**
	 * @return line: Current line of this Puyo
	 */
	public int getLine() {
		return line;
	}
	
	/**
	 * Sets the new line of this Puyo
	 * @param x: the new line
	 */
	public void setLine(int x) {
		this.line = x;
	}
	
	/**
	 * @return col: the current column of this Puyo
	 */
	public int getColumn() {
		return col;
	}
	
	/**
	 * Sets the new column of this Puyo
	 * @param y: the new column
	 */
	public void setColumn(int y) {
		this.col = y;
	}
	
	/**
	 * @return type: the current type of this Puyo
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Sets the type of this Puyo
	 * @param type: the new type
	 */
	public void setType(int type) {
		this.type = type;
	}
}
