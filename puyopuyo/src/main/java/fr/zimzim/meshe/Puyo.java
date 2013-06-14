package fr.zimzim.meshe;


public class Puyo implements GameItem{
	
	private int line;
	private int col;
	private int type;
	
	public Puyo(int x, int y, int type) {
		this.line = x;
		this.col = y;
		this.type = type;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int x) {
		this.line = x;
	}

	public int getColumn() {
		return col;
	}

	public void setColumn(int y) {
		this.col = y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
