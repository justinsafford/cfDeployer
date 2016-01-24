package com.justinsafford.cfUtilities.cloudClients;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CloudClientRepository extends MongoRepository<CloudClientEntity, String>{
}
