package fr.zimzim.ressource;


public class Case {
	
	private Puyo puyo;
	private int posX;
	private int posY;
	
	public Case(Puyo puyo, int posX, int posY) {
		this.puyo = puyo;
		this.posX = posX;
		this.posY = posY;
	}
	
	
	public Puyo getPuyo() {
		return this.puyo;
	}
	public void setPuyo(Puyo puyo) {
		this.puyo = puyo;
	}
	public int getPosX() {
		return this.posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return this.posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}

}
