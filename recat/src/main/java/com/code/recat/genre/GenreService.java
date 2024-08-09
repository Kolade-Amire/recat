package com.code.recat.genre;

import org.springframework.data.domain.Page;

public interface GenreService {

    Genre getGenreById(Integer id);

    Genre getGenreByName(String name);

    Genre addGenre(String genreName);

    Page<Genre> getAllGenres(int pageNum, int pageSize);

    void deleteGenre(Integer id);

    Genre updateGenre(Integer id, String name);
}
