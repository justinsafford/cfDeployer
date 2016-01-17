package com.justinsafford.cfUtilities.unit;

import com.justinsafford.cfUtilities.AppController;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.Staging;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AppControllerTest {
    @Mock
    CloudFoundryClient cloudFoundryClient;

    @InjectMocks
    AppController appController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    MockMvc mockMVC;

    @Before
    public void setupMock() {
        mockMVC = standaloneSetup(appController)
                .build();
    }

    @Test
    public void postToCreateApplicationCreatesANewCFApplication() throws Exception {
        mockMVC.perform(post("/app")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"applicationName\":\"app-name\"," +
                        "\"memory\":1," +
                        "\"service\":\"service-name\"}"))
                .andExpect(status().isCreated());

        List<String> serviceList = new ArrayList<>();
        serviceList.add("service-name");
        verify(cloudFoundryClient, times(1)).createApplication(
                eq("app-name"), any(Staging.class), eq(1), anyList(), eq(serviceList));
    }
}
