package fr.zimzim.model;

public class Map {
	
	private int height;
	private int width;
	private Case[][] map;
	
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
	
	public Case getCase(int i, int j) {
		return this.map[i][j];
	}
	
	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}
	
	

}
