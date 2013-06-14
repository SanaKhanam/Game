package fr.zimzim.render;

import fr.zimzim.model.GameEngine;
import fr.zimzim.util.Settings;

import javax.swing.*;
import java.awt.*;


public class RenderEngine extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7196135111434103291L;

	private static final int TAILLE_PIXELS = 32;

	private Image[] images;
	private GameEngine gameEngine;
	private Dimension dimension;

	public RenderEngine(GameEngine game) {
		this.gameEngine = game;
		this.dimension = new Dimension(this.gameEngine.getMapDimension());
		this.setPreferredSize(new Dimension(TAILLE_PIXELS*dimension.width, TAILLE_PIXELS*dimension.height));
		
		this.images = new Image[5];
        this.images[Settings.PUYO_EMPTY] = getToolkit().getImage(this.getClass().getResource("/img/puyo_empty.png"));
        this.images[Settings.PUYO_GREEN] = getToolkit().getImage(this.getClass().getResource("/img/p_green.png"));
        this.images[Settings.PUYO_YELLOW] = getToolkit().getImage(this.getClass().getResource("/img/p_yellow.png"));
        this.images[Settings.PUYO_RED] = getToolkit().getImage(this.getClass().getResource("/img/p_red.png"));
        this.images[Settings.PUYO_BLUE] = getToolkit().getImage(this.getClass().getResource("/img/p_blue.png"));

		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.green);
		g.drawRect(0, 0, this.dimension.width*TAILLE_PIXELS, this.dimension.height*TAILLE_PIXELS);
		for(int i=0; i<this.dimension.height; i++){
			for(int j=0; j<this.dimension.width; j++){
				if(gameEngine.getCase(i,j).getPuyo() != null)
					g.drawImage(this.images[gameEngine.getCase(i,j).getPuyo().getType()], j*TAILLE_PIXELS, i*TAILLE_PIXELS, null);
			}
		}
		
		for(int i=0; i<this.gameEngine.getFallers().size(); i++) {
			g.drawImage(this.images[gameEngine.getFallers().get(i).getType()], gameEngine.getFallers().get(i).getColumn()*TAILLE_PIXELS, gameEngine.getFallers().get(i).getLine()*TAILLE_PIXELS, null);
		}

	}

}
