package com.crpdev.graphql.service;

import com.crpdev.graphql.model.Book;
import com.crpdev.graphql.repository.BookRepository;
import com.crpdev.graphql.service.datafetcher.AllBooksDataFetcher;
import com.crpdev.graphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by rajapandian
 * Date: 06/06/20
 * Project: spring-boot-graphql
 * Package: com.crpdev.graphql.service
 **/
@Service
public class GraphQLService {

    @Value("classpath:books.graphql")
    Resource resource;

    @Autowired
    private BookRepository bookRepository;

    private GraphQL graphQL;
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException {

//        Load Sample Data to the repository
        loadDataIntoHSQL();

//      Read the book schema file and load the schema
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoHSQL() {
        Stream.of(
                new Book("123", "Book One", "Kindle", new String[] {
                        "Author 1", "Author 2"}, "June 2020"),
                new Book("345", "Book Two", "Kindle", new String[] {
                        "Author 1", "Author 2"}, "June 2020")
        ).forEach(book -> {
            bookRepository.save(book);
        });
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring ->
                    typeWiring.dataFetcher("allBooks", allBooksDataFetcher)
                            .dataFetcher("book", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
