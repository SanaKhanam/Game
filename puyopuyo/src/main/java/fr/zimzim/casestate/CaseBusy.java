package fr.zimzim.casestate;

public class CaseBusy implements CaseState {
	
	private static final CaseState instance = new CaseBusy();
	
	public static CaseState getInstance() {
		return instance;
	}

}
