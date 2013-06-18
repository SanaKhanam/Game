package fr.zimzim.render;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fr.zimzim.model.GameEngine;

public class GraphicEngine implements Observer{
	
	private Map<String,GraphicComponent> graphics;
	
	public GraphicEngine(GameEngine engine){
		this.graphics = new Hashtable<String, GraphicComponent>();
		this.graphics.put(MapRender.NAME, new MapRender(engine));
		this.graphics.put(ItemRender.NAME, new ItemRender(engine));
		this.graphics.put(ScoreRender.NAME, new ScoreRender(engine));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Iterator<String> it = this.graphics.keySet().iterator();
		while(it.hasNext()) this.graphics.get(it.next()).update();
		
	}
	
	public GraphicComponent getComponent(String name){
		return this.graphics.get(name);
	}

	public void clear() {
		Iterator<String> it = this.graphics.keySet().iterator();
		while(it.hasNext()) this.graphics.get(it.next()).clear();
	}

}
