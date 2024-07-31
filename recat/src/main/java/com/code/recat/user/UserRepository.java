package com.code.recat.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    Optional<User> findByEmail(String email);

//    @Query("SELECT u FROM User u JOIN FETCH u.tokens WHERE u.email = :email")
//    @Transactional
//    Optional<UserDTO> findUserAndTokensByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.tokens t " +
            "LEFT JOIN FETCH u.favoriteBooks fb " +
            "WHERE u.email = :email")
    @Transactional
    Optional<User> findUserAndTokensByEmail(@Param("email") String email);




}
