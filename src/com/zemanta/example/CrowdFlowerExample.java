package com.zemanta.example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zemanta.crowdflower.client.*;

public class CrowdFlowerExample {
	
	public void createEmptyJob(CrowdFlowerClient cf_client) {
		
		System.out.println("Creating empty job - untitled and without data ...");
		String response = cf_client.createNewJobWithoutData();
		System.out.println(response);
		
	}
	
	public void getJobData(CrowdFlowerClient cf_client, String jobID) {
		System.out.println("Geting data about particular job ...");
		String response = cf_client.read(jobID);
		System.out.println(response);
	}
	
	public void uploadDatatoExistingJob(CrowdFlowerClient cf_client, String jobID) {
		
		System.out.println("Upload data to existing job ...");
		JsonArray data =  new JsonArray();
	
		JsonObject obj = new JsonObject();
		JsonObject obj2 = new JsonObject();
		
		obj.addProperty("column_1", "11");
		obj.addProperty("column_2", "2212");
		obj.addProperty("column_3", "2213");
		
		obj2.addProperty("column_1", "2321");
		obj2.addProperty("column_2", "");
		obj2.addProperty("column_3", "2322");
		
		data.add(obj);
		data.add(obj2);
		
		String obj_collection = "";
		
		for (int i = 0; i < data.size(); i++) {
			obj_collection += "\n" + data.get(i).toString();
		}
		
		System.out.println("Data for upload: \n" + obj_collection);
						
		String response = cf_client.bulkUploadJSONToExistingJob(jobID, obj_collection);
		System.out.println("Bulk upload status: \n" + response);

	}
	
	public void uploadDataToNewJob(CrowdFlowerClient cf_client) {
		
		System.out.println("Upload data to new job ...");
		JsonObject obj = new JsonObject();
		
		obj.addProperty("column_1", "Èum¹um¾um");
		obj.addProperty("column_2", "Ÿber šber †Š");
		
		String response = cf_client.bulkUploadJSONToNewJob(obj.toString());
		System.out.println("Bulk upload status: \n" + response);
		
	}

	public void renameExistingJob(CrowdFlowerClient cf_client, String job_id, String new_title) {
		
		System.out.println("Renaming existing job...");
		String response = cf_client.changeJobTitle(job_id, new_title);
		System.out.println("Rename status: " + response);
		
	}
	
	
	public void getAllJobs(CrowdFlowerClient cf_client) {
		System.out.println("Get all jobs ...");
		String myJobs = cf_client.getAllJobs();
		System.out.println(myJobs);

	}
	
	
	
	public static void main(String [] args) {

		String myApiKey = "enter-your-api-key-here-or-pass-it-as-an-argument";
		String job_id = "142373";
		
		//apikey can be passed as an argument
		if (args.length > 0) {
			myApiKey = args[0];
		}
		
		
		CrowdFlowerClient cf_client = new CrowdFlowerClient(myApiKey);
		CrowdFlowerExample demo = new CrowdFlowerExample();

		//demo.createEmptyJob(cf_client);
				
		//demo.getJobData(cf_client, job_id);

		
		//demo.uploadDatatoExistingJob(cf_client, job_id);
		

		//demo.uploadDataToNewJob(cf_client);
		
		demo.renameExistingJob(cf_client, job_id, "New title of the job");
		
		//demo.getAllJobs(cf_client);
		
		
		
	}
	
	
	
}
