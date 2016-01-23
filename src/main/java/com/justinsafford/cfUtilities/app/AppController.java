package com.justinsafford.cfUtilities.app;

import com.justinsafford.cfUtilities.cloudClient.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClient.CloudClientRepository;
import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(
            value = "/application/{cloudClientId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> getAllApps(@PathVariable String cloudClientId) {

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

        List<CloudApplication> cloudAppsFound = cloudFoundryClient.getApplications();
        List<String> cloudAppsList = new ArrayList<>();

        cloudAppsFound
                .forEach(cloudApplication -> cloudAppsList.add(cloudApplication.getName()));

        return cloudAppsList;
    }

    @RequestMapping(
            value = "/application/{appName}/{cloudClientId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse getAppDetails(@PathVariable String cloudClientId,
                                      @PathVariable String appName) {

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

        CloudApplication cloudApplicationFound = cloudFoundryClient.getApplication(appName);

        ApplicationResponse applicationResponse = new ApplicationResponse();

        applicationResponse.setAppName(cloudApplicationFound.getName());
        applicationResponse.setSpaceName(cloudApplicationFound.getSpace().getName());
        applicationResponse.setStaging(cloudApplicationFound.getStaging());
        applicationResponse.setAppState(cloudApplicationFound.getState().toString());
        applicationResponse.setInstances(cloudApplicationFound.getInstances());
        applicationResponse.setRunningInstances(cloudApplicationFound.getRunningInstances());
        applicationResponse.setMemory(cloudApplicationFound.getMemory());
        applicationResponse.setDiskQuota(cloudApplicationFound.getDiskQuota());
        applicationResponse.setUris(cloudApplicationFound.getUris());
        applicationResponse.setServices(cloudApplicationFound.getServices());
        applicationResponse.setEnvVariables(cloudApplicationFound.getEnv());

        return applicationResponse;
    }
}
