package com.code.recat.author;

import com.code.recat.book.Book;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface AuthorService {

    Page<Author> findAllAuthors(int pageNum, int pageSize);

    Author getAuthor(Integer authorId);

    Author addNewAuthor(AuthorRequest newAuthor);

    Author updateAuthor(Integer authorId, AuthorRequest author);

    void deleteAuthor(Integer authorId);

    Author getAuthorFromDto(AuthorDto authorDto);

    //for internal use only
    Author saveAuthor(Author author);

    Author getAuthorByName(String authorName);
}
