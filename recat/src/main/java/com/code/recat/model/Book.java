package com.code.recat.model;

import jakarta.persistence.*;

public class Book {
    private Long id;
    private String title;
    private Author author;
    private String isbn;
    private String genre;
    private int publicationYear;
    private Publisher publisher;
    private String description;
    private String language;
}

