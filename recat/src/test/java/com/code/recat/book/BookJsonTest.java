package com.code.recat.book;


import com.code.recat.author.Author;
import com.code.recat.genre.Genre;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RunWith(SpringRunner.class)
public class BookJsonTest {
    @Autowired
    private JacksonTester<Book> bookJson;

    @Autowired
    private JacksonTester<Book[]> bookJsonList;

    private Book[] books;

    @Value("classpath:book/bookSingle.json")
    Resource singleFile;

    @Value("classpath:book/bookList.json")
    Resource listFile;

    @BeforeEach
    void setUp() {
        var author1 = Author.builder()
                .authorId(61L)
                .name("Harper Lee")
                .dateOfBirth(LocalDate.of(1950, 7, 3 ))
                .gender("Female")
                .build();

        var author2 = Author.builder()
                .authorId(83L)
                .name("Random Author")
                .dateOfBirth(LocalDate.of(1960, 8, 5 ))
                .gender("Male")
                .build();

        books = Arrays.array(
                Book.builder()
                        .bookId(1L)
                        .title("Catcher in the Rye")
                        .author(author1)
                        .blurb("Jonas discovers the truth about his dystopian society and seeks change.")
                        .publicationYear(2004)
                        .genres(Set.of(new Genre(14L, "Dystopian")))
                        .isbn("796013036-1")
                        .coverImageUrl("https://dummyimage.com/234x100.png/dddddd/000000")
                        .comments(new HashSet<>())
                        .build(),
                Book.builder().bookId(2L)
                        .title("The Stranger")
                        .author(author2)
                        .blurb("Katniss Everdeen fights for survival in a dystopian society.")
                        .publicationYear(1985)
                        .genres(Set.of(new Genre(10L, "Thriller")))
                        .isbn("184252720-7")
                        .coverImageUrl("https://dummyimage.com/236x100.png/cc0000/ffffff")
                        .comments(new HashSet<>())
                        .build()
        );
    }

    @Test
    void bookSerializationTest() throws IOException {
        Book book = books[0];

        // Write the book to JSON
        JsonContent<Book> jsonNode = bookJson.write(book);

        assertThat(jsonNode).isStrictlyEqualToJson(singleFile);
        assertThat(jsonNode).hasJsonPathNumberValue("@.bookId");
        assertThat(jsonNode).extractingJsonPathNumberValue("@.bookId").isEqualTo(1);
        assertThat(jsonNode).hasJsonPathStringValue("@.title");
        assertThat(jsonNode).extractingJsonPathStringValue("@.title").isEqualTo("Catcher in the Rye");
        assertThat(jsonNode).extractingJsonPathNumberValue("@.author.authorId").isEqualTo(61);
        assertThat(jsonNode).hasJsonPathStringValue("@.blurb");
        assertThat(jsonNode).extractingJsonPathStringValue("@.blurb").isEqualTo("Jonas discovers the truth about his dystopian society and seeks change.");
        assertThat(jsonNode).hasJsonPathNumberValue("@.publicationYear");
        assertThat(jsonNode).extractingJsonPathNumberValue("@.publicationYear").isEqualTo(2004);
        assertThat(jsonNode).hasJsonPathArrayValue("@.genres");
        assertThat(jsonNode).extractingJsonPathArrayValue("@.genres")
                .containsExactly(Map.of("genreId", 14, "name", "Dystopian"));
        assertThat(jsonNode).hasJsonPathStringValue("@.isbn");
        assertThat(jsonNode).extractingJsonPathStringValue("@.isbn").isEqualTo("796013036-1");
        assertThat(jsonNode).hasJsonPathStringValue("@.coverImageUrl");
        assertThat(jsonNode).extractingJsonPathStringValue("@.coverImageUrl").isEqualTo("https://dummyimage.com/234x100.png/dddddd/000000");
    }

    @Test
    void bookDeserializationTest() throws IOException {
        String expected = """
                {
                           "bookId": 1,
                           "title": "Catcher in the Rye",
                           "author": {
                             "authorId": 61,
                             "name": "Harper Lee",
                             "dateOfBirth": "1950-07-03",
                             "gender": "Female",
                             "books": []
                           },
                           "blurb": "Jonas discovers the truth about his dystopian society and seeks change.",
                           "publicationYear": 2004,
                           "genres": [
                             {
                               "genreId": 14,
                               "name": "Dystopian"
                             }
                           ],
                           "isbn": "796013036-1",
                           "coverImageUrl": "https://dummyimage.com/234x100.png/dddddd/000000",
                           "comments": []
                         }
                """;

        Book newBook = books[0];

        assertThat(bookJson.parse(expected))
                .isEqualTo(newBook);
        assertThat(bookJson.parseObject(expected).getBookId()).isEqualTo(1);
        assertThat(bookJson.parseObject(expected).getTitle()).isEqualTo(newBook.getTitle());
        assertThat(bookJson.parseObject(expected).getAuthor()).isEqualTo(newBook.getAuthor());
        assertThat(bookJson.parseObject(expected).getBlurb()).isEqualTo(newBook.getBlurb());
        assertThat(bookJson.parseObject(expected).getPublicationYear()).isEqualTo(newBook.getPublicationYear());
        assertThat(bookJson.parseObject(expected).getGenres()).isEqualTo(newBook.getGenres());
        assertThat(bookJson.parseObject(expected).getIsbn()).isEqualTo(newBook.getIsbn());
        assertThat(bookJson.parseObject(expected).getCoverImageUrl()).isEqualTo(newBook.getCoverImageUrl());
    }

    @Test
    void bookListSerializationTest() throws IOException {
        assertThat(bookJsonList.write(books)).isStrictlyEqualToJson(listFile);
    }

    @Test
    void bookListDeserializationTest() throws IOException {
        String expected = """
                [
                    {
                            "bookId": 1,
                            "title": "Catcher in the Rye",
                            "author": {
                              "authorId": 61,
                              "name": "Harper Lee",
                              "dateOfBirth": "1950-07-03",
                              "gender": "Female",
                              "books": []
                            },
                            "blurb": "Jonas discovers the truth about his dystopian society and seeks change.",
                            "publicationYear": 2004,
                            "genres": [
                              {
                                "genreId": 14,
                                "name": "Dystopian"
                              }
                            ],
                            "isbn": "796013036-1",
                            "coverImageUrl": "https://dummyimage.com/234x100.png/dddddd/000000",
                            "comments": []
                          },
                          {
                            "bookId": 2,
                            "title": "The Stranger",
                            "author": {
                              "authorId": 83,
                              "name": "Random Author",
                              "dateOfBirth": "1960-08-05",
                              "gender": "Male",
                              "books": []
                            },
                            "blurb": "Katniss Everdeen fights for survival in a dystopian society.",
                            "publicationYear": 1985,
                            "genres": [
                              {
                                "genreId": 10,
                                "name": "Thriller"
                              }
                            ],
                            "isbn": "184252720-7",
                            "coverImageUrl": "https://dummyimage.com/236x100.png/cc0000/ffffff",
                            "comments": []
                          }
                  ]
                """;

        assertThat(bookJsonList.parse(expected)).isEqualTo(books);
    }
}
