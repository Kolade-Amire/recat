package com.code.recat.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthorDto {
    private Long id;
    private String name;
}
