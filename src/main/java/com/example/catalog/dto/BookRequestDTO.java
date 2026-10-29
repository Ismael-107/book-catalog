package com.example.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookRequestDTO(
        @NotBlank(message = "isbn es obligatorio") String isbn,
        @NotBlank(message = "title es obligatorio") String title,
        @NotBlank(message = "author es obligatorio") String author,
        @NotNull(message = "year es obligatorio") @Positive(message = "year debe ser positivo") Integer year) {
}