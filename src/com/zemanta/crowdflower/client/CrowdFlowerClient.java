package com.zemanta.crowdflower.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;


import com.zemanta.crowdflower.client.CF_DataType;
import com.zemanta.crowdflower.client.CF_JobParameters;
import com.zemanta.util.Util;

public class CrowdFlowerClient{
	
	private static String SERVICE_URL = "https://api.crowdflower.com/v1/";
	private String api_key = "";
	private int timeout = 1000;
	
	public CrowdFlowerClient(String apiKey) {
		api_key = apiKey;
	}
	
		
	public String createNewJobWithoutData() {
		
		String url = SERVICE_URL + "jobs";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "POST",CF_DataType.JSON, "", this.timeout);
		return results;
	
	}
		
	public String createNewJobWithoutData(String title, String instructions) {
		
		String url = SERVICE_URL + "jobs?";
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.TITLE.value(), title);
		params.put(CF_JobParameters.INSTRUCTIONS.value(), instructions);
	
		url += Util.buildQueryFromParameters(params);
		
		String results = getJSON(url, "POST",CF_DataType.JSON , "", this.timeout);
		return results;
	
	}
	
		
	public String bulkUploadToNewJob(CF_DataType content_type, String data) {
		
		String url = SERVICE_URL + "jobs/upload.json?key=" + api_key;		
		String results = getJSON(url, "POST", content_type, data, this.timeout);
		return results;		
	}
	
	
	public String bulkUploadJSONToExistingJob(String jobID, String data) {
		
		String url = SERVICE_URL + "jobs/" + jobID + "/upload?";

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.FORCE.value(), "true");

		url += Util.buildQueryFromParameters(params);
				
		String results = getJSON(url, "POST", CF_DataType.JSON, data, this.timeout);
		return results;
		
	}

	
	// Cannot have title and instructions!
	public String bulkUploadJSONToNewJob(String data) {
		
		String url = SERVICE_URL + "jobs/upload.json?";

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.FORCE.value(), "true");

		url += Util.buildQueryFromParameters(params);
				
		String results = getJSON(url, "POST", CF_DataType.JSON, data, this.timeout);
		return results;
		
	}

	
	
	public String bulkUploadToExistingJob(String jobID, CF_DataType content_type, String data) {

		String url = SERVICE_URL + "jobs/" + jobID + "/upload";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "POST", content_type, data, this.timeout);
		return results;
		
	}
	
	public String changeJobTitle(String job_id, String new_title) {
		String url= SERVICE_URL + "jobs/" + job_id + ".json?";

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.TITLE.value(), new_title);
		params.put(CF_JobParameters.INSTRUCTIONS.value(),"Write some instructions here.");

		url += Util.buildQueryFromParameters(params);

		
		String results = getJSON(url, "PUT", CF_DataType.JSON ,new_title, this.timeout);
		return results;
	}
	
	
	
	public String update(String job_id, CF_DataType content_type){
		
		String url= SERVICE_URL + "jobs/" + job_id + "/upload";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "PUT", content_type,"", this.timeout);
		return results;
	
	}
	
	public String read(String job_id) {
		
		String url= SERVICE_URL + "jobs/" + job_id;
		url += "?key=" + api_key;
		
		String results = getJSON(url, "GET", CF_DataType.JSON, "", this.timeout);
		return results;
	}

	public String getAllJobs() {
		String url = SERVICE_URL + "jobs.json";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "GET", CF_DataType.JSON, "", this.timeout);
		
		return results;
		
	}
	
	protected String getJSON(String url, String method, CF_DataType contentType, String content, int timeout) {
		HttpURLConnection c = null;
		String result = "";
		
		System.out.println("URL: " + url);
		
		try {
	        URL u = new URL(url);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestProperty("Accept", "application/json");
	        c.setRequestProperty("Content-type", contentType.value());
	        c.setRequestMethod(method); //GET, SET, ...
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        
	        if(content!= null && content.length() > 0) {
		        c.setDoOutput(true);
	        	c.setRequestProperty("Content-length", String.valueOf(content.length()));
	        	OutputStreamWriter osw = new OutputStreamWriter(c.getOutputStream());
	        	osw.write(content);
	        	osw.close();
		        
	        } else {
		        c.setRequestProperty("Content-length", "0");
	        }
	        
	        c.setConnectTimeout(timeout);
	        c.setReadTimeout(timeout);
	        c.connect();
	        int status = c.getResponseCode();
	        
	        BufferedReader br = null;
	        
	        switch(status) {
	        	case 404: default:
	        		br = new BufferedReader(new InputStreamReader(c.getErrorStream())); break;
	        	case 200: case 201:
	        		br = new BufferedReader(new InputStreamReader(c.getInputStream())); break;
	        }
	        
	        //add status code to response
	        System.out.println("Status: " + status);
	        result += "{status:" + String.valueOf(status) + ", response : ";
	        
	        
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            result +=  sb.toString();

		 } catch (MalformedURLException ex) {
		    	System.out.println("Malfromed URL exception occured: " + ex.getMessage());
	    } catch (IOException ex) {
		    	System.out.println("IOException error occured: " + ex.getMessage());
	    }
	    finally {
	    	if(c != null) {
	    		c.disconnect();
	    	}
	    }
        result += "}";
	    return result;
	}
	

}
