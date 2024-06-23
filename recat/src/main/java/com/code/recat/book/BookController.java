package com.code.recat.book;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/books")
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

    @GetMapping("/principal")
    public String home(Principal principal){
        return "Hello, " + principal.getName();
    }
}