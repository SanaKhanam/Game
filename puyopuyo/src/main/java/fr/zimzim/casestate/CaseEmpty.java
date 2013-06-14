package fr.zimzim.casestate;

public class CaseEmpty implements CaseState {
	
	private static final CaseState instance = new CaseEmpty();
	
	public static CaseState getInstance() {
		return instance;
	}
}
