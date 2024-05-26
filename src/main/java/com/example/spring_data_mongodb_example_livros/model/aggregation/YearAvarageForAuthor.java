package com.example.spring_data_mongodb_example_livros.model.aggregation;

public class YearAvarageForAuthor {
    private String author;
    private double yearAvarage;

    public YearAvarageForAuthor(String author, double yearAvarage) {
        this.author = author;
        this.yearAvarage = yearAvarage;
    }

    public String getAuthor() {
        return author;
    }

    public double getYearAvarage() {
        return yearAvarage;
    }
}
