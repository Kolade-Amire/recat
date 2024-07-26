package com.code.recat.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTestDto {
    private Long userId;
    private String name;
    private String username;
    private String email;
    private String gender;
    private String password;
    private Role role;
}



