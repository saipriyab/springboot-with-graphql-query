package com.example.graphqlwithmongopoc.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.graphqlwithmongopoc.model.Developer;
import com.example.graphqlwithmongopoc.repository.DeveloperRepository;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
	@Autowired
	private DeveloperRepository developerRepository;

	@Value("classpath:developer.graphqls")
	private Resource schemaResource;

	private GraphQL graphQL;

	@PostConstruct
	public void loadSchema() throws IOException {
		File schemaFile = schemaResource.getFile();
		TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildwiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private RuntimeWiring buildwiring() {
		DataFetcher<List<Developer>> fetcher1 = data -> {
			return (List<Developer>) developerRepository.findAll();
		};
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWriting -> typeWriting.dataFetcher("findAllDevelopers", fetcher1)).build();
	}

	@GetMapping("/findall")
	public Object findAll() {
    ExecutionResult result=graphQL.execute(ExecutionInput.newExecutionInput("query{findAllDevelopers{experience}}"));
    return result.getData();
	}

}
