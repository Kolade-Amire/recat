package com.code.recat.author;

import java.util.Date;
import com.code.recat.enums.Gender;

public record Author(
        int author_id,
        String name,
        Date date_of_birth,
        Gender gender
) {}