package fr.zimzim.casestate;

/**
 * 
 * @author Simon Jambu
 *	State instance of a map case. Means that the current case is empty.
 */
public class CaseEmpty implements CaseState {
	
	/**
	 * Static instance
	 */
	private static final CaseState instance = new CaseEmpty();
	
	/**
	 * 
	 * @return class instance
	 */
	public static CaseState getInstance() {
		return instance;
	}
}
