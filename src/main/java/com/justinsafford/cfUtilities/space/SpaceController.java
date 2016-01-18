package com.justinsafford.cfUtilities.space;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpaceController {

    CloudFoundryClient myCloudFoundryClient;

    @RequestMapping(
            value = "/space/{cloudClientId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpace(@PathVariable String cloudClientId,
                            @RequestParam String spaceName) {
        myCloudFoundryClient.createSpace(spaceName);
    }
}
