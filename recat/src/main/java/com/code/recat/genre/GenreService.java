package com.code.recat.genre;

import org.springframework.data.domain.Page;

public interface GenreService {

    Genre getGenreById(Long id);

    Genre getGenreByName(String name);

    Genre addGenre(String genreName);

    Page<Genre> getAllGenres(int pageNum, int pageSize);

    void deleteGenre(Long id);

    Genre updateGenre(Long id, String name);
}
