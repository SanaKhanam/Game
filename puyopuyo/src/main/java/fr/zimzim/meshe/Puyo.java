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
	 * Returns current puyo's line
	 * @return line
	 */
	public int getLine() {
		return line;
	}
	
	/**
	 * Sets puyo's new line
	 * @param x: the new line
	 */
	public void setLine(int x) {
		this.line = x;
	}
	
	/**
	 * Returns current puyo's column
	 * @return col
	 */
	public int getColumn() {
		return col;
	}
	
	/**
	 * Sets new puyo's column
	 * @param y: the new column
	 */
	public void setColumn(int y) {
		this.col = y;
	}
	
	/**
	 * Returns puyo's type
	 * @return type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Sets puyo's type
	 * @param type: the new type
	 */
	public void setType(int type) {
		this.type = type;
	}
}
