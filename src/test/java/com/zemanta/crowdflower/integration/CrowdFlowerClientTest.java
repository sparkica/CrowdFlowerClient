package com.zemanta.crowdflower.integration;

import com.zemanta.crowdflower.client.CrowdFlowerClient;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by eracle on 17/11/15.
 */
public class CrowdFlowerClientTest extends TestCase {

    private CrowdFlowerClient sut;

    private String apikey;

    public CrowdFlowerClientTest(){
        super();
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/default.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.apikey = properties.getProperty("apikey");

    }


    @Before
    public void setUp() {
        //System.out.println(apikey);
        this.sut = new CrowdFlowerClient(this.apikey, 2000);
    }

    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void testGetAllJobs() throws Exception {
        //System.out.println("Get all jobs 1...");
        String myJobs = sut.getAllJobs();
        assertTrue(myJobs.contains("\"status\":200, \"response\" :"));

        assertFalse(myJobs.startsWith("{\"status\":40"));
        //System.out.println(myJobs);
    }

    @Test
    public void testCopyJob_unvalidId() throws Exception {
        String response = sut.copyJob("000000");
        assertTrue(response.equals("{\"status\":404, \"response\" : {\"error\": {\"message\":\"We couldn't find what you were looking for.\"}}\n" +
                "}"));
    }

    @Test
    public void testCopyJob_validId() throws Exception {
       // System.out.println("Copying job with default params...");
        String myJobs = sut.getAllJobs();
        //System.out.println(myJobs);

        assertFalse(myJobs.startsWith("{\"status\":40"));

        String old_id = myJobs.split("id\":")[1].split(",\"options\":")[0];
        //System.out.println(id);
        String response = sut.copyJob(old_id);
        assertTrue(response.startsWith("{\"status\":200, \"response\" : {\"id\":"));
        //System.out.println(response);
        String new_job_id = response.split("id\":")[1].split(",\"options\":")[0];

        //check if an integer
        assertTrue(new_job_id.matches("^-?\\d+$"));

        //String del_job_resp = sut.deleteJob(new_job_id);
        //System.out.println(del_job_resp);


    }
    @Test
    public void testCopyJob_withGolden() throws Exception {
        // System.out.println("Copying job with default params...");
        String myJobs = sut.getAllJobs();
        //System.out.println(myJobs);

        assertFalse(myJobs.startsWith("{\"status\":40"));

        String old_id = myJobs.split("id\":")[1].split(",\"options\":")[0];
        //System.out.println(id);
        String response = sut.copyJob_onlyGolden(old_id);
        assertTrue(response.startsWith("{\"status\":200, \"response\" : {\"id\":"));
        System.out.println(response);
        String new_job_id = response.split("id\":")[1].split(",\"options\":")[0];

        //check if an integer
        assertTrue(new_job_id.matches("^-?\\d+$"));

        //String del_job_resp = sut.deleteJob(new_job_id);
        //System.out.println(del_job_resp);
    }
    public void testChangeJobTitle() throws Exception {
        // System.out.println("Copying job with default params...");
        String myJobs = sut.getAllJobs();
        //System.out.println(myJobs);

        assertFalse(myJobs.startsWith("{\"status\":40"));
        String old_id = myJobs.split("id\":")[1].split(",\"options\":")[0];
        //System.out.println(id);
        String response = sut.copyJob(old_id);
        assertTrue(response.startsWith("{\"status\":200, \"response\" : {\"id\":"));
        //System.out.println(response);
        String new_job_id = response.split("id\":")[1].split(",\"options\":")[0];

        //check if an integer
        assertTrue(new_job_id.matches("^-?\\d+$"));


        String rename_response = sut.changeJobTitle(new_job_id, "renamed test job");
        assertTrue(rename_response.startsWith("{\"status\":200, \"response\" : {\"id\":"));
        //System.out.println(rename_response);
        //System.out.println("Renaming existing job...");
        //System.out.println("Rename status: " + rename_response);



    }
    //@Test
    public void testBulkUploadToExistingJob() throws Exception {

        // System.out.println("Copying job with default params...");
        String myJobs = sut.getAllJobs();
        //System.out.println(myJobs);
        assertFalse(myJobs.startsWith("{\"status\":40"));
        String old_id = myJobs.split("id\":")[1].split(",\"options\":")[0];
        //System.out.println(id);
        String response = sut.copyJob_onlyGolden(old_id);
        assertTrue(response.startsWith("{\"status\":200, \"response\" : {\"id\":"));
        //System.out.println(response);
        String new_job_id = response.split("id\":")[1].split(",\"options\":")[0];

        //check if an integer
        assertTrue(new_job_id.matches("^-?\\d+$"));


        String rename_response = sut.changeJobTitle(new_job_id, "Riconoscere L'entita' - T018");
        assertTrue(rename_response.startsWith("{\"status\":200, \"response\" : {\"id\":"));

        File csv = new File(getClass().getResource("/out018.csv").toURI());
        String csv_str = new String(Files.readAllBytes(Paths.get(csv.getPath())));
//        System.out.println(csv_str.substring(0,100));
        //System.out.println(csv_str);

        String up_resp = sut.bulkUploadToExistingJob_csvFile(new_job_id,csv_str);
        assertTrue(up_resp.startsWith("{\"status\":200, \"response\" : {\"id\":"));
//        System.out.println(up_resp);

    }
    //TODO:
    //1. WAIT FOR TEST AND ROWS TO BE CORRECTLY LOADED
    //2. JOB START METHOD
    //3. job is finished?
    //3. JOB ROWS DOWNLOAD


    @Test
    public void testCancelJob() throws Exception {
        //TODO
    }
/*
    @Test
    public void testGetJob() throws Exception {
        System.out.println("Geting data about particular job ...");
        String response = sut.getJob(jobID);
        System.out.println(response);

    }
    */
    public void testCreateNewJobWithoutData() throws Exception {

    }

    public void testCreateNewJobWithoutData1() throws Exception {

    }

    public void testBulkUploadToNewJob() throws Exception {

    }

    public void testBulkUploadJSONToExistingJob() throws Exception {

    }

    public void testBulkUploadJSONToNewJob() throws Exception {

    }







    public void testUpdateJob() throws Exception {

    }


    public void testUpateJobCML() throws Exception {

    }




    public void testGetLegend() throws Exception {

    }

    public void testGetJobUnits() throws Exception {

    }
}