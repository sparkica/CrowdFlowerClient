package com.zemanta.example;

import com.zemanta.crowdflower.client.*;

public class CrowdFlowerExample {
	
	
	public static void main(String [] args) {

		String myApi = "4d7e7346df7aecae92259843ca7f7bbad14bdbe2";
		String jobID = "135723";
		
		CrowdFlowerClient cf_client = new CrowdFlowerClient(myApi);

		//create new job
		//String newEmptyJob = cf_client.create_empty_job();
		//System.out.println(newEmptyJob);

	
		System.out.println("-----------------------");

		//get data about specific job
		//String job = cf_client.read(jobID);
		//System.out.println(job);
		

		System.out.println("-----------------------");

		
		//get all jobs
		String myJobs = cf_client.get_all_jobs();
		System.out.println(myJobs);

		
	}
	
	
	
}