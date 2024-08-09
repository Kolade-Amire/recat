package com.code.recat.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@Transactional(rollbackOn = Exception.class)
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t WHERE t.user.userId = :id and (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokensByUser(@Param("id")Integer id);


    Optional<Token> findByToken(String token);
}
