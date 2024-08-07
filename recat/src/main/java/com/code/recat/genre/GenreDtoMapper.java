package com.code.recat.genre;

import java.util.Set;
import java.util.stream.Collectors;

public class GenreDtoMapper {

    public static GenreDto mapGenreToDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getGenreId())
                .name(genre.getName())
                .build();
    }

    public static Set<GenreDto> mapGenreSetToDto(Set<Genre> genres) {

        return genres.stream().map(
                GenreDtoMapper::mapGenreToDto
        ).collect(Collectors.toSet());
    }
}
