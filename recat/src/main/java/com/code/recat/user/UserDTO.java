package com.code.recat.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO{
    private Long userId;
    private String name;
    private String username;
    private String email;
    private String gender;
}
