package com.justinsafford.cfUtilities.unit;

import com.justinsafford.cfUtilities.CloudClientRepository;
import com.justinsafford.cfUtilities.CloudController;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CloudClientControllerTest {
    @Mock
    CloudClientRepository cloudClientRepository;

    @Mock
    CloudFoundryClient cloudFoundryClient;

    @InjectMocks
    CloudController cloudController;

    MockMvc mockMvc;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setupMock() {
        mockMvc = standaloneSetup(cloudController)
                .build();
    }

    //TODO:Get this f'ing test working
    @Ignore
    @Test
    public void postToCreateCloudClientCreatesAndSavesCloudDetailsToDatabase() throws Exception {

//        cloudFoundryClient = PowerMockito.mock(CloudFoundryClient.class);//(mock(CloudCredentials.class), mock(URL.class), anyString(), anyString());

//        whenNew(CloudFoundryClient.class).withAnyArguments().thenReturn(any(CloudFoundryClient.class));



//        CloudFoundryClient expectedCloudFoundryClient = mock(CloudFoundryClient.class);
//        when(cloudClientRepository.save(any(CloudFoundryClient.class)))
//                .thenReturn(expectedCloudFoundryClient);

        mockMvc.perform(post("/cloudClient")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cloudFoundryUsername\":\"cf-username\"," +
                        "\"cloudFoundryPassword\":\"cf-password\"," +
                        "\"cloudFoundryOrg\":\"cf-org\"," +
                        "\"cloudFoundrySpace\":\"cf-space\"}"))
                .andExpect(status().isCreated());

//        verify(cloudClientRepository, times(1)).save(any(CloudFoundryClient.class));
    }
}
