package com.zemanta.crowdflower.integration;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Created by eracle on 18/11/15.
 */
public class ApiKeyFileTest  extends TestCase {

    @Test
    public void testPropertiesFileExists() throws Exception{
        URL resource = getClass().getResource("/default.properties");
        assertNotNull(resource);
        File file = new File(resource.getFile());
        assertTrue(file.exists());
        Properties properties = new Properties();


        try {
            properties.load(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String key = properties.getProperty("apikey");
        assertNotNull(key);
    }
}
