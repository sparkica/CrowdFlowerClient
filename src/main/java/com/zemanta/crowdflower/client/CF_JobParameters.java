package com.zemanta.crowdflower.client;

public enum CF_JobParameters {

	TITLE ("job[title]"),
	INSTRUCTIONS("job[instructions]"),
	FORCE("force"),
	ALL_UNITS("all_units"),
	GOLD("gold"),
	CML("job[cml]");
	
	private String cf_param;
	
	CF_JobParameters(String paramName) {
		cf_param = paramName;
	}
	
	public String value() {
		return cf_param;
	}
	
}
