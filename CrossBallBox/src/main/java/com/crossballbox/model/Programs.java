package com.crossballbox.model;

public enum Programs {

	TAEBO("T"), CROSSFIT("C"), AEROTONUS("A"), TOTAL50("T50");
	
	private String programsLetter;
	 
	private Programs(String s) {
		programsLetter = s;
	}
 
	public final String getProgramLetter() {
		return programsLetter;
	}
}
