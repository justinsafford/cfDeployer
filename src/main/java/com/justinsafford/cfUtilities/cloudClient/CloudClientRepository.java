package com.justinsafford.cfUtilities.cloudClient;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CloudClientRepository extends MongoRepository<CloudClient, Long>{
}
