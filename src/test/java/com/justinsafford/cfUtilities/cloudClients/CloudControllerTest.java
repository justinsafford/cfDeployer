package com.justinsafford.cfUtilities.cloudClients;

import com.justinsafford.cfUtilities.cloudFoundryClientBuilder.DefaultCloudClientBuilder;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CloudControllerTest {

    @Mock
    DefaultCloudClientBuilder defaultCloudClientBuilder;

    @Mock
    DefaultCloudClientService defaultCloudClientService;

    @InjectMocks
    CloudController cloudController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    MockMvc mockMvc;

    @Before
    public void setupMock() throws Exception {
        mockMvc = standaloneSetup(cloudController)
                .build();
    }

    @Test
    public void createCloudClientCreatesANewCloudFoundryClient() throws Exception {
        CloudFoundryClient cloudFoundryClient = mock(CloudFoundryClient.class);
        when(defaultCloudClientBuilder.generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(cloudFoundryClient);

        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(defaultCloudClientService.addNewCloudClient(any(CloudClientRequest.class), any(URL.class)))
                .thenReturn(cloudClientEntity);

                mockMvc.perform(post("/cloudClients")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                        .andExpect(status().isCreated());
    }
}