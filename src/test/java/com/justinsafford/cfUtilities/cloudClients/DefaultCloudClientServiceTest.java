package com.justinsafford.cfUtilities.cloudClients;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCloudClientServiceTest {

    @Mock
    CloudClientRepository cloudClientRepository;

    @InjectMocks
    DefaultCloudClientService defaultCloudClientService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void cloudClientServicePersistsNewCloudClientToRepository() throws MalformedURLException {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        when(cloudClientRepository.save(any(CloudClientEntity.class)))
                .thenReturn(cloudClientEntity);

        CloudClientRequest cloudClientRequest = new CloudClientRequest();
        URL url = new URL("HTTP", "api.run.pivotal.io", 80, "");
        defaultCloudClientService.addNewCloudClient(cloudClientRequest, url);

        verify(cloudClientRepository, times(1)).save(any(CloudClientEntity.class));
    }
}