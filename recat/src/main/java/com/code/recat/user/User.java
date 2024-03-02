package com.code.recat.user;

import com.code.recat.enums;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
        @Id
        int id,
        @NotBlank
        String name,
        @NotBlank
        String username,
        String email,
        enums.Gender gender,
        @NotBlank
        String password,
        @NotBlank
        enums.Role role

) {
}
