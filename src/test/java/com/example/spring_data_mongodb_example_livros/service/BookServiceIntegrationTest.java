package com.example.spring_data_mongodb_example_livros.service;

import com.example.spring_data_mongodb_example_livros.model.Book;
import com.example.spring_data_mongodb_example_livros.model.aggregation.PriceForAuthor;
import com.example.spring_data_mongodb_example_livros.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=4.0.12")
public class BookServiceIntegrationTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        bookRepository.save(new Book("Book One", "Author One", 2001, "Fiction", 10.0));
        bookRepository.save(new Book("Book Two", "Author One", 2002, "Fiction", 20.0));
        bookRepository.save(new Book("Book Three", "Author Two", 2003, "Non-Fiction", 30.0));
    }

    @Test
    public void testFindByPriceGreaterThan() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = bookService.findByPriceGreaterThan(20.00, pageable);

        assertNotNull(page);
        assertEquals(2, page.getTotalElements());

        Book book = page.getContent().get(0);
        assertEquals("Book Two", book.getTitle());
    }

    @Test
    public void testCalculatePriceForAuthorBibliography() {
        List<PriceForAuthor> list = bookService.calculatePriceForAuthorBibliography();
        assertNotNull(list);
        assertEquals(2, list.size());

        PriceForAuthor authorOne = list.stream()
                .filter(a -> a.getAuthor().equals("Author One"))
                .findFirst()
                .orElse(null);
        assertNotNull(authorOne);
        assertEquals(30.0, authorOne.getTotalPrice(), 0.01);
    }
}
