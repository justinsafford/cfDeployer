package com.justinsafford.cfUtilities.spaces;

import com.justinsafford.cfUtilities.cloudClients.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClients.CloudClientRepository;
import com.justinsafford.cfUtilities.cloudFoundryClientBuilder.CloudClientBuilder;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SpaceController {

    @Autowired
    CloudClientRepository cloudClientRepository;

    @Autowired
    CloudClientBuilder cloudClientBuilder;

    @RequestMapping(
            value = "/spaces",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpace(@RequestParam String cloudClientId,
                            @RequestParam String spaceName) {

        CloudClientEntity cloudClientEntity = cloudClientRepository.findOne(cloudClientId);

        CloudFoundryClient cloudFoundryClient = cloudClientBuilder.generateCloudFoundryClient(
                cloudClientEntity.getCloudUser(),
                cloudClientEntity.getCloudPass(),
                cloudClientEntity.getCloudOrg(),
                cloudClientEntity.getCloudSpace());

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

        CloudFoundryClient cloudFoundryClient = cloudClientBuilder.generateCloudFoundryClient(
                cloudClientEntity.getCloudUser(),
                cloudClientEntity.getCloudPass(),
                cloudClientEntity.getCloudOrg(),
                cloudClientEntity.getCloudSpace());

        List<CloudSpace> cloudSpacesFound = cloudFoundryClient.getSpaces();
        List<String> cloudSpaceList = new ArrayList<>();
        cloudSpacesFound
                .forEach(cloudSpaceFound -> cloudSpaceList.add(cloudSpaceFound.getName()));

        return cloudSpaceList;
    }

    @RequestMapping(
            value = "/spaces/{spaceName}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteSpace(@PathVariable String spaceName,
                            @RequestParam String cloudClientId) {

        CloudClientEntity cloudClientEntity = cloudClientRepository.findOne(cloudClientId);

        CloudFoundryClient cloudFoundryClient = cloudClientBuilder.generateCloudFoundryClient(
                cloudClientEntity.getCloudUser(),
                cloudClientEntity.getCloudPass(),
                cloudClientEntity.getCloudOrg(),
                cloudClientEntity.getCloudSpace());

        cloudFoundryClient.deleteSpace(spaceName);
    }
}
