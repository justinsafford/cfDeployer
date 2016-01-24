package com.justinsafford.cfUtilities.cloudClient;

import org.cloudfoundry.client.lib.CloudFoundryClient;

public interface CloudClientBuilder {
    CloudFoundryClient generateCloudFoundryClient(String username, String password, String org, String space);
}
