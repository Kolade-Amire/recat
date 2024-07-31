package com.code.recat.author;

import com.code.recat.book.Book;
import com.code.recat.book.BookServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final BookServiceImpl bookService;

    @Override
    public Page<Author> findAllAuthors(int pageNum, int pageSize) {
        var pageable = PageRequest.of(pageNum, pageSize);
        return authorRepository.getAllByOrderByName(pageable);
    }

    @Override
    public Author getAuthor(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(
                () -> new EntityNotFoundException("Author not found.")
        );
    }

    @Override
    @Transactional
    public Author addNewAuthor(AuthorRequest authorRequest) {
        var newAuthor = Author.builder()
                .name(authorRequest.getName())
                .gender(authorRequest.getGender())
                .dateOfBirth(authorRequest.getDateOfBirth())
                .build();

        return authorRepository.save(newAuthor);
    }

    @Override
    @Transactional
    public Author updateAuthor(Long authorId, AuthorRequest author) {
        var existingAuthor = getAuthor(authorId);
        if(author.getName() != null){existingAuthor.setName(author.getName());}
        if(author.getGender() != null){existingAuthor.setGender(author.getGender());}
        if(author.getDateOfBirth() != null){existingAuthor.setDateOfBirth(author.getDateOfBirth());}

        return authorRepository.save(existingAuthor);

    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {
        var author = getAuthor(authorId);
        author.getBooks().clear();
        authorRepository.delete(author);
    }

    @Override
    public Set<Book> addBookToAuthorProfile(Long authorId, Long bookId) {
        var author = getAuthor(authorId);
        var existingAuthorBooks = author.getBooks();
        var book = bookService.findById(bookId);
        existingAuthorBooks.add(book);

        return existingAuthorBooks;
    }

}
