package com.example.catalog.dto;

public record BookResponse(Long id, String isbn, String title, String author, Integer year) {
}