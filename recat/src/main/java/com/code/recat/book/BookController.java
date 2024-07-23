package com.code.recat.book;


import com.code.recat.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookServiceImpl bookService;
    public BookController(BookServiceImpl bookService){
        this.bookService = bookService;
    }

    @GetMapping
    ResponseEntity<List<Book>> getAllBooks(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Book> eventPage = bookService.findAllBooks(pageNum,pageSize);
        return ResponseEntity.ok(eventPage.getContent());
    }

    @PutMapping("/{book_id}")
    ResponseEntity<Book> updateBook (
            @PathVariable Integer book_id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String blurb,
            @RequestParam(required = false) Integer publicationYear,
            @RequestParam(required = false) Set<Genre> genres,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String coverImageUrl
            ){

        Book updatedBook = bookService.updateBook(book_id, title, blurb, publicationYear, genres, isbn, coverImageUrl);

        return  ResponseEntity.ok(updatedBook);
    }


}