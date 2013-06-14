package fr.zimzim.render;

import fr.zimzim.casestate.CaseBusy;
import fr.zimzim.meshe.GameItem;
import fr.zimzim.model.GameEngine;
import fr.zimzim.util.Settings;

import javax.swing.*;
import java.awt.*;


public class RenderEngine extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7196135111434103291L;

	

	private Image[] images;
	private GameEngine gameEngine;
	private Dimension dimension;

	public RenderEngine(GameEngine game) {
		this.gameEngine = game;
		this.dimension = new Dimension(Settings.MAP_WIDTH, Settings.MAP_HEIGHT);
		this.setPreferredSize(new Dimension(Settings.TAILLE_PIXELS*dimension.width,
				Settings.TAILLE_PIXELS*dimension.height));
		
		this.images = new Image[5];
        this.images[Settings.PUYO_EMPTY] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_EMPTY));
        this.images[Settings.PUYO_GREEN] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_GREEN));
        this.images[Settings.PUYO_YELLOW] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_YELLOW));
        this.images[Settings.PUYO_RED] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_RED));
        this.images[Settings.PUYO_BLUE] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_BLUE));

		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.green);
		g.drawRect(0, 0, this.dimension.width*Settings.TAILLE_PIXELS, this.dimension.height*Settings.TAILLE_PIXELS);
		for(int i=0; i<this.dimension.height; i++){
			for(int j=0; j<this.dimension.width; j++){
				if(gameEngine.getCase(i,j).getState() instanceof CaseBusy)
					g.drawImage(this.images[gameEngine.getCase(i,j).getItem().getType()],
							j*Settings.TAILLE_PIXELS,
							i*Settings.TAILLE_PIXELS,
							null);
			}
		}
		
		for(int i=0; i<this.gameEngine.getActiveItems().size(); i++) {
			GameItem item = gameEngine.getActiveItems().get(i);
			g.drawImage(this.images[item.getType()],
					item.getColumn()*Settings.TAILLE_PIXELS,
					item.getLine()*Settings.TAILLE_PIXELS,
					null);
		}

	}

}
