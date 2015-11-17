package com.zemanta.crowdflower.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
	private HttpURLConnection connection;
	
	public CrowdFlowerClient(String apiKey, int defaultTimeout) {
		api_key = apiKey;
		connection = null;
		timeout = defaultTimeout;
	}
			
	public String createNewJobWithoutData() {
		
		String url = SERVICE_URL + "jobs";
		url += "?key=" + api_key;
		
		return getJSON(url, "POST",CF_DataType.JSON, "", this.timeout);
	}
		
	public String createNewJobWithoutData(String title, String instructions) {
		
		String url = SERVICE_URL + "jobs?";
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.TITLE.value(), title);
		params.put(CF_JobParameters.INSTRUCTIONS.value(), instructions);
	
		url += Util.buildQueryFromParameters(params);
		
		return getJSON(url,"POST",CF_DataType.JSON , "", this.timeout);
	
	}
	
		
	public String bulkUploadToNewJob(CF_DataType content_type, String data) {
		
		String url = SERVICE_URL + "jobs/upload.json?key=" + api_key;	
		
		return getJSON(url, "POST", content_type, data, this.timeout);
	}
	
	
	public String bulkUploadJSONToExistingJob(String jobID, String data) {
		
		String url = SERVICE_URL + "jobs/" + jobID + "/upload?";

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.FORCE.value(), "true");

		url += Util.buildQueryFromParameters(params);
		
		return getJSON(url, "POST", CF_DataType.JSON, data, this.timeout);
			
	}

	
	// Cannot have title and instructions - it fails!
	public String bulkUploadJSONToNewJob(String data) {
		
		String url = SERVICE_URL + "jobs/upload.json?";

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.FORCE.value(), "true");

		url += Util.buildQueryFromParameters(params);
		
		return getJSON(url, "POST", CF_DataType.JSON, data, this.timeout);
				
		
	}

	
	
	public String bulkUploadToExistingJob(String jobID, CF_DataType content_type, String data) {

		String url = SERVICE_URL + "jobs/" + jobID + "/upload";
		url += "?key=" + api_key;
		
		return getJSON(url, "POST", content_type, data, this.timeout);
	}
	
	
	//changes title only if cml field, problem, and instructions are set
	//no error is returned yet - it's a bug in API (confirmed by CrowdFlower staff in Nov 2012)
	public String changeJobTitle(String job_id, String new_title) {
		String url= SERVICE_URL + "jobs/" + job_id + ".json?";

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.TITLE.value(), new_title);

		url += Util.buildQueryFromParameters(params);
		return getJSON(url, "PUT", CF_DataType.JSON , new_title, this.timeout);
	}
	
	
	public String upateJobCML(String job_id, String cml) {
		
		String url= SERVICE_URL + "jobs/" + job_id + ".json?";

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("key", api_key);
		params.put(CF_JobParameters.CML.value(), cml);

		url += Util.buildQueryFromParameters(params);
		return getJSON(url, "PUT", CF_DataType.JSON , "", this.timeout);
	}
	
		
	public String updateJob(String job_id, Map<String, String> params) {
		
		String url = SERVICE_URL + "jobs/" + job_id + ".json?";
		
		Map<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("key", api_key);
		parameters.putAll(params);
			
		url += Util.buildQueryFromParameters(params);
		
		return getJSON(url, "PUT", CF_DataType.JSON , "", this.timeout);
		
	}
	
	
	public String getJob(String job_id) {
		
		String url= SERVICE_URL + "jobs/" + job_id + ".json";
		url += "?key=" + api_key;
		
		return getJSON(url, "GET", CF_DataType.JSON, null, this.timeout);
	}

	public String getAllJobs() {
		String url = SERVICE_URL + "jobs.json";
		url += "?key=" + api_key;
		
		return getJSON(url, "GET", CF_DataType.JSON, null, this.timeout * 2);
	}
	
	public String copyJob(String job_id) {
		
		String url = SERVICE_URL + "jobs/" + job_id + "/copy.json";
		url += "?key=" + api_key;
		
		return getJSON(url, "POST", CF_DataType.JSON, "", this.timeout);
		
	}
	
	public String copyJob(String job_id, Map<String, String> params) {
		
		String url = SERVICE_URL + "jobs/" + job_id + "/copy.json?";
		
		Map<String, String> params_new = new LinkedHashMap<String, String>();
		params_new.put("key", api_key);
		params_new.putAll(params);
		
		url += Util.buildQueryFromParameters(params_new);
		return getJSON(url, "POST", CF_DataType.JSON, "", this.timeout);
	}
	
	public String getLegend(String job_id) {
		
		String url = SERVICE_URL + "jobs/" + job_id + "/legend";
		url += "?key=" + api_key;
		
		return getJSON(url, "GET", CF_DataType.JSON, "", this.timeout);
	}
	
	public String getJobUnits(String job_id) {
		String url = SERVICE_URL + "jobs/" + job_id + "/units/";
		url += "?key=" + api_key;
		return getJSON(url, "GET", CF_DataType.JSON, "", this.timeout);
	}
	
	
	//todo: refactor this so it will be mockable
	
	protected void openConnection(String url) throws MalformedURLException, IOException{	

		if(connection == null) {
			URL u = new URL(url);	
			connection =  (HttpURLConnection) u.openConnection();
		}
	}
	
	public void setHttpURLConnection(HttpURLConnection con) {
		
		connection = con;
	}
	
	
	protected void createRequest(String method, CF_DataType contentType, String content, int timeout) 
			throws UnsupportedEncodingException, IOException {
        
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-type", contentType.value());
		connection.setRequestMethod(method); //GET, SET, ...
		connection.setUseCaches(false);
		connection.setAllowUserInteraction(false);
        
        if(content!= null) {
	        connection.setDoOutput(true);
        	connection.setRequestProperty("Content-length", String.valueOf(content.length()));
        	OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(),"UTF8");
        	osw.write(content);
        	osw.close();
	        
        } else {
	        connection.setRequestProperty("Content-length", "0");
        }
        
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
				
	}
	
	protected String createErrorResponse(String errorType, String details) {
		String result = "{\"status\":\"ERROR\", \"error\":{\"message\": \"";
		result += "An exception of type " + errorType + " occured. Details: " + details + "\"}}";
		
		return result;
	}
	
	protected String getResponse() {

		String result = "";

		try {
			connection.connect();
			int status = connection.getResponseCode();
		        
	        BufferedReader br = null;
	        	        
	        switch(status) {
	        	case HttpURLConnection.HTTP_BAD_REQUEST: default:
	        		br = new BufferedReader(new InputStreamReader(connection.getErrorStream())); break;
	        	case HttpURLConnection.HTTP_OK: case HttpURLConnection.HTTP_CREATED:
	        		br = new BufferedReader(new InputStreamReader(connection.getInputStream())); break;
	        }
		        
	        System.out.println("Status: " + status);
	        result += "{\"status\":" + String.valueOf(status) + ", \"response\" : ";
		    StringBuilder sb = new StringBuilder();
	        String line;
	         while ((line = br.readLine()) != null) {
	             sb.append(line+"\n");
	         }
	         br.close();
	         result +=  sb.toString();
	         result += "}";

		}
		catch (IOException e) {
			result = createErrorResponse("Input/Output Exception", e.getLocalizedMessage());
		}
		finally {
			connection.disconnect();
		}
         
        return result;
		
	}
	
	protected String getJSON(String url, String method, CF_DataType contentType, String content, int timeout) {

		String result = "";
		try {
			openConnection(url);
			createRequest(method, contentType, content, timeout);
			result =  getResponse();
		}
		catch (MalformedURLException ex) {
			result = createErrorResponse("Malformed URL", ex.getLocalizedMessage());
		}
		catch (IOException e) {
			result = createErrorResponse("Input/Output Exception", e.getLocalizedMessage());
		}
		catch (Exception e2) {
			result = createErrorResponse("Unspecified Exception", e2.getLocalizedMessage());
		} finally {
			connection.disconnect();
			connection = null;	
		}
		
		return result;
	}
	

}
