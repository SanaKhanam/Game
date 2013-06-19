package fr.zimzim.render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import fr.zimzim.meshe.GameItem;
import fr.zimzim.model.GameEngine;
import fr.zimzim.util.Settings;

/**
 * GraphicComponent in charge of displaying next active items
 * @see GameEngine#addActiveItems()
 * @author Simon Jambu
 *
 */
public class ItemRender extends JComponent implements GraphicComponent{

	/**
	 * Component name
	 */
	public static final String NAME = "NextItem";

	/**
	 * Item list to display
	 */
	private List<GameItem> items;

	/**
	 * Item images
	 */
	private Image[] images;

	/**
	 * Frame image
	 */
	private Image cadre;

	/**
	 * Instance of game engine
	 */
	private GameEngine engine;

	/**
	 * Auto generated serial
	 */
	private static final long serialVersionUID = 7326520990733045489L;

	/**
	 * Constructor. Initializes the component
	 * @param engine
	 */
	public ItemRender(GameEngine engine) {
		this.items = new ArrayList<GameItem>();
		this.engine = engine;
		this.cadre = getToolkit().getImage(this.getClass().getResource(Settings.IMG_SMALL_CADRE));
		this.images = new Image[4];
		this.images[Settings.PUYO_GREEN] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_GREEN));
		this.images[Settings.PUYO_YELLOW] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_YELLOW));
		this.images[Settings.PUYO_RED] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_RED));
		this.images[Settings.PUYO_BLUE] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_BLUE));
		this.setPreferredSize(new Dimension((Settings.TAILLE_PIXELS*2)+(Settings.LEFT_RIM_CADRE*2),
				(Settings.TAILLE_PIXELS)+Settings.TOP_RIM_CADRE*2));
		repaint();
	}

	/**
	 * Draws items 
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.cadre,
				0,
				0,
				null);
		if(items.size()>0){
			for(int i=0; i< items.size(); i++) {
				g.drawImage(this.images[this.items.get(i).getType()],
						(i*Settings.TAILLE_PIXELS)+Settings.LEFT_RIM_CADRE,
						Settings.TOP_RIM_CADRE,
						null);
			}
		}

	}

	/**
	 * Update the component
	 */
	public void update() {
		if(engine.getNextItems().size() > 0){
			this.items.clear();
			this.items.addAll(engine.getNextItems());	
			repaint();
		}
	}

	/**
	 * Resets useful datas
	 */
	@Override
	public void clear() {
		this.items.clear();
	}

}
