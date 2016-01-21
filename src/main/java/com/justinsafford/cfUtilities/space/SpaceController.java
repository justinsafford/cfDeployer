package com.justinsafford.cfUtilities.space;

import com.justinsafford.cfUtilities.cloudClient.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClient.CloudClientRepository;
import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class SpaceController {

    @Autowired
    CloudClientRepository cloudClientRepository;

    @RequestMapping(
            value = "/space/{cloudClientId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpace(@PathVariable String cloudClientId,
                            @RequestParam String spaceName) {

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

        cloudFoundryClient.createSpace(spaceName);
    }
}
