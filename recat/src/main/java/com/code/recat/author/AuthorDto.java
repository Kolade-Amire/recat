package com.code.recat.author;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthorDto {
    private Long authorId;
    private String name;
}
