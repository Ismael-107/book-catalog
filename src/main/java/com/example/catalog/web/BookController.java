package com.example.catalog.web;

import com.example.catalog.dto.BookRequest;
import com.example.catalog.dto.BookResponse;
import com.example.catalog.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<BookResponse>> list() {
        List<BookResponse> all = books.values().stream()
                .sorted((a, b) -> Long.compare(a.getId(), b.getId()))
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(all);                                   // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getOne(@PathVariable("id") Long id) {
        Book book = books.get(id);
        if (book == null) {
            return ResponseEntity.notFound().build();                    // 404
        }
        return ResponseEntity.ok(toResponse(book));                      // 200
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody BookRequest request) {
        Book book = new Book(sequence.incrementAndGet(),
                request.isbn(), request.title(), request.author(), request.year());
        books.put(book.getId(), book);
        URI location = URI.create("/books/" + book.getId());
        return ResponseEntity.created(location).body(toResponse(book)); // 201 + Location
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable("id") Long id,
                                               @RequestBody BookRequest request) {
        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();                    // 404
        }
        Book book = new Book(id, request.isbn(), request.title(),
                request.author(), request.year());
        books.put(id, book);
        return ResponseEntity.ok(toResponse(book));                      // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (books.remove(id) == null) {
            return ResponseEntity.notFound().build();                    // 404
        }
        return ResponseEntity.noContent().build();                       // 204
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getIsbn(), book.getTitle(),
                book.getAuthor(), book.getYear());
    }
}