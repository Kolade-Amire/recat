package com.code.recat.author;

public class AuthorDtoMapper {

    public static AuthorDto mapAuthorToDto(Author author) {
        return AuthorDto.builder()
                .id(author.getAuthorId())
                .name(author.getName())
                .build();
    }
}
