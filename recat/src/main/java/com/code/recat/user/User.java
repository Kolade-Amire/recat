package com.code.recat.user;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
        @Id
        int user_id,
        @NotBlank
        String name,
        @NotBlank
        String username,
        String email,
        String gender,
        @NotBlank
        String password,
        @NotBlank
        String role
) {
}
