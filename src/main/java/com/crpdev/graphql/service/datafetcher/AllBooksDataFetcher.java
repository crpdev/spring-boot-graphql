package com.crpdev.graphql.service.datafetcher;

import com.crpdev.graphql.model.Book;
import com.crpdev.graphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by rajapandian
 * Date: 06/06/20
 * Project: spring-boot-graphql
 * Package: com.crpdev.graphql.service.datafetcher
 **/
@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {

    private BookRepository bookRepository;

    public AllBooksDataFetcher(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return bookRepository.findAll();
    }
}
