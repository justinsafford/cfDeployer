package com.justinsafford.cfUtilities.apps;

import com.justinsafford.cfUtilities.cloudClients.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClients.CloudClientRepository;
import com.justinsafford.cfUtilities.cloudFoundryClientBuilder.CloudClientBuilder;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    CloudClientRepository cloudClientRepository;

    @Autowired
    CloudClientBuilder cloudClientBuilder;

    @RequestMapping(
            value = "/applications",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createApp(@RequestParam String cloudClientId,
                          @RequestBody ApplicationRequest applicationRequest) {

        CloudClientEntity cloudClientEntity = cloudClientRepository.findOne(cloudClientId);

        CloudFoundryClient cloudFoundryClient = cloudClientBuilder.generateCloudFoundryClient(
                cloudClientEntity.getCloudUser(),
                cloudClientEntity.getCloudPass(),
                cloudClientEntity.getCloudOrg(),
                cloudClientEntity.getCloudSpace());

        ArrayList<String> uris = new ArrayList<>();
        String uri = "http://" + applicationRequest.getApplicationName() + ".cfapps.io";
        uris.add(uri);

        ArrayList<String> services = new ArrayList<>();
        services.add(applicationRequest.getService());

        cloudFoundryClient.createApplication(applicationRequest.getApplicationName(), new Staging(), 1024, uris, services);
    }

    @RequestMapping(
            value = "/applications",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllApps(@RequestParam String cloudClientId) {

        CloudClientEntity cloudClientEntity = cloudClientRepository.findOne(cloudClientId);

        CloudFoundryClient cloudFoundryClient = cloudClientBuilder.generateCloudFoundryClient(
                cloudClientEntity.getCloudUser(),
                cloudClientEntity.getCloudPass(),
                cloudClientEntity.getCloudOrg(),
                cloudClientEntity.getCloudSpace());

        List<CloudApplication> cloudAppsFound = cloudFoundryClient.getApplications();
        List<String> cloudAppsList = new ArrayList<>();

        cloudAppsFound
                .forEach(cloudApplication -> cloudAppsList.add(cloudApplication.getName()));

        return cloudAppsList;
    }

    @RequestMapping(
            value = "/applications/{appName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse getAppDetails(@RequestParam String cloudClientId,
                                             @PathVariable String appName) {

        CloudClientEntity cloudClientEntity = cloudClientRepository.findOne(cloudClientId);

        CloudFoundryClient cloudFoundryClient = cloudClientBuilder.generateCloudFoundryClient(
                cloudClientEntity.getCloudUser(),
                cloudClientEntity.getCloudPass(),
                cloudClientEntity.getCloudOrg(),
                cloudClientEntity.getCloudSpace());

        CloudApplication cloudApplicationFound = cloudFoundryClient.getApplication(appName);

        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setAppName(cloudApplicationFound.getName());
        applicationResponse.setStaging(cloudApplicationFound.getStaging());
        applicationResponse.setInstances(cloudApplicationFound.getInstances());
        applicationResponse.setRunningInstances(cloudApplicationFound.getRunningInstances());
        applicationResponse.setMemory(cloudApplicationFound.getMemory());
        applicationResponse.setDiskQuota(cloudApplicationFound.getDiskQuota());
        applicationResponse.setUris(cloudApplicationFound.getUris());
        applicationResponse.setServices(cloudApplicationFound.getServices());
        applicationResponse.setEnvVariables(cloudApplicationFound.getEnv());

        applicationResponse.setSpaceName(cloudApplicationFound.getSpace().getName());
        applicationResponse.setAppState(cloudApplicationFound.getState().toString());

        return applicationResponse;
    }
}
