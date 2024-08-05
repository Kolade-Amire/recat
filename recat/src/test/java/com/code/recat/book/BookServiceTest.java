package com.code.recat.book;

import com.code.recat.author.Author;
import com.code.recat.author.AuthorDto;
import com.code.recat.author.AuthorRequest;
import com.code.recat.author.AuthorService;
import com.code.recat.genre.Genre;
import com.code.recat.genre.GenreService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;


    private BookRequest book1;
    private BookRequest book2;
    private Author author1;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Genre testGenre = genreService.addGenre("Fantasy");

        author1 = authorService.addNewAuthor(new AuthorRequest("Author One", LocalDate.of(2024, 8, 2), "female"));


        var author2 = authorService.addNewAuthor(new AuthorRequest("Author Two", LocalDate.of(2024, 8, 3), "male"));

        book1 = new BookRequest("Book One Title", author1, "Blurb for first book.", 2000, Set.of(testGenre), "25362348-72", "https://coverimage1.com");

        book2 = new BookRequest("Another Book Title", author2, "Blurb for second book.", 2010, Set.of(testGenre), "25485210-89", "https://coverimage2.com");

    }

    @Test
    void shouldReturnAllBooksSortedInOrderOfTitle() {
        bookService.addNewBook(book1);
        bookService.addNewBook(book2);

        var page = bookService.findAllBooks(0, 10);

        assertNotNull(page);
        assertEquals(2, page.getTotalElements());
        assertEquals("Another Book Title", page.getContent().get(0).getTitle());
    }

    @Test
    @DirtiesContext
    void shouldAddANewBook() {

        var newBookRequest = new BookRequest( "New Book Title", author1, "Blurb for New book.", 2020, new HashSet<>(), "25362348-72", "https://coverimagefornewbook.com");

        var savedBook = bookService.addNewBook(newBookRequest);

        assertNotNull(savedBook);

        assertEquals(newBookRequest.getIsbn(), savedBook.getIsbn());
        assertEquals(newBookRequest.getTitle(), savedBook.getTitle());
        System.out.println("book created with id: " + savedBook.getBookId());

    }

    @Test
    @DirtiesContext
    void shouldReturnMatchingBooksWhenSearchedByTitle() {

        bookService.addNewBook(book1);
        bookService.addNewBook(book2);


        var searchQuery = "Book One Title";
        var page = bookService.findMatchingBooksByTitleOrAuthorName(searchQuery, 0, 10);


        assertNotNull(page);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Book One Title");
    }

    @Test
    @DirtiesContext
    void shouldUpdateExistingBookDetailsWithNewDetails(){
        var existingBook = bookService.addNewBook(book1);

        genreService.addGenre("Non-Fiction");

        var genre = genreService.getGenreByName("Non-Fiction");



        var newBookRequest = new BookRequest("Modified Title", book1.getAuthor(),"Modified Blurb", book1.getPublicationYear(), Set.of(genre), "25362348-72", "https://updatedCoverUrl.com");

        var updatedBook = bookService.updateBook(existingBook.getBookId(), newBookRequest);

        assertNotNull(updatedBook);
        assertEquals(newBookRequest.getTitle(), updatedBook.getTitle());
        assertEquals(newBookRequest.getBlurb(), updatedBook.getBlurb());
        assertEquals(newBookRequest.getGenres(), updatedBook.getGenres());
        assertEquals(newBookRequest.getCoverImageUrl(), updatedBook.getCoverImageUrl());


    }

    @Test
    @DirtiesContext
    void shouldDeleteExistingBookUsingBookId(){
        var book = bookService.addNewBook(book1);
        var bookId = book.getBookId();

        bookService.deleteBook(bookId);
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(bookId));
        var exception = assertThrows(EntityNotFoundException.class ,() -> bookService.findBookById(bookId));

        assertEquals("Book does not exist", exception.getMessage());

    }

    @Test
    void shouldReturnEntityNotFoundForBookThatDoesNotExist(){
        Long bookId = 14L;
        assertThrows(EntityNotFoundException.class, () -> bookService.findBookById(bookId));
    }

}
