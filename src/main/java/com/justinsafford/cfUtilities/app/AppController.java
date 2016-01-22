package com.justinsafford.cfUtilities.app;

import com.justinsafford.cfUtilities.cloudClient.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClient.CloudClientRepository;
import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@RestController
public class AppController {

    @Autowired
    CloudClientRepository cloudClientRepository;

    @RequestMapping(
            value = "/application/{cloudClientId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createApp(@PathVariable String cloudClientId,
                          @RequestBody ApplicationRequest applicationRequest) {

        CloudClientEntity cloudClientEntity = cloudClientRepository.findOne(cloudClientId);

        CloudCredentials cloudCredentials = new CloudCredentials(cloudClientEntity.getCloudUser(), cloudClientEntity.getCloudPass());
        URL url = null;
        try {
            url = new URL("HTTP", "api.run.pivotal.io", 80, "");
        } catch (MalformedURLException e) {
            System.out.println("something bad happened here");
        }
        String cloudOrg = cloudClientEntity.getCloudOrg();
        String cloudSpace = cloudClientEntity.getCloudSpace();
        CloudFoundryClient cloudFoundryClient = new CloudFoundryClient(
                cloudCredentials,
                url,
                cloudOrg,
                cloudSpace
        );

        ArrayList<String> uris = new ArrayList<>();
        String uri = "http://" + applicationRequest.getApplicationName() + ".cfapps.io";
        uris.add(uri);

        ArrayList<String> services = new ArrayList<>();
        services.add(applicationRequest.getService());

        cloudFoundryClient.createApplication(applicationRequest.getApplicationName(), new Staging(), 1024, uris, services);
    }
}
