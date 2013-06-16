package fr.zimzim.render;

import java.util.Hashtable;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class GraphicEngine implements Observer{
	
	private Map<String,GraphicComponent> graphics;
	
	public GraphicEngine(){
		this.graphics = new Hashtable<String,GraphicComponent>();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
