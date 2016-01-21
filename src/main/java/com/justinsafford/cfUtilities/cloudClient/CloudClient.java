package com.justinsafford.cfUtilities.cloudClient;

import java.net.URL;

public class CloudClient {
    private String cloudUser;
    private String cloudPass;
    private String cloudOrg;
    private String cloudSpace;
    private URL cloudUrl;

    public String getCloudUser() {
        return cloudUser;
    }

    public void setCloudUser(String cloudUser) {
        this.cloudUser = cloudUser;
    }

    public String getCloudPass() {
        return cloudPass;
    }

    public void setCloudPass(String cloudPass) {
        this.cloudPass = cloudPass;
    }

    public String getCloudOrg() {
        return cloudOrg;
    }

    public void setCloudOrg(String cloudOrg) {
        this.cloudOrg = cloudOrg;
    }

    public String getCloudSpace() {
        return cloudSpace;
    }

    public void setCloudSpace(String cloudSpace) {
        this.cloudSpace = cloudSpace;
    }

    public URL getCloudUrl() {
        return cloudUrl;
    }

    public void setCloudUrl(URL cloudUrl) {
        this.cloudUrl = cloudUrl;
    }
}
