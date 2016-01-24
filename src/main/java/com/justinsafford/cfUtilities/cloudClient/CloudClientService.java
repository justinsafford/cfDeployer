package com.justinsafford.cfUtilities.cloudClient;

import java.net.URL;

public interface CloudClientService {
    CloudClientEntity addNewCloudClient(CloudClientRequest cloudClientRequest, URL url);
}
