package fr.zimzim.casestate;

/**
 * 
 * @author Simon Jambu
 *	State instance of a map case. Means that the current case is empty.
 */
public class CaseEmpty implements CaseState {
	
	private static final CaseState instance = new CaseEmpty();
	
	public static CaseState getInstance() {
		return instance;
	}
}
