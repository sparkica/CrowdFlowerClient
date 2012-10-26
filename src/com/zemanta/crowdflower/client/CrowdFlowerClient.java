package com.zemanta.crowdflower.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import com.zemanta.crowdflower.client.CF_DataType;
import com.zemanta.crowdflower.client.CF_JobParameters;

public class CrowdFlowerClient{
	
	private static String SERVICE_URL = "https://api.crowdflower.com/v1/";
	private String api_key = "";
	private int timeout = 1000;
	
	public CrowdFlowerClient(String apiKey) {
		api_key = apiKey;
	}
	
		
	public String createEmptyJob() {
		
		String url = SERVICE_URL + "jobs";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "POST",CF_DataType.JSON,this.timeout);
		return results;
	
	}
		
	public String createEmptyJob(String title, String instructions) {
		
		String url = SERVICE_URL + "jobs";
		url += "?key=" + api_key + "&";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(CF_JobParameters.TITLE.value(), title);
		params.put(CF_JobParameters.INSTRUCTIONS.value(), instructions);
	
		url += build_query(params);
		
		String results = getJSON(url, "POST",CF_DataType.JSON ,this.timeout);
		return results;
	
	}
	
	public String bulkUploadJSON(String jobID, String data) {
		
		String url = SERVICE_URL + "jobs/" + jobID + "/upload";
		url += "?key=" + api_key;
		
		String results = getJSON(url, "POST", CF_DataType.JSON, data, this.timeout);
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
		
		String results = getJSON(url, "GET", CF_DataType.JSON, this.timeout);
		return results;
	}

	public String getAllJobs() {
		String url = SERVICE_URL + "jobs.json";
		url += "?key=" + api_key;
		
		System.out.println("URL: " + url);
		System.out.println("Type JSON: " + CF_DataType.JSON.value());		
		
		String results = getJSON(url, "GET", CF_DataType.JSON, this.timeout);
		
		return results;
		
	}

	private String getJSON(String url, String method, CF_DataType contentType, int timeout) {
		
		HttpURLConnection c = null;
		String result = "";
		try {
	        URL u = new URL(url);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestProperty("Accept", "application/json");
	        c.setRequestProperty("Content-type", contentType.value());
	        c.setRequestMethod(method); //GET, SET, ...
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	     	c.setRequestProperty("Content-length", "0");
	        c.setConnectTimeout(timeout);
	        c.setReadTimeout(timeout);
	        c.connect();
	        int status = c.getResponseCode();

//	        switch (status) {
//	            case 200:
//	            case 201:
//	                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
//	                StringBuilder sb = new StringBuilder();
//	                String line;
//	                while ((line = br.readLine()) != null) {
//	                    sb.append(line+"\n");
//	                }
//	                br.close();
//	                return sb.toString();
//	            default:
//	            	System.out.println("Something went wrong, status: " + status);
//	            	//get message!
//	        }
	        
	        //TODO: check if this is true!
	        //read in all cases, because service returns error message
	        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            result =  sb.toString();
	        

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
		return result;
	}
	
	private String getJSON(String url, String method, CF_DataType contentType, String content, int timeout) {
		HttpURLConnection c = null;
		String result = "";
		try {
	        URL u = new URL(url);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestProperty("Accept", "application/json");
	        c.setRequestProperty("Content-type", contentType.value());
	        c.setRequestMethod(method); //GET, SET, ...
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        
	        if(content!= null) {
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
	        
	        //TODO: check whether switch clause is needed
	        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            result =  sb.toString();

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
	    return result;
	}
	
	
	//encode values only, parameter names include special chars like -> [ ]
	private String build_query(Map<String, String> parameters) {
		StringBuilder sb = new StringBuilder();
		
		try {
			for (Map.Entry<String, String> entry: parameters.entrySet()) {
				String key = entry.getKey();
				String value = URLEncoder.encode(entry.getValue(),"UTF-8");
				sb.append(key).append("=").append(value).append("&");
			}	
		}
		catch(UnsupportedEncodingException  e)
		{
			e.printStackTrace();
		}

		return sb.toString();
		
	}
	



}
