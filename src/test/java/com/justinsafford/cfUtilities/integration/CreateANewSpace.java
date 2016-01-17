package com.justinsafford.cfUtilities.integration;

import com.justinsafford.cfUtilities.Application;
import com.justinsafford.cfUtilities.Configurations;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.MalformedURLException;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, Configurations.class})
@WebAppConfiguration
public class CreateANewSpace {

    @Autowired
    CloudFoundryClient myCloudFoundryClient;

    @Test
    public void addNewSpace() throws MalformedURLException {
        myCloudFoundryClient.createSpace("testSpace");
    }
}
