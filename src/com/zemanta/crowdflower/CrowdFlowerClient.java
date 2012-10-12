package com.zemanta.crowdflower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


import com.zemanta.crowdflower.CF_DataType;

public class CrowdFlowerClient{
	
	private static String SERVICE_URL = "https://api.crowdflower.com/v1/";
	private String api_key = "";
	private int timeout = 500;
	
	public CrowdFlowerClient(String apiKey) {
		api_key = apiKey;
	}
	
	//1. upload data
	
	// upload - new job
	// update - job_id needed, existing job is updated
	
	
	//first object is used to set columns
	public String create(CF_DataType content_type) {
		
		String url = SERVICE_URL + "jobs";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "POST", content_type.toString(), this.timeout);
		return results;
	
	}
	
	public String create_empty_job() {
		
		String url = SERVICE_URL + "jobs";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "POST","" , this.timeout);
		return results;
	
	}
	
	
	public String update(String job_id, CF_DataType content_type){
		
		String url= SERVICE_URL + "jobs/" + job_id + "/upload";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "PUT", content_type.toString(), this.timeout);
		return results;
	
	}
	
	public String read(String job_id) {
		
		String url= SERVICE_URL + "jobs/" + job_id;
		url += "?key=" + api_key;
		
		String results = getJSON(url, "GET", CF_DataType.JSON.toString(), this.timeout);
		return results;
	}

	public String get_all_jobs() {
		String url = SERVICE_URL + "jobs.json";
		url += "?key=" + api_key;
		
		System.out.println("URL: " + url);
		
		System.out.println("Type JSON: " + CF_DataType.JSON.toString());
		
		String results = getJSON(url, "GET", CF_DataType.JSON.toString(), this.timeout);
		
		
		return results;
		
	}
	
	
	private String build_query(Map<String, String> parameters) {
		StringBuilder sb = new StringBuilder();
		
		try {
			
			for (Map.Entry<String, String> entry: parameters.entrySet()) {
				String key = URLEncoder.encode(entry.getKey(), "UTF-8");
				String value = URLEncoder.encode(entry.getValue(),"UTF-8");
				sb.append(key).append("=").append(value).append("?");
			}
			
		}
		catch(UnsupportedEncodingException  e)
		{
			e.printStackTrace();
		}
		
		
		return sb.toString();
		
	}
	
	
	private String getJSON(String url, String format, String contentType, int timeout) {
	    try {
	        URL u = new URL(url);
	        HttpURLConnection c = (HttpURLConnection) u.openConnection();
	        c.setRequestProperty("content-type", contentType);
	        c.setRequestMethod(format); //GET, SET, ...
	        c.setRequestProperty("Content-length", "0");
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        c.setConnectTimeout(timeout);
	        c.setReadTimeout(timeout);
	        c.connect();
	        int status = c.getResponseCode();

	        switch (status) {
	            case 200:
	            case 201:
	                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
	                StringBuilder sb = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    sb.append(line+"\n");
	                }
	                br.close();
	                return sb.toString();
	        }

	    } catch (MalformedURLException ex) {
	    	System.out.println("Malfromed URL exception occured: " + ex.getMessage());
	    } catch (IOException ex) {
	    	System.out.println("IOException error occured: " + ex.getMessage());
	    }
	    return null;
	}
	
	
	private String url_encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			System.out.print("Unsuccessful attempt to encode " + value +" into UTF8");
			System.out.println("Exception: " + e.getMessage());
		    System.exit(1);
		}
		return null;
	}


}
