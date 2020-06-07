package com.crpdev.graphql.resource;

import com.crpdev.graphql.service.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rajapandian
 * Date: 06/06/20
 * Project: spring-boot-graphql
 * Package: com.crpdev.graphql.resource
 **/
@RestController
@RequestMapping("/api/books")
public class BookResource {

    private GraphQLService graphQLService;

    public BookResource(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @PostMapping
    public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
        ExecutionResult execute = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<Object>(execute, HttpStatus.OK);
    }
}
