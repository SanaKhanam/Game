package fr.zimzim.render;

import fr.zimzim.casestate.CaseBusy;
import fr.zimzim.meshe.GameItem;
import fr.zimzim.model.GameEngine;
import fr.zimzim.util.Settings;

import javax.swing.*;
import java.awt.*;

/**
 * GraphicComponent in charge of displaying the game map
 * @see Map
 * @author Simon Jambu
 *
 */
public class MapRender extends JComponent implements GraphicComponent{

	/**
	 * Auto generated serial
	 */
	private static final long serialVersionUID = 7196135111434103291L;

	/**
	 * Component name
	 */
	public static final String NAME = "MapRender";
	
	/**
	 * Component images
	 */
	private Image[] images;
	
	/**
	 * Frame image
	 */
	private Image cadre;
	
	/**
	 * Instance of game engine
	 */
	private GameEngine gameEngine;
	
	/**
	 * Constructor. Initializes the component
	 * @param game: Instance of the game engine
	 */
	public MapRender(GameEngine game) {
		this.gameEngine = game;
		this.setPreferredSize(new Dimension((Settings.TAILLE_PIXELS*Settings.MAP_WIDTH)+(Settings.LEFT_RIM_CADRE*2),
				(Settings.TAILLE_PIXELS*Settings.MAP_HEIGHT)+(Settings.TOP_RIM_CADRE*2)));
		this.cadre = getToolkit().getImage(this.getClass().getResource(Settings.IMG_CADRE));
		this.images = new Image[Settings.NB_PUYOS_TYPES];
        this.images[Settings.PUYO_GREEN] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_GREEN));
        this.images[Settings.PUYO_YELLOW] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_YELLOW));
        this.images[Settings.PUYO_RED] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_RED));
        this.images[Settings.PUYO_BLUE] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_BLUE));
		repaint();
	}
	
	/**
	 * Draws game-map
	 */
	public void paint(Graphics g) {
		super.paint(g);
		//Draw the game map
		g.drawImage(this.cadre,
				0,
				0,
				null);
		for(int i=0; i<Settings.MAP_HEIGHT; i++){
			for(int j=0; j<Settings.MAP_WIDTH; j++){
				if(gameEngine.getCase(i,j).getState() instanceof CaseBusy && gameEngine.getCase(i,j).getItem() !=null)
					g.drawImage(this.images[gameEngine.getCase(i,j).getItem().getType()],
							(j*Settings.TAILLE_PIXELS)+Settings.LEFT_RIM_CADRE,
							(i*Settings.TAILLE_PIXELS)+Settings.TOP_RIM_CADRE,
							null);
			}
		}
		
		//Draw active items
		for(int i=0; i<this.gameEngine.getActiveItems().size(); i++) {
			GameItem item = gameEngine.getActiveItems().get(i);
			if(item.getLine() !=-1)
			g.drawImage(this.images[item.getType()],
					(item.getColumn()*Settings.TAILLE_PIXELS)+Settings.LEFT_RIM_CADRE,
					(item.getLine()*Settings.TAILLE_PIXELS)+Settings.TOP_RIM_CADRE,
					null);
		}

	}
	
	/**
	 * Update the component
	 */
	public void update() {
		repaint();
	}
	
	/**
	 * Resets useful datas
	 */
	@Override
	public void clear() {}

}
