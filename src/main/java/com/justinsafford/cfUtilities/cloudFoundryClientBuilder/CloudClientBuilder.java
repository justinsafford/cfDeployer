package com.justinsafford.cfUtilities.cloudFoundryClientBuilder;

import org.cloudfoundry.client.lib.CloudFoundryClient;

public interface CloudClientBuilder {
    CloudFoundryClient generateCloudFoundryClient(String username, String password, String org, String space);
}
