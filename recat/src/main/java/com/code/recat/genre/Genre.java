package com.code.recat.genre;

import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

@Table(name = "genre")
public record Genre(
        @Id
        int id,
        String name
) {
}
