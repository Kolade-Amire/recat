package com.code.recat.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

//    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tokens WHERE u.email = :email")
//    Optional<User> findUserWithTokens(@Param("email") String email);

    @Query("SELECT t.user FROM Token t WHERE t.user.email = :email AND (t.expired = false OR t.revoked = false)")
    Optional<User> findUserWithTokens(@Param("email") String email);

}
