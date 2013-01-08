package com.zemanta.example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

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
		String response = cf_client.getJob(jobID);
		System.out.println(response);
	}
	
	
	public void copyJob(CrowdFlowerClient cf_client, String jobID) {
		System.out.println("Copying job with default params...");
		
		String response = cf_client.copyJob(jobID);
		System.out.println(response);
		
	}
	
	public void uploadDatatoExistingJob(CrowdFlowerClient cf_client, String jobID) {
		
		System.out.println("Upload data to existing job ...");
		JsonArray data =  new JsonArray();
	
		JsonObject obj = new JsonObject();
		JsonObject obj2 = new JsonObject();
		
		obj.addProperty("column_1", "11");
		obj.addProperty("column_2", "2212");
		
		obj2.addProperty("column_1", "2321");
		obj2.addProperty("column_2", "");
		
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
		
		obj.addProperty("column_1", "čšžČŠŽ");
		obj.addProperty("column_2", "Über öber äpfel");
		
		String response = cf_client.bulkUploadJSONToNewJob(obj.toString());
		System.out.println("Bulk upload status: \n" + response);
		
	}

	public void renameExistingJob(CrowdFlowerClient cf_client, String job_id, String new_title) {
		
		System.out.println("Renaming existing job...");
		String response = cf_client.changeJobTitle(job_id, new_title);
		System.out.println("Rename status: " + response);
		
	}
	
	public void updateJobCML(CrowdFlowerClient cf_client, String job_id, String cml) {
		
		System.out.println("Update CML...");
		String response = cf_client.upateJobCML(job_id, cml);
		System.out.println("Update status: " + response);
		
	}
	
	
	public void getAllJobs(CrowdFlowerClient cf_client) {
		System.out.println("Get all jobs 1...");
		String myJobs = cf_client.getAllJobs();
		System.out.println(myJobs);

	}
	
	public void getJobUnits(CrowdFlowerClient cf_client, String job_id) {
		System.out.println("Get units of a job");
		String units = cf_client.getJobUnits(job_id);
		System.out.println(units);
	}
	
	
	
	public static void main(String [] args) {

		String myApiKey = "enter-your-api-key-here-or-pass-it-as-an-argument";
		String job_id = "154342";
		
		//apikey can be passed as an argument
		if (args.length > 0) {
			myApiKey = args[0];
		}
		
		
		CrowdFlowerClient cf_client = new CrowdFlowerClient(myApiKey);
		CrowdFlowerExample demo = new CrowdFlowerExample();
		cf_client.setTimeout(5000);

		//demo.createEmptyJob(cf_client);
				
		//demo.getJobData(cf_client, job_id);

		
		//demo.uploadDatatoExistingJob(cf_client, job_id);
		

		//demo.uploadDataToNewJob(cf_client);
		
		//demo.renameExistingJob(cf_client, job_id, "Biting the dust 123");
		
		//demo.copyJob(cf_client, job_id);
		
		demo.getAllJobs(cf_client);
		
		//demo.getJobUnits(cf_client, job_id);
	
		/*String entityType = "test";
		String reconSearchUrl = "http://www.example.com";
		String recon = "test";
		String cml = "<p>" + 
				  entityType + ":&#xA0;" +
				  "{{anchor}}<br />" + entityType + "'s profile page:&#xA0;<a href=\"{{link}}\" target=\"_blank\" id=\"\">" +
				  "{{link}}</a></p><br />" +
				  "<hr />" +
				  "<p>&#xA0;<b>FIRST</b> check suggested links:</p>" +
					"<ol type=\"1\">" + 
					"<li>Suggestion 1: <a href=\"{{suggestion_url_1}}\" target=\"_blank\">" + 
					"{{suggestion_name_1}}</a></li>" + 
					"<li>Suggestion 2: <a href=\"{{suggestion_url_2}}\" target=\"_blank\">" + 
					"{{suggestion_name_2}}</a></li>" +
					"<li>Suggestion 3: <a href=\"{{suggestion_url_3}}\" target=\"_blank\">" +
					"{{suggestion_name_3}}</a></li>" + 
					"<li>None of the above matches (<a target=\"_blank\" href=\""+ reconSearchUrl + "\">find page on your own</a>)</li>" +
					"</ol>" + 
					"<cml:select label=\"Best suggestion\" validates=\"required\" gold=\"true\" instructions=\"Select the best option for this "+ entityType +".\">" +
					"    <cml:option label=\"Suggestion 1\" value=\"Suggestion 1\"></cml:option>" +
					"    <cml:option label=\"Suggestion 2\" value=\"Suggestion 2\"></cml:option>" +
					"    <cml:option label=\"Suggestion 3\" value=\"Suggestion 3\"></cml:option>" +
					"    <cml:option label=\"None of the above\" value=\"None of the above\"></cml:option>" +
					"  </cml:select>" +
			"<cml:text label=\"Enter "+ recon + " link\" gold=\"true\" only-if=\"best_suggestion:[4]\" instructions=\"Find " + recon + " page for this " + entityType + " and paste it in this field\"  validates=\"url:['non-search']\">"  +
			"</cml:text>";
		
		System.out.println("\n" + cml + "\n");
		
		demo.updateJobCML(cf_client, job_id, cml);*/
		
	}
	
	
	
}
