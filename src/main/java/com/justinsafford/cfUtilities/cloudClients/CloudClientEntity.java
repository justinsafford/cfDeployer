package com.justinsafford.cfUtilities.cloudClients;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;

@Document(collection = "cloudClients")
public class CloudClientEntity {

    @Id
    private String cloudClientId;

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

    public void setCloudClientId(String cloudClientId) {
        this.cloudClientId = cloudClientId;
    }

    public String getCloudClientId() {
        return cloudClientId;
    }
}
