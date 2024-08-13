package com.code.recat.user;

import com.code.recat.author.AuthorRequest;
import com.code.recat.author.AuthorService;
import com.code.recat.book.BookRequest;
import com.code.recat.book.BookService;
import com.code.recat.genre.GenreService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserServiceTest {


    @Autowired
    private UserService userService;

    private User user;

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;


    @BeforeEach
    void setup() {

        user = User.builder()
                .name("Kolade Amire")
                .username("koladeam")
                .password("password123")
                .email("koladeamire20@gmail.com")
                .gender("Male").role(Role.USER)
                .dateJoined(LocalDateTime.now())
                .favoriteBooks(new HashSet<>())
                .tokens(new ArrayList<>())
                .build();

        userService.saveUser(user);


    }

    @Test
    @DirtiesContext
    void shouldCreateANewUser() {

        User newUser = User.builder()
                .name("Ope Amire")
                .username("helenope")
                .password("password123")
                .email("helenamire@gmail.com")
                .gender("female").role(Role.USER)
                .dateJoined(LocalDateTime.now())
                .favoriteBooks(new HashSet<>())
                .tokens(new ArrayList<>())
                .build();

        var createdUser = userService.saveUser(newUser);

        assertEquals(newUser, createdUser);
        assertEquals(newUser.getUserId(), createdUser.getUserId());
        assertEquals(newUser.getEmail(), createdUser.getEmail());

    }

    @Test
    @DirtiesContext
    void shouldReturnSavedUserByEmail() {

        String email = user.getEmail();
        var result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    @DirtiesContext
    void shouldReturnSavedUserProfile() {

        var id = user.getUserId();
        var result = userService.getUserProfile(id);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserId(), result.getId());
    }

    @Test
    @DirtiesContext
    void shouldReturnSavedUserById() {

        Integer id = user.getUserId();
        var result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    @DirtiesContext
    void shouldUpdateUserDetails() {
    var id = user.getUserId();
        var updateRequest = UserRequest.builder()
                .firstName("Stephen")
                .lastName("Amire")
                .username("stephamire")
                .build();
        var newName = updateRequest.getFirstName() + " " + updateRequest.getLastName();

        var updatedUser = userService.updateUserDetails(id, updateRequest);

        assertNotNull(updatedUser);
        assertEquals(user.getUserId(), updatedUser.getUserId());
        assertNotEquals(user.getName(), updatedUser.getName());
        assertEquals(updateRequest.getUsername(), updatedUser.getProfileName());
        assertEquals(newName, updatedUser.getName());


    }

    @Test
    @DirtiesContext
    void shouldDeleteUser() {
        userService.deleteUser(user.getUserId());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(user.getUserId()));

    }

    @Test
    @DirtiesContext
    void shouldAddBookAsFavourite() {
        //Setting up an existing book
        var testGenre = genreService.addGenre("Fantasy");

        var author1 = new AuthorRequest("Author One", LocalDate.of(2024, 8, 2), "female");
        authorService.addNewAuthor(author1);

        var book1 = new BookRequest("Book One Title", author1.getName(), "Blurb for first book.", 2000, Set.of(testGenre), "25362348-72", "https://coverimage1.com");

        //add book to database
       var addedBook = bookService.addNewBook(book1);
        //get book
       var existingBook = bookService.findBookForView(addedBook.getBookId());

        //add book as favourite for test user
        var userFavBooks = userService.addBookAsFavourite(user.getUserId(), existingBook);

        assertNotNull(userFavBooks);
        assertThat(userFavBooks).isEqualTo(Set.of(existingBook));

    }

    @Test
    @DirtiesContext
    void shouldGetUserFavouriteBooks() {

        //Setting up an existing book
        var testGenre = genreService.addGenre("Fantasy");

        var author1 = new AuthorRequest("Author One", LocalDate.of(2024, 8, 2), "female");
        authorService.addNewAuthor(author1);

        var book1 = new BookRequest("Book One Title", author1.getName(), "Blurb for first book.", 2000, Set.of(testGenre), "25362348-72", "https://coverimage1.com");

        //add book to database
        var addedBook = bookService.addNewBook(book1);
        //get book
        var existingBook = bookService.findBookForView(addedBook.getBookId());

        //add book as favourite for test user
        userService.addBookAsFavourite(user.getUserId(), existingBook);

        var userFavBooks = userService.getUserFavouriteBooks(user.getUserId());

        assertNotNull(userFavBooks);
        assertThat(userFavBooks).isEqualTo(Set.of(existingBook));


    }

}
