package com.code.recat.book;

import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/book/{book_id}")
    public void editBook(@PathVariable int book_id){

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{book_id}")
    public void deleteBook(@PathVariable int book_id){
        repository.deleteBooksByBook_id(book_id);
    }

}
