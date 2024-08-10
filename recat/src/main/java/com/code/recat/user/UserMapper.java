package com.code.recat.user;

public class UserMapper {

    public static UserDTO mapUserToDto (User user){
        return UserDTO.builder()
                .id(user.getUserId())
                .name(user.getName())
                .username(user.getProfileName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }
}
