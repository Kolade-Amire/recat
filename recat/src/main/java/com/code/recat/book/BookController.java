package com.code.recat.book;


import com.code.recat.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/books")
public class BookController {

    private final BookService bookService;


    @PostMapping
    ResponseEntity<Book> createNewBook(@RequestBody BookRequest bookRequest, UriComponentsBuilder ucb) {
        var createdBook = bookService.addNewBook(bookRequest);
        URI newBookLocation = ucb
                .path("/books/{id}")
                .buildAndExpand(createdBook.getBookId())
                .toUri();
        return ResponseEntity.created(newBookLocation).body(createdBook);
    }

    @GetMapping
    ResponseEntity<Page<BookDto>> getAllBooks(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<BookDto> page = bookService.findAllBooks(pageNum, pageSize);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search")
    ResponseEntity<Page<BookDto>> searchBooksByTitleOrAuthorName(@RequestParam String searchQuery, @RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<BookDto> page = bookService.findMatchingBooksByTitleOrAuthorName(searchQuery, pageNum, pageSize);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{bookId}")
    ResponseEntity<Book> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookRequest bookRequest
    ) {

        Book updatedBook = bookService.updateBook(bookId, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}")
    ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookDto> findBookById(@PathVariable Long bookId) {
        BookDto book = bookService.findBookById(bookId);
        return ResponseEntity.ok(book);
    }

    //TODO: get books by their genre tags


}