package fr.zimzim.model;

import fr.zimzim.casestate.CaseEmpty;

/**
 * This class represents the game map. Holds 'Case' which are data objects of the map
 * @see Case
 * @author Simon Jambu
 *
 */
public class Map {
	
	/**
	 * Height of the map
	 */
	private int height;
	
	/**
	 * Width of the map
	 */
	private int width;
	
	/**
	 * Current CaseMap
	 */
	private Case[][] map;
	
	/**
	 * Map constructor
	 * @param height: height of the map
	 * @param width: width of the map
	 */
	public Map(int height, int width) {
		this.height = height;
		this.width = width;
		
		this.map = new Case[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Case c = new Case(null,i,j);
				this.map[i][j] = c;
			}
		}
	}
	
	/**
	 * Clear all the map cases by setting their states Empty and removing game items held
	 */
	public void clear(){
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.map[i][j].setItem(null);
				this.map[i][j].setState(CaseEmpty.getInstance());
			}
		}
	}
	
	/**
	 * Returns case on [i][j]
	 * @param i: the line
	 * @param j: the column
	 * @return the map case situated on [i][j]
	 */
	public Case getCase(int i, int j) {
		return this.map[i][j];
	}
	
	/**
	 * 
	 * @return map height
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * 
	 * @return map width
	 */
	public int getWidth() {
		return this.width;
	}
	
	

}
