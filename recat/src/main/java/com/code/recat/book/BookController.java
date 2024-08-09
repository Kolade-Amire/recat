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
    ResponseEntity<Book> createNewBook(@RequestBody BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        var createdBook = bookService.addNewBook(bookRequest);
        URI newBookLocation = uriComponentsBuilder
                .path(AppConstants.BASE_URL + "/books/{id}")
                .buildAndExpand(createdBook.getBookId())
                .toUri();
        return ResponseEntity.created(newBookLocation).body(createdBook);
    }

    @GetMapping
    ResponseEntity<Page<BookViewDto>> getAllBooks(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<BookViewDto> page = bookService.findAllBooks(pageNum, pageSize);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search")
    ResponseEntity<Page<BookViewDto>> searchBooksByTitleOrAuthorName(@RequestParam String searchQuery, @RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<BookViewDto> page = bookService.findMatchingBooksByTitleOrAuthorName(searchQuery, pageNum, pageSize);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    ResponseEntity<BookViewDto> updateBook(
            @PathVariable Integer id,
            @RequestBody BookRequest bookRequest
    ) {

        BookViewDto updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<BookViewDto> findBookById(@PathVariable Integer id) {
        var book = bookService.findBookForView(id);
        return ResponseEntity.ok(book);
    }

    //TODO: get books by their genre tags


}