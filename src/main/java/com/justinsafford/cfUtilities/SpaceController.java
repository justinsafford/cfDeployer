package com.justinsafford.cfUtilities;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SpaceController {
    CloudFoundryClient myCloudFoundryClient;

    @RequestMapping(
            value = "/space",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpace() {
        myCloudFoundryClient.createSpace("space-name");
    }
}
