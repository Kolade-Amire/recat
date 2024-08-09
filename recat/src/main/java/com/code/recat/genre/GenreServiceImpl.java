package com.code.recat.genre;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{

    private final GenreRepository repository;


    @Override
    public Genre getGenreById(Integer id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Genre does not exist")
                );
    }

    @Override
    public Genre getGenreByName(String name) {
        return repository.findByName(name)
                .orElseThrow(
                        () -> new EntityNotFoundException("Genre does not exist")
                );
    }

    @Override
    public Genre addGenre(String genreName) {
        var book = Genre.builder()
                .name(genreName)
                .build();

        return repository.save(book);
    }

    @Override
    public Page<Genre> getAllGenres(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return repository.findAllByOrderByNameAsc(pageable);
    }

    @Override
    public void deleteGenre(Integer id) {
        var genre = getGenreById(id);
        repository.delete(genre);
    }

    @Override
    public Genre updateGenre(Integer id, String name) {
        var genre = getGenreById(id);
        genre.setName(name);
        return repository.save(genre);
    }

}
