package com.justinsafford.cfUtilities;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class ConfigurationFile {
    @Bean
    public CloudFoundryClient myCloudFoundryClient() throws MalformedURLException {
        String cloudUser = System.getenv("CFUSER");
        String cloudPass = System.getenv("CFPASS");
        String cloudOrg = System.getenv("CFORG");
        String cloudSpace = System.getenv("CFSPACE");

        CloudCredentials cloudCredentials = new CloudCredentials(
                cloudUser, cloudPass);
        URL url = new URL("HTTP", "api.run.pivotal.io", 80, "");

        return new CloudFoundryClient(cloudCredentials, url, cloudOrg, cloudSpace);
    }
}
