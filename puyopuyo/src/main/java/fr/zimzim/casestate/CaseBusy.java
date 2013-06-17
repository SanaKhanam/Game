package fr.zimzim.casestate;

/**
 * 
 * @author Simon Jambu
 * State instance of a map case. Means that the current case holds something (a game item)
 * @see GameItem
 */
public class CaseBusy implements CaseState {
	
	/**
	 * Static instance
	 */
	private static final CaseState instance = new CaseBusy();
	
	/**
	 * 
	 * @return class instance
	 */
	public static CaseState getInstance() {
		return instance;
	}

}
