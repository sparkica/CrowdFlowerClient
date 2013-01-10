package com.zemanta.crowdflower.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.zemanta.crowdflower.client.CrowdFlowerClient;

public class CrowdFlowerClientTest {

	HttpURLConnection mockConnection;
	CrowdFlowerClient client;
	
	@Before
	public void setUp() throws Exception {
		
		mockConnection = mock(HttpURLConnection.class);
		
		client = new CrowdFlowerClient("fake-api-key",1500);
		client.setHttpURLConnection(mockConnection);
	}

	@After
	public void tearDown() throws Exception {
		mockConnection = null;
		client = null;
	}

	@Test
	public void testGetJSONReturnsErrorResponse() throws Exception{
		String expected = "{\"status\":\"ERROR\", \"error\":{\"message\": \"An exception of type Unspecified Exception occured. Details: null\"}}";		
		assertEquals(expected, client.getAllJobs());
	}

	@Test
	public void testGetJSONReturnsOKResponse() throws Exception{
		Mockito.when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
		String str = "{\"id\":\"12345\", \"instructions\":\"Test\"}";
		String expected = "{\"status\":200, \"response\" : " + str + "\n}";		 
		InputStream is = new ByteArrayInputStream(str.getBytes());
		Mockito.when(mockConnection.getInputStream()).thenReturn(is);
		
		assertEquals(expected, client.getAllJobs());

	}

	
	
}
