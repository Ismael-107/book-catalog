package com.example.catalog.model;

public class Book {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private Integer year;

    public Book(Long id, String isbn, String title, String author, Integer year) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Long getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Integer getYear() { return year; }
}