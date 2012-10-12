package com.zemanta.example;

import com.zemanta.crowdflower.*;

public class CrowdFlowerExample {
	
	
	public static void main(String [] args) {

		String myApi = "4d7e7346df7aecae92259843ca7f7bbad14bdbe2";
		
		CrowdFlowerClient cf_client = new CrowdFlowerClient(myApi);
		
	
		System.out.println("-----------------------");
		
		String newEmptyJob = cf_client.create_empty_job();
		System.out.println(newEmptyJob);

		System.out.println("-----------------------");

		
		//get all jobs
		String myJobs = cf_client.get_all_jobs();
		System.out.println(myJobs);

		
	}
	
	
	
}