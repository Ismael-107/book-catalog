package com.example.catalog.web;

import com.example.catalog.dto.BookRequest;
import com.example.catalog.dto.BookResponse;
import com.example.catalog.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @GetMapping
    public List<BookResponse> list() {
        return books.values().stream()
                .sorted((a, b) -> Long.compare(a.getId(), b.getId()))
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public BookResponse getOne(@PathVariable("id") Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return toResponse(book);
    }

    @PostMapping
    public BookResponse create(@RequestBody BookRequest request) {
        Book book = new Book(sequence.incrementAndGet(),
                request.isbn(), request.title(), request.author(), request.year());
        books.put(book.getId(), book);
        return toResponse(book);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable("id") Long id, @RequestBody BookRequest request) {
        if (!books.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Book book = new Book(id, request.isbn(), request.title(), request.author(), request.year());
        books.put(id, book);
        return toResponse(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        if (books.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getIsbn(), book.getTitle(),
                book.getAuthor(), book.getYear());
    }
}