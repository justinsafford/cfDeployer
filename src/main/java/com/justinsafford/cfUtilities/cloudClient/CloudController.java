package com.justinsafford.cfUtilities.cloudClient;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CloudController {

    @Autowired
    CloudClientService cloudClientService;

    @Autowired
    CloudClientBuilder cloudClientBuilder;

    @RequestMapping(
            value = "/cloudClients",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CloudClientResponse createCloudClient(@RequestBody CloudClientRequest cloudClientRequest) {

        CloudFoundryClient cloudFoundryClient = cloudClientBuilder.generateCloudFoundryClient(
                cloudClientRequest.getCloudFoundryUsername(),
                cloudClientRequest.getCloudFoundryPassword(),
                cloudClientRequest.getCloudFoundryOrg(),
                cloudClientRequest.getCloudFoundrySpace());

        CloudClientEntity cloudClientEntity =
                cloudClientService.addNewCloudClient(cloudClientRequest, cloudFoundryClient.getCloudControllerUrl());

        CloudClientResponse cloudClientResponse = new CloudClientResponse();
        cloudClientResponse.setCloudClientId(cloudClientEntity.getCloudClientId());

        return cloudClientResponse;
    }
}
