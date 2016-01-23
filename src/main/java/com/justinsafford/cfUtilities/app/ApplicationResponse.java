package com.justinsafford.cfUtilities.app;

import org.cloudfoundry.client.lib.domain.Staging;

import java.util.List;

public class ApplicationResponse {

    String appName;
    String spaceName;
    String appState;

    int runningInstances;
    int instances;
    int memory;
    int diskQuota;

    Staging staging;
    List<String> uris;
    List<String> services;
    List<String> envVariables;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public Staging getStaging() {
        return staging;
    }

    public void setStaging(Staging staging) {
        this.staging = staging;
    }

    public String getAppState() {
        return appState;
    }

    public void setAppState(String appState) {
        this.appState = appState;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public int getRunningInstances() {
        return runningInstances;
    }

    public void setRunningInstances(int runningInstances) {
        this.runningInstances = runningInstances;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getDiskQuota() {
        return diskQuota;
    }

    public void setDiskQuota(int diskQuota) {
        this.diskQuota = diskQuota;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<String> getEnvVariables() {
        return envVariables;
    }

    public void setEnvVariables(List<String> envVariables) {
        this.envVariables = envVariables;
    }
}
