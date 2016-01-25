package com.justinsafford.cfUtilities.spaces;

import com.justinsafford.cfUtilities.cloudClients.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClients.CloudClientRepository;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SpaceControllerTest {

    @Mock
    CloudClientRepository cloudClientRepository;

    @Mock
    DefaultCloudClientBuilder defaultCloudClientBuilder;

    @InjectMocks
    SpaceController spaceController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    MockMvc mockMvc;

    @Before
    public void setupMock() throws Exception {
        mockMvc = standaloneSetup(spaceController)
                .build();
    }

    @Test
    public void createSpaceCreatesANewSpaceWithinOrg() throws Exception {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(cloudClientRepository.findOne(anyString()))
                .thenReturn(cloudClientEntity);

        CloudFoundryClient cloudFoundryClient = mock(CloudFoundryClient.class);
        when(defaultCloudClientBuilder.generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(cloudFoundryClient);

        mockMvc.perform(post("/spaces")
                .accept(MediaType.APPLICATION_JSON)
                .param("cloudClientId", "cloud-id")
                .param("spaceName", "space-name")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(cloudClientRepository, times(1)).findOne("cloud-id");
        verify(defaultCloudClientBuilder, times(1)).generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void getAllSpacesRetrievesAllSpacesWithinOrg() throws Exception {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(cloudClientRepository.findOne(anyString()))
                .thenReturn(cloudClientEntity);

        CloudFoundryClient cloudFoundryClient = mock(CloudFoundryClient.class);
        when(defaultCloudClientBuilder.generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(cloudFoundryClient);

        mockMvc.perform(get("/spaces")
                .accept(MediaType.APPLICATION_JSON)
                .param("cloudClientId", "cloud-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cloudClientRepository, times(1)).findOne("cloud-id");
        verify(defaultCloudClientBuilder, times(1)).generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void deleteSpaceWithinOrg() throws Exception {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(cloudClientRepository.findOne(anyString()))
                .thenReturn(cloudClientEntity);

        CloudFoundryClient cloudFoundryClient = mock(CloudFoundryClient.class);
        when(defaultCloudClientBuilder.generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(cloudFoundryClient);

        mockMvc.perform(delete("/spaces/{spaceName}", "space-name")
                .accept(MediaType.APPLICATION_JSON)
                .param("cloudClientId", "cloud-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cloudClientRepository, times(1)).findOne("cloud-id");
        verify(defaultCloudClientBuilder, times(1)).generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString());
    }
}