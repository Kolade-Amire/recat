package com.code.recat.author;

import java.util.Date;

import org.springframework.data.relational.core.mapping.Table;

@Table("authors")
public record Author(
        int author_id,
        String name,
        Date date_of_birth,
        String gender
) {}