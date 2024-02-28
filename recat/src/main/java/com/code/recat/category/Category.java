package com.code.recat.category;

import org.springframework.data.annotation.Id;

public record Category(
        @Id
        int id,
        String name
) {
}
