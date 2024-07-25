package com.code.recat.author;

import com.code.recat.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorRequest {
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
}
