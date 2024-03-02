package com.code.recat.book;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookRepository repository;
    public BookController(BookRepository repository){
        this.repository = repository;
    }

    @GetMapping("")
    public List<Book> findAllBooks(){
        return repository.findAll();
    }

    @GetMapping("/title/{title}")
    public List<Book> findByTitle(@PathVariable String title){
        return repository.findBooksByTitleContainsIgnoreCase(title);
    }

    @GetMapping("/genre/{genre}")
    public List<Book> findByGenre(@PathVariable String genre){
        return repository.findBooksByCategoryName(genre);
    }

    @GetMapping("/author/{author}")
    public List<Book> findByAuthor(@PathVariable String author){
        return repository.findBooksByAuthorName(author);
    }

    @PostMapping("/book/{book_id}")
    public void editBook(@PathVariable String book_id){

    }


}
