package com.code.recat.author;

import org.springframework.data.domain.Page;

public interface AuthorService {

    Page<AuthorDto> findAllAuthors(int pageNum, int pageSize);

    Author getAuthorById(Integer authorId);

    AuthorDto addNewAuthor(AuthorRequest newAuthor);

    Author getAuthorByName(String authorName);

    AuthorDto updateAuthor(Integer authorId, AuthorRequest author);

    void deleteAuthor(Integer authorId);

    Author getAuthorFromDto(AuthorDto authorDto);

    //for internal use only
    Author saveAuthor(Author author);

    Page<AuthorDto> searchAuthorsByName(String authorName);
}
