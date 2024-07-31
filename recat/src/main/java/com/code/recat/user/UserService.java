package com.code.recat.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User getUserByEmail(String email) throws UsernameNotFoundException;
    User getUserWithTokensByEmail(String email) throws UsernameNotFoundException;

    User saveUser(User user);

    User getUserById (Long id);

    User updateName (String email, UserRequest userRequest);

    void deleteUser (Long id);



}
