package com.code.recat;
import com.code.recat.enums.Role;
import com.code.recat.enums.Gender;

public record User(
        int user_id,
        String name,
        String username,
        String email,
        Gender gender,
        String password,
        Role role
) {
}
