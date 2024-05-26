package com.example.spring_data_mongodb_example_livros.repository;

import com.example.spring_data_mongodb_example_livros.model.Book;
import com.example.spring_data_mongodb_example_livros.model.aggregation.BooksForAuthor;
import com.example.spring_data_mongodb_example_livros.model.aggregation.YearAvarageForAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String>{
    Optional<Book> findByTitle(String title);
    Page<Book> findByGender(String gender, Pageable pageable);
    Page<Book> findByAuthorLike(String author, Pageable pageable);
    Page<Book> findByTitleLike(String title, Pageable pageable);

    @Query("{'year': {$gte: ?0}}")
    Page<Book> findByYearGreaterThan(int year, Pageable pageable);

    @Query("{'year': {$lte: ?0}}")
    Page<Book> findByYearLesserThan(int year, Pageable pageable);

    @Query("{'year': {$gte: ?0, $lte: ?1}}")
    Page<Book> searchForYearBetween(int initialYear, int finalYear, Pageable pageable);

    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$author', 'yearAvarage': { '$avg': '$year' } } }",
            "{ '$project': { 'author': '$_id', 'yearAvarage': 1, '_id': 0 } }"
    })
    List<YearAvarageForAuthor> calculateYearAvarageForAuthor();

    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$author', 'booksCount': { '$sum': 1 } } }",
            "{ '$project': { 'author': '$_id', 'booksCount': 1, '_id': 0 } }"
    })
    List<BooksForAuthor> countBooksForAuthor();

}
