package com.justinsafford.cfUtilities;

import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CloudClientRepository extends MongoRepository<CloudFoundryClient,Long>{
}
