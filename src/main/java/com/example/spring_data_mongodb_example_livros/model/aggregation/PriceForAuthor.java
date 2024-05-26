package com.example.spring_data_mongodb_example_livros.model.aggregation;

public class PriceForAuthor {
    private String author;
    private int totalPrice;

    public PriceForAuthor(String author, int totalPrice) {
        this.author = author;
        this.totalPrice = totalPrice;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
