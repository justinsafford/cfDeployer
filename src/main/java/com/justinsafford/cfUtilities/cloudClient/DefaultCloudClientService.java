package com.justinsafford.cfUtilities.cloudClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.UUID;

@Service
public class DefaultCloudClientService implements CloudClientService{

    @Autowired
    CloudClientRepository cloudClientRepository;

    @Override
    public CloudClientEntity addNewCloudClient(CloudClientRequest cloudClientRequest, URL url) {
        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        cloudClientEntity.setCloudClientId(UUID.randomUUID().toString());
        cloudClientEntity.setCloudUser(cloudClientRequest.getCloudFoundryUsername());
        cloudClientEntity.setCloudPass(cloudClientRequest.getCloudFoundryPassword());
        cloudClientEntity.setCloudOrg(cloudClientRequest.getCloudFoundryOrg());
        cloudClientEntity.setCloudSpace(cloudClientRequest.getCloudFoundrySpace());
        cloudClientEntity.setCloudUrl(url);

        cloudClientRepository.save(cloudClientEntity);

        return cloudClientEntity;
    }
}
