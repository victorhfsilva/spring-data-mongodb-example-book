package com.example.spring_data_mongodb_example_livros.repository;

import com.example.spring_data_mongodb_example_livros.model.Book;
import com.example.spring_data_mongodb_example_livros.model.aggregation.BooksForAuthor;
import com.example.spring_data_mongodb_example_livros.model.aggregation.YearAvarageForAuthor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=4.0.12")
public class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        bookRepository.save(new Book("Book One", "Author One", 2001, "Fiction", 10.0));
        bookRepository.save(new Book("Book Two", "Author One", 2002, "Fiction", 20.0));
        bookRepository.save(new Book("Book Three", "Author Two", 2003, "Non-Fiction", 30.0));
    }

    @Test
    public void testFindByTitle() {
        Optional<Book> book = bookRepository.findByTitle("Book One");
        assertTrue(book.isPresent());
        assertEquals("Author One", book.get().getAuthor());
    }

    @Test
    public void testFindByGender() {
        Page<Book> page = bookRepository.findByGender("Fiction", PageRequest.of(0, 10));
        assertNotNull(page);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    public void testFindByAuthorLike() {
        Page<Book> page = bookRepository.findByAuthorLike("Author", PageRequest.of(0, 10));
        assertNotNull(page);
        assertEquals(3, page.getTotalElements());
    }

    @Test
    public void testFindByTitleLike() {
        Page<Book> page = bookRepository.findByTitleLike("Book", PageRequest.of(0, 10));
        assertNotNull(page);
        assertEquals(3, page.getTotalElements());
    }

    @Test
    public void testFindByYearGreaterThan() {
        Page<Book> page = bookRepository.findByYearGreaterThan(2001, PageRequest.of(0, 10));
        assertNotNull(page);
        assertEquals(3, page.getTotalElements());
    }

    @Test
    public void testFindByYearLesserThan() {
        Page<Book> page = bookRepository.findByYearLesserThan(2002, PageRequest.of(0, 10));
        assertNotNull(page);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    public void testSearchForYearBetween() {
        Page<Book> page = bookRepository.searchForYearBetween(2001, 2003, PageRequest.of(0, 10));
        assertNotNull(page);
        assertEquals(3, page.getTotalElements());
    }

    @Test
    public void testCalculateYearAvarageForAuthor() {
        List<YearAvarageForAuthor> list = bookRepository.calculateYearAvarageForAuthor();
        assertNotNull(list);
        assertEquals(2, list.size());
        YearAvarageForAuthor authorOne = list.stream()
                .filter(a -> a.getAuthor().equals("Author One"))
                .findFirst()
                .orElse(null);
        assertNotNull(authorOne);
        assertEquals(2001.5, authorOne.getYearAvarage(), 0.01);
    }

    @Test
    public void testCountBooksForAuthor() {
        List<BooksForAuthor> list = bookRepository.countBooksForAuthor();
        assertNotNull(list);
        assertEquals(2, list.size());
        BooksForAuthor authorOne = list.stream()
                .filter(a -> a.getAuthor().equals("Author One"))
                .findFirst()
                .orElse(null);
        assertNotNull(authorOne);
        assertEquals(2, authorOne.getBooksCount());
    }

}