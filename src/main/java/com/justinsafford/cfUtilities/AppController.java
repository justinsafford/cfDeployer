package com.justinsafford.cfUtilities;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class AppController {

    CloudFoundryClient cloudFoundryClient;

    @RequestMapping(
            value = "/app",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewApp(@RequestBody ApplicationRequest applicationRequest) {
        ArrayList<String> uris = new ArrayList<>();
        String uri = "http://" + applicationRequest.getApplicationName() + ".cfapps.io";
        uris.add(uri);

        ArrayList<String> services = new ArrayList<>();
        services.add(applicationRequest.getService());

        cloudFoundryClient.createApplication(applicationRequest.getApplicationName(), new Staging(), 1, uris, services);
    }
}
