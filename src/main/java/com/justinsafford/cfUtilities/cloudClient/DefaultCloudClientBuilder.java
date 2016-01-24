package com.justinsafford.cfUtilities.cloudClient;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class DefaultCloudClientBuilder implements CloudClientBuilder {

    @Override
    public CloudFoundryClient generateCloudFoundryClient(String username, String password, String org, String space) {
        CloudCredentials cloudCredentials = new CloudCredentials(
                username,
                password);

        URL url = null;
        try {
            url = new URL("HTTP", "api.run.pivotal.io", 80, "");
        } catch (MalformedURLException e) {

        }

        CloudFoundryClient cloudFoundryClient =
                new CloudFoundryClient(
                        cloudCredentials,
                        url,
                        org,
                        space);

        return cloudFoundryClient;
    }
}
