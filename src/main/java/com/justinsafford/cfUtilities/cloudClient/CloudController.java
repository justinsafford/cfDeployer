package com.justinsafford.cfUtilities.cloudClient;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CloudController {

    @Autowired
    CloudClientRepository cloudClientRepository;

    @Autowired
    DefaultCloudClientBuilder defaultCloudClientBuilder;

    @RequestMapping(
            value = "/cloudClients",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CloudClientResponse createCloudClient(@RequestBody CloudClientRequest cloudClientRequest) {

        CloudFoundryClient cloudFoundryClient = defaultCloudClientBuilder.generateCloudFoundryClient(
                cloudClientRequest.getCloudFoundryUsername(),
                cloudClientRequest.getCloudFoundryPassword(),
                cloudClientRequest.getCloudFoundryOrg(),
                cloudClientRequest.getCloudFoundrySpace());

        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        cloudClientEntity.setCloudClientId(UUID.randomUUID().toString());
        cloudClientEntity.setCloudUser(cloudClientRequest.getCloudFoundryUsername());
        cloudClientEntity.setCloudPass(cloudClientRequest.getCloudFoundryPassword());
        cloudClientEntity.setCloudOrg(cloudClientRequest.getCloudFoundryOrg());
        cloudClientEntity.setCloudSpace(cloudClientRequest.getCloudFoundrySpace());
        cloudClientEntity.setCloudUrl(cloudFoundryClient.getCloudControllerUrl());

        cloudClientRepository.save(cloudClientEntity);

        CloudClientResponse cloudClientResponse = new CloudClientResponse();
        cloudClientResponse.setCloudClientId(cloudClientEntity.getCloudClientId());

        return cloudClientResponse;
    }
}
