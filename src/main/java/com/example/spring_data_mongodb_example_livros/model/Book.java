package com.example.spring_data_mongodb_example_livros.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    private String author;

    private int year;

    private String gender;

    private double price;

    protected Book() {
    }

    public Book(String title, String autor, int year, String gender, double price) {
        this.title = title;
        this.author = autor;
        this.year = year;
        this.gender = gender;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }


    public int getYear() {
        return year;
    }

    public String getGender() {
        return gender;
    }

    public double getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public void setYear(int year) {
        this.year = year;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
