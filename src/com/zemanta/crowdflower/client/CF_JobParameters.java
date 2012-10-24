package com.zemanta.crowdflower.client;

public enum CF_JobParameters {

	TITLE ("job[title]"),
	INSTRUCTIONS("job[instructions]");
	
	private String cf_param;
	
	CF_JobParameters(String paramName) {
		cf_param = paramName;
	}
	
	public String value() {
		return cf_param;
	}
	
}
