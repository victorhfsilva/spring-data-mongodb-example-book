package com.example.spring_data_mongodb_example_livros.service.impl;

import com.example.spring_data_mongodb_example_livros.model.Book;
import com.example.spring_data_mongodb_example_livros.model.aggregation.BooksForAuthor;
import com.example.spring_data_mongodb_example_livros.model.aggregation.PriceForAuthor;
import com.example.spring_data_mongodb_example_livros.model.aggregation.YearAvarageForAuthor;
import com.example.spring_data_mongodb_example_livros.repository.BookRepository;
import com.example.spring_data_mongodb_example_livros.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public void update(String id, Book newBook) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setGender(newBook.getGender());
        book.setYear(newBook.getYear());
        book.setPrice(newBook.getPrice());
        bookRepository.save(book);
    }

    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow();
    }

    public Page<Book> findByGender(String gender, Pageable pageable) {
        return bookRepository.findByGender(gender, pageable);
    }

    public Page<Book> findByAuthorLike(String author, Pageable pageable) {
        return bookRepository.findByAuthorLike(author, pageable);
    }

    public Page<Book> findByTitleLike(String title, Pageable pageable) {
        return bookRepository.findByTitleLike(title, pageable);
    }

    public Page<Book> findByYearGreaterThan(int year, Pageable pageable) {
        return bookRepository.findByYearGreaterThan(year, pageable);
    }

    public Page<Book> findByYearLesserThan(int year, Pageable pageable) {
        return bookRepository.findByYearLesserThan(year, pageable);
    }

    public Page<Book> searchForYearBetween(int initialYear, int finalYear, Pageable pageable) {
        return bookRepository.searchForYearBetween(initialYear, finalYear, pageable);
    }

    public List<YearAvarageForAuthor> calculateYearAvarageForAuthor(){
        return bookRepository.calculateYearAvarageForAuthor();
    }

    public List<BooksForAuthor> countBooksForAuthor() {
        return bookRepository.countBooksForAuthor();
    }

    public Page<Book> findByPriceGreaterThan(double price, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("price").gte(price));

        long total = mongoTemplate.count(query, Book.class);
        query.with(pageable);
        List<Book> books = mongoTemplate.find(query, Book.class);

        return new PageImpl<>(books, pageable, total);
    }

    public List<PriceForAuthor> calculatePriceForAuthorBibliography() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("author").sum("price").as("totalPrice"),
                Aggregation.project("totalPrice").and("author").previousOperation()
        );

        AggregationResults<PriceForAuthor> results = mongoTemplate.aggregate(aggregation,
                "books",
                PriceForAuthor.class);

        return results.getMappedResults();
    }
}
