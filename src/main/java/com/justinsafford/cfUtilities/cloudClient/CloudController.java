package com.justinsafford.cfUtilities.cloudClient;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@RestController
public class CloudController {

    @Autowired
    CloudClientRepository cloudClientRepository;

    //TODO:Find a way to unit test this
    @RequestMapping(
            value = "/cloudClient",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CloudClientResponse createCloudFoundryClient(@RequestBody CloudClientRequest cloudClientRequest) {
        CloudCredentials cloudCredentials = new CloudCredentials(
                cloudClientRequest.getCloudFoundryUsername(),
                cloudClientRequest.getCloudFoundryPassword());

        URL url = null;
        try {
            url = new URL("HTTP", "api.run.pivotal.io", 80, "");
        } catch (MalformedURLException e) {

        }

        CloudFoundryClient cloudFoundryClient =
                new CloudFoundryClient(
                        cloudCredentials,
                        url,
                        cloudClientRequest.getCloudFoundryOrg(),
                        cloudClientRequest.getCloudFoundrySpace());

        CloudClientEntity cloudClientEntity = new CloudClientEntity();
        cloudClientEntity.setCloudClientId(UUID.randomUUID().toString());
        cloudClientEntity.setCloudUser(cloudClientRequest.getCloudFoundryUsername());
        cloudClientEntity.setCloudUser(cloudClientRequest.getCloudFoundryUsername());
        cloudClientEntity.setCloudPass(cloudClientRequest.getCloudFoundryPassword());
        cloudClientEntity.setCloudOrg(cloudClientRequest.getCloudFoundryOrg());
        cloudClientEntity.setCloudSpace(cloudClientRequest.getCloudFoundrySpace());
        cloudClientEntity.setCloudUrl(url);

        cloudClientRepository.save(cloudClientEntity);


        CloudClientResponse cloudClientResponse = new CloudClientResponse();
        cloudClientResponse.setCloudClientId(cloudClientEntity.getCloudClientId());

        return cloudClientResponse;
    }
}
