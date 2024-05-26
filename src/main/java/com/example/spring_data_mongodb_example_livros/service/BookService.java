package com.example.spring_data_mongodb_example_livros.service;

import com.example.spring_data_mongodb_example_livros.model.Book;
import com.example.spring_data_mongodb_example_livros.model.aggregation.BooksForAuthor;
import com.example.spring_data_mongodb_example_livros.model.aggregation.PriceForAuthor;
import com.example.spring_data_mongodb_example_livros.model.aggregation.YearAvarageForAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(String id);
    Book save(Book book);
    void deleteById(String id);
    void deleteAll();
    void update(String id, Book newBook);
    Book findByTitle(String title);
    Page<Book> findByGender(String gender, Pageable pageable);
    Page<Book> findByAuthorLike(String author, Pageable pageable);
    Page<Book> findByTitleLike(String title, Pageable pageable);
    Page<Book> findByYearGreaterThan(int year, Pageable pageable);
    Page<Book> findByYearLesserThan(int year, Pageable pageable);
    Page<Book> searchForYearBetween(int initialYear, int finalYear, Pageable pageable);
    List<YearAvarageForAuthor> calculateYearAvarageForAuthor();
    List<BooksForAuthor> countBooksForAuthor();
    Page<Book> findByPriceGreaterThan(double price, Pageable pageable);
    List<PriceForAuthor> calculatePriceForAuthorBibliography();
}
