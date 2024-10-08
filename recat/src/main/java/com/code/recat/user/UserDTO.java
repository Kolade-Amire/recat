package com.code.recat.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO{
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String gender;
}
