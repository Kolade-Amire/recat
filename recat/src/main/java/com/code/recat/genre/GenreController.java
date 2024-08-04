package com.code.recat.genre;

import com.code.recat.book.Book;
import com.code.recat.book.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreService service;


    @GetMapping("/{id}")
    ResponseEntity<Genre> getGenreById (@PathVariable Long id){
        var genre =  service.getGenreById(id);
        return ResponseEntity.ok(genre);
    }

    @GetMapping("/{name}")
    ResponseEntity<Genre> getGenreByName (@PathVariable String name){
        var genre =  service.getGenreByName(name);
        return ResponseEntity.ok(genre);
    }

    @PostMapping
    ResponseEntity<Genre> createNewGenre (@RequestBody String name){
        var newGenre = service.addGenre(name);
        return ResponseEntity.ok(newGenre);
    }

    @GetMapping
    ResponseEntity<Page<Genre>> getAllGenres(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Genre> page = service.getAllGenres(pageNum, pageSize);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        service.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Genre> updateGenre(
            @PathVariable Long id,
            @RequestBody String name
    ) {

        Genre genre = service.updateGenre(id, name);
        return ResponseEntity.ok(genre);
    }

}
