package com.justinsafford.cfUtilities.cloudClient;

public class CloudClientRequest {
    private String cloudFoundryUsername;
    private String cloudFoundryPassword;
    private String cloudFoundryOrg;
    private String cloudFoundrySpace;

    public String getCloudFoundryUsername() {
        return cloudFoundryUsername;
    }

    public void setCloudFoundryUsername(String cloudFoundryUsername) {
        this.cloudFoundryUsername = cloudFoundryUsername;
    }

    public String getCloudFoundryPassword() {
        return cloudFoundryPassword;
    }

    public void setCloudFoundryPassword(String cloudFoundryPassword) {
        this.cloudFoundryPassword = cloudFoundryPassword;
    }

    public String getCloudFoundryOrg() {
        return cloudFoundryOrg;
    }

    public void setCloudFoundryOrg(String cloudFoundryOrg) {
        this.cloudFoundryOrg = cloudFoundryOrg;
    }

    public String getCloudFoundrySpace() {
        return cloudFoundrySpace;
    }

    public void setCloudFoundrySpace(String cloudFoundrySpace) {
        this.cloudFoundrySpace = cloudFoundrySpace;
    }
}
