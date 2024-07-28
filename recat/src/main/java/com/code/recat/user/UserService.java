package com.code.recat.user;

import com.code.recat.dto.UserDTO;
import com.code.recat.dto.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
    }

    @Transactional
    public UserDTO getUserWithToensByEmail(String email) throws UsernameNotFoundException {
        var user = userRepository.findUserWithTokens(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
        user.getTokens().size();
        return UserMapper.mapUserToUserDto(user);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
