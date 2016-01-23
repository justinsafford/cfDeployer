package com.justinsafford.cfUtilities.space;

import com.justinsafford.cfUtilities.cloudClient.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClient.CloudClientRepository;
import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SpaceController {

    @Autowired
    CloudClientRepository cloudClientRepository;

    @RequestMapping(
            value = "/spaces/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpace(@RequestParam String cloudClientId,
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
        cloudFoundryClient.associateDeveloperWithSpace(spaceName);
    }

    @RequestMapping(
            value = "/spaces",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllSpaces(@RequestParam String cloudClientId) {

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

        List<CloudSpace> cloudSpacesFound = cloudFoundryClient.getSpaces();
        List<String> cloudSpaceList = new ArrayList<>();
        cloudSpacesFound
                .forEach(cloudSpaceFound -> cloudSpaceList.add(cloudSpaceFound.getName()));

        return cloudSpaceList;
    }
}
