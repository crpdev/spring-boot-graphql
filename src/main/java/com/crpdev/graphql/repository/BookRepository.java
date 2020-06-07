package com.crpdev.graphql.repository;

import com.crpdev.graphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rajapandian
 * Date: 06/06/20
 * Project: spring-boot-graphql
 * Package: com.crpdev.graphql.repository
 **/
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
