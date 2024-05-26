package com.example.spring_data_mongodb_example_livros.model.aggregation;

public class BooksForAuthor {
    private String author;
    private int booksCount;

    public BooksForAuthor(String author, int booksCount) {
        this.author = author;
        this.booksCount = booksCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getBooksCount() {
        return booksCount;
    }
}
