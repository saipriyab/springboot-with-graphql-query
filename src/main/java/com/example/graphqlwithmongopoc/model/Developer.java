package com.example.graphqlwithmongopoc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "developers")
@Data
public class Developer {

	@Id
	private String id;
	private String name;
	private Integer experience;
}
