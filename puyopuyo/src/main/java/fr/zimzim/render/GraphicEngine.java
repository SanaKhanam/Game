package fr.zimzim.render;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fr.zimzim.model.GameEngine;

/**
 * Graphic component holder. Observes the game engine and update its components when engine state change
 * @author Simon Jambu
 *
 */
public class GraphicEngine implements Observer{
	
	/**
	 * Graphical components
	 */
	private Map<String,GraphicComponent> graphics;
	
	/**
	 * Constructor
	 * @param engine: Instance of the game engine
	 */
	public GraphicEngine(GameEngine engine){
		this.graphics = new Hashtable<String, GraphicComponent>();
		this.graphics.put(MapRender.NAME, new MapRender(engine));
		this.graphics.put(ItemRender.NAME, new ItemRender(engine));
		this.graphics.put(ScoreRender.NAME, new ScoreRender(engine));
	}
	
	/**
	 * Updates all graphical components
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		Iterator<String> it = this.graphics.keySet().iterator();
		while(it.hasNext()) this.graphics.get(it.next()).update();
		
	}
	
	/**
	 * 
	 * @param name: Component's name
	 * @return Graphical component called <code>name</code>
	 */
	public GraphicComponent getComponent(String name){
		return this.graphics.get(name);
	}
	
	/**
	 * Resets all components
	 */
	public void clear() {
		Iterator<String> it = this.graphics.keySet().iterator();
		while(it.hasNext()) this.graphics.get(it.next()).clear();
	}

}
