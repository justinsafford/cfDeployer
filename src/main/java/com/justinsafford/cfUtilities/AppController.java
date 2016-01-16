package com.justinsafford.cfUtilities;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AppController {

    CloudFoundryClient cloudFoundryClient;

    @RequestMapping(
            value = "/app",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewApp() {

        ArrayList<String> uris = new ArrayList<>();
        uris.add("app-uri");
        ArrayList<String> services = new ArrayList<>();
        services.add("app-service");
        cloudFoundryClient.createApplication("app-name", new Staging(), 1, uris, services);
    }
}
