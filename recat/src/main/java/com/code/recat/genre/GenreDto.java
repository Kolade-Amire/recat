package com.code.recat.genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenreDto {
    private Long id;
    private String name;
}
