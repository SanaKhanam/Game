package fr.zimzim.casestate;

/**
 * 
 * @author Simon Jambu
 * State instance of a map case. Means that the current case hold something (an item)
 */
public class CaseBusy implements CaseState {
	
	private static final CaseState instance = new CaseBusy();
	
	/**
	 * 
	 * @return class instance
	 */
	public static CaseState getInstance() {
		return instance;
	}

}
