package com.zemanta.crowdflower.client;

public enum CF_DataType {
	JSON ("application/json"),
	DATA_FEED("job[uri]="),
	SPREADSHEET_EXCEL ("application/vnd.ms-excel"),
	SPREADSHEET_CSV ("text/csv"),
	SPREADSHEET_PLAIN("text/plain");
	
	private String cf_type;
		
	CF_DataType(String data_type) {
		cf_type = data_type;
	}
	
	public String toString() {
		return cf_type;
	}
}
