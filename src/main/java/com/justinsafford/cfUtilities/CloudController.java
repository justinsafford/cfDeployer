package com.justinsafford.cfUtilities;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.MalformedURLException;
import java.net.URL;

public class CloudController {

    CloudClientRepository cloudClientRepository;

    @RequestMapping(
            value = "/cloudClient",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCloudFoundryClient(@RequestBody CloudClientRequest cloudClientRequest) {
//TODO:Properly test everything here
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
