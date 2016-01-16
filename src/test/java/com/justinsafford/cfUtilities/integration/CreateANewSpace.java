package com.justinsafford.cfUtilities.integration;

import com.justinsafford.cfUtilities.Application;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CreateANewSpace {

    @Autowired
    CloudFoundryClient myCloudFoundryClient;

    @Test
    public void addNewSpace() throws MalformedURLException {
        myCloudFoundryClient.createSpace("testSpace");
    }
}
