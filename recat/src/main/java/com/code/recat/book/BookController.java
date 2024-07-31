package com.code.recat.book;


import  org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    ResponseEntity<Book> addNewBook(@RequestBody BookRequest bookRequest){
        var createdBook = bookService.addNewBook(bookRequest);
        return ResponseEntity.ok(createdBook);
    }

    @GetMapping
    ResponseEntity<List<Book>> getAllBooks(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Book> eventPage = bookService.findAllBooks(pageNum, pageSize);
        return ResponseEntity.ok(eventPage.getContent());
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
    ResponseEntity<Book> findBookById(@PathVariable Long bookId) {
        Book book = bookService.findBookById(bookId);
        return ResponseEntity.ok(book);
    }


}