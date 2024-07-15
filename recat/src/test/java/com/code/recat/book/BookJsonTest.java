package com.code.recat.book;


import com.code.recat.genre.Genre;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
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
        books = Arrays.array(
                Book.builder()
                        .book_id(1)
                        .title("Catcher in the Rye").author_id(61)
                        .blurb("Jonas discovers the truth about his dystopian society and seeks change.")
                        .publication_year(2004)
                        .genres(Set.of(new Genre(14, "Dystopian")))
                        .isbn("796013036-1")
                        .cover_image_url("http://dummyimage.com/234x100.png/dddddd/000000")
                        .build(),
                Book.builder().book_id(2)
                        .title("The Stranger").author_id(83)
                        .blurb("Katniss Everdeen fights for survival in a dystopian society.")
                        .publication_year(1985)
                        .genres(Set.of(new Genre(10, "Thriller")))
                        .isbn("184252720-7")
                        .cover_image_url("http://dummyimage.com/236x100.png/cc0000/ffffff")
                        .build()
        );
    }

    @Test
    void bookSerializationTest() throws IOException {
        Book book = books[0];

        // Write the book to JSON
        JsonContent<Book> jsonNode = bookJson.write(book);
        
        assertThat(jsonNode).isStrictlyEqualToJson(singleFile);
        assertThat(jsonNode).hasJsonPathNumberValue("@.book_id");
        assertThat(jsonNode).extractingJsonPathNumberValue("@.book_id").isEqualTo(1);
        assertThat(jsonNode).hasJsonPathStringValue("@.title");
        assertThat(jsonNode).extractingJsonPathStringValue("@.title").isEqualTo("Catcher in the Rye");
        assertThat(jsonNode).hasJsonPathNumberValue("@.author_id");
        assertThat(jsonNode).extractingJsonPathNumberValue("@.author_id").isEqualTo(61);
        assertThat(jsonNode).hasJsonPathStringValue("@.blurb");
        assertThat(jsonNode).extractingJsonPathStringValue("@.blurb").isEqualTo("Jonas discovers the truth about his dystopian society and seeks change.");
        assertThat(jsonNode).hasJsonPathNumberValue("@.publication_year");
        assertThat(jsonNode).extractingJsonPathNumberValue("@.publication_year").isEqualTo(2004);
        assertThat(jsonNode).hasJsonPathArrayValue("@.genres");
        assertThat(jsonNode).extractingJsonPathArrayValue("@.genres")
                .containsExactly(Map.of("genre_id", 14, "name", "Dystopian"));
        assertThat(jsonNode).hasJsonPathStringValue("@.isbn");
        assertThat(jsonNode).extractingJsonPathStringValue("@.isbn").isEqualTo("796013036-1");
        assertThat(jsonNode).hasJsonPathStringValue("@.cover_image_url");
        assertThat(jsonNode).extractingJsonPathStringValue("@.cover_image_url").isEqualTo("http://dummyimage.com/234x100.png/dddddd/000000");
    }

    @Test
    void bookDeserializationTest() throws IOException {
        String expected = """
                {
                     "book_id": 1,
                     "title": "Catcher in the Rye",
                     "author_id": 61,
                     "blurb": "Jonas discovers the truth about his dystopian society and seeks change.",
                     "publication_year": "2004",
                     "genres": [
                       {
                         "genre_id": 14,
                         "name": "Dystopian"
                       }
                     ],
                     "isbn": "796013036-1",
                     "cover_image_url": "http://dummyimage.com/234x100.png/dddddd/000000"
                   }
                """;

        Book newBook = Book.builder()
                .book_id(1)
                .title("Catcher in the Rye").author_id(61)
                .blurb("Jonas discovers the truth about his dystopian society and seeks change.")
                .publication_year(2004)
                .genres(Set.of(new Genre(14, "Dystopian")))
                .isbn("796013036-1")
                .cover_image_url("http://dummyimage.com/234x100.png/dddddd/000000")
                .build();

        assertThat(bookJson.parse(expected))
                .isEqualTo(newBook);
        assertThat(bookJson.parseObject(expected).getBook_id()).isEqualTo(1);
        assertThat(bookJson.parseObject(expected).getTitle()).isEqualTo(newBook.getTitle());
        assertThat(bookJson.parseObject(expected).getAuthor_id()).isEqualTo(newBook.getAuthor_id());
        assertThat(bookJson.parseObject(expected).getBlurb()).isEqualTo(newBook.getBlurb());
        assertThat(bookJson.parseObject(expected).getPublication_year()).isEqualTo(newBook.getPublication_year());
        assertThat(bookJson.parseObject(expected).getGenres()).isEqualTo(newBook.getGenres());
        assertThat(bookJson.parseObject(expected).getIsbn()).isEqualTo(newBook.getIsbn());
        assertThat(bookJson.parseObject(expected).getCover_image_url()).isEqualTo(newBook.getCover_image_url());
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
                      "book_id": 1,
                      "title": "Catcher in the Rye",
                      "author_id": 61,
                      "blurb": "Jonas discovers the truth about his dystopian society and seeks change.",
                      "publication_year": "2004",
                      "genres": [
                        {
                          "genre_id": 14,
                          "name": "Dystopian"
                        }
                      ],
                      "isbn": "796013036-1",
                      "cover_image_url": "http://dummyimage.com/234x100.png/dddddd/000000"
                    },
                    {
                      "book_id": 2,
                      "title": "The Stranger",
                      "author_id": 83,
                      "blurb": "Katniss Everdeen fights for survival in a dystopian society.",
                      "publication_year": "1985",
                      "genres": [
                        {
                          "genre_id": 10,
                          "name": "Thriller"
                        }
                      ],
                      "isbn": "184252720-7",
                      "cover_image_url": "http://dummyimage.com/236x100.png/cc0000/ffffff"
                    }
                  ]
                """;

        assertThat(bookJsonList.parse(expected)).isEqualTo(books);
    }
}
