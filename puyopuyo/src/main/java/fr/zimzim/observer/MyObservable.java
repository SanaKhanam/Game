package fr.zimzim.observer;

import java.util.Observable;

/**
 * Objects that holds observers (Design pattern Observer)
 * @author Simon Jambu
 *
 */
public class MyObservable extends Observable{
	
	/**
	 * Notifies that the object observed has change his state
	 */
	public void setChanged(){
		super.setChanged();
	}
}
