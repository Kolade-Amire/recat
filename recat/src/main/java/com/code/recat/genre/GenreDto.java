package com.code.recat.genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenreDto {
    private Integer id;
    private String name;
}
