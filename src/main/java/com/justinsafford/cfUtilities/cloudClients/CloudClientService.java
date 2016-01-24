package com.justinsafford.cfUtilities.cloudClients;

import java.net.URL;

public interface CloudClientService {
    CloudClientEntity addNewCloudClient(CloudClientRequest cloudClientRequest, URL url);
}
