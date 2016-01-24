package apps;

import com.justinsafford.cfUtilities.apps.ApplicationController;
import com.justinsafford.cfUtilities.cloudClients.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClients.CloudClientRepository;
import com.justinsafford.cfUtilities.cloudFoundryClientBuilder.DefaultCloudClientBuilder;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.CloudSpace;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ApplicationControllerTest {

    @Mock
    CloudClientRepository cloudClientRepository;

    @Mock
    DefaultCloudClientBuilder defaultCloudClientBuilder;

    @InjectMocks
    ApplicationController applicationController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    MockMvc mockMvc;

    @Before
    public void setupMock() throws Exception {
        mockMvc = standaloneSetup(applicationController)
                .build();
    }

    @Test
    public void createAppCreatesANewAppWithinSpace() throws Exception {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(cloudClientRepository.findOne(anyString()))
                .thenReturn(cloudClientEntity);

        CloudFoundryClient cloudFoundryClient = mock(CloudFoundryClient.class);
        when(defaultCloudClientBuilder.generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(cloudFoundryClient);

        mockMvc.perform(post("/applications")
                .accept(MediaType.APPLICATION_JSON)
                .param("cloudClientId", "cloud-id")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllAppsWithinASpace() throws Exception {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(cloudClientRepository.findOne(anyString()))
                .thenReturn(cloudClientEntity);

        CloudFoundryClient cloudFoundryClient = mock(CloudFoundryClient.class);
        when(defaultCloudClientBuilder.generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(cloudFoundryClient);

        mockMvc.perform(get("/applications")
                .accept(MediaType.APPLICATION_JSON)
                .param("cloudClientId", "cloud-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getDetailedInfoOnAppWithinASpace() throws Exception {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(cloudClientRepository.findOne(anyString()))
                .thenReturn(cloudClientEntity);

        CloudFoundryClient cloudFoundryClient = mock(CloudFoundryClient.class);
        when(defaultCloudClientBuilder.generateCloudFoundryClient(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(cloudFoundryClient);

        CloudApplication cloudApplication = mock(CloudApplication.class);
        when(cloudFoundryClient.getApplication("app-name"))
                .thenReturn(cloudApplication);

        CloudSpace cloudSpace = new CloudSpace(null, "", null);
        when(cloudApplication.getSpace()).thenReturn(cloudSpace);
        when(cloudApplication.getState()).thenReturn(CloudApplication.AppState.valueOf("STARTED"));

        mockMvc.perform(get("/applications/{appName}", "app-name")
                .accept(MediaType.APPLICATION_JSON)
                .param("cloudClientId", "cloud-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}