package com.example.graphqlwithmongopoc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.graphqlwithmongopoc.model.Developer;

@Repository
public interface DeveloperRepository extends MongoRepository<Developer, String>{

}
