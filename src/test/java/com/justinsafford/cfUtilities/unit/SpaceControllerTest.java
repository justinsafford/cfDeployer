package com.justinsafford.cfUtilities.unit;

import com.justinsafford.cfUtilities.SpaceController;
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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SpaceControllerTest {
    @Mock
    CloudFoundryClient cloudFoundryClient;

    @InjectMocks
    SpaceController spaceController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    MockMvc mockMVC;

    @Before
    public void setupMock() {
        mockMVC = standaloneSetup(spaceController)
                .build();
    }

    @Test
    public void postToCreateSpaceCreatesANewCFSpace() throws Exception {
        mockMVC.perform(post("/space/{cloudClientId}", "cloud-client")
                .param("spaceName", "space-name")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(cloudFoundryClient, times(1)).createSpace(eq("space-name"));
    }
}
