package com.justinsafford.cfUtilities.integration;

import com.justinsafford.cfUtilities.Application;
import com.justinsafford.cfUtilities.cloudClient.CloudClientRepository;
import com.justinsafford.cfUtilities.cloudTestHelperClasses.CloudTestCreds;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CreateCloudClient {
    @Autowired
    CloudClientRepository cloudClientRepository;

    @Autowired
    CloudTestCreds testCloudCreds;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setupMock() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .defaultRequest(get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .alwaysExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .build();
    }

    @Before
    public void wipeDatabase() {
        cloudClientRepository.deleteAll();
    }

    //TODO:Find a better way to test this
    @Ignore
    @Test
    public void createNewCloudFoundryClient() throws Exception {
        mockMvc.perform(post("/cloudClient")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cloudFoundryUsername\":\""+testCloudCreds.cloudUser+"\"," +
                        "\"cloudFoundryPassword\":\""+testCloudCreds.cloudPass+"\"," +
                        "\"cloudFoundryOrg\":\""+testCloudCreds.cloudOrg+"\"," +
                        "\"cloudFoundrySpace\":\""+testCloudCreds.cloudSpace+"\"}"))
                .andExpect(status().isCreated());

        List<CloudFoundryClient> cloudFoundryClientList = new ArrayList<>();
        assertThat(cloudFoundryClientList.size(), is(1));
    }
}
