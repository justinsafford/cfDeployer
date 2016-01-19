package com.justinsafford.cfUtilities.cloudClient;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class CloudController {

    CloudClientRepository cloudClientRepository;

    //TODO:Find a way to unit test this
    @RequestMapping(
            value = "/cloudClient",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCloudFoundryClient(@RequestBody CloudClientRequest cloudClientRequest) {
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

        cloudClientRepository.save(cloudFoundryClient);
    }
}
