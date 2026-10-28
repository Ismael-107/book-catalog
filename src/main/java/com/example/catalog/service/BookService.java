package com.example.catalog.service;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public BookResponseDTO create(BookRequestDTO request) {
        Book book = new Book(sequence.incrementAndGet(),
                request.isbn(), request.title(), request.author(), request.year());
        books.put(book.getId(), book);
        return toResponse(book);
    }

    public List<BookResponseDTO> list() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .map(this::toResponse)
                .toList();
    }

    public BookResponseDTO get(Long id) {
        return toResponse(getOrThrow(id));
    }

    public BookResponseDTO update(Long id, BookRequestDTO request) {
        getOrThrow(id);
        Book book = new Book(id, request.isbn(), request.title(),
                request.author(), request.year());
        books.put(id, book);
        return toResponse(book);
    }

    public void delete(Long id) {
        getOrThrow(id);
        books.remove(id);
    }


    private Book getOrThrow(Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return book;
    }

    private BookResponseDTO toResponse(Book book) {
        return new BookResponseDTO(book.getId(), book.getIsbn(), book.getTitle(),
                book.getAuthor(), book.getYear());
    }
}