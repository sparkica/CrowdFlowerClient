package com.zemanta.example;

import com.google.gson.JsonObject;
import com.zemanta.crowdflower.client.*;

public class CrowdFlowerExample {
	
	
	public static void main(String [] args) {

		String myApi = "4d7e7346df7aecae92259843ca7f7bbad14bdbe2";
		String jobID = "135723";
		String jobID2 = "140536";
		
		CrowdFlowerClient cf_client = new CrowdFlowerClient(myApi);

		//create new job
		//String newEmptyJob = cf_client.create_empty_job();
		//System.out.println(newEmptyJob);

	
		System.out.println("-----------------------");

		//get data about specific job
		//String job = cf_client.read(jobID);
		//System.out.println(job);
		
		JsonObject obj = new JsonObject();
		obj.addProperty("column_1", "some content");
		obj.addProperty("column_2", "other content");
		obj.addProperty("column_3", "yep?");
						
		String res = cf_client.bulkUploadJSON(jobID2, obj.toString());
		System.out.println("bulk upload: " + res);
		
		System.out.println("-----------------------");

		
		//get all jobs
		String myJobs = cf_client.getAllJobs();
		System.out.println(myJobs);

		
	}
	
	
	
}