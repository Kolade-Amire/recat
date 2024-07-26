package com.code.recat.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@Transactional(rollbackOn = Exception.class)
public interface TokenRepository extends JpaRepository<Token, Long> {

//    @Query(value = "select t from Token t inner join User u on t.user.userId = u.userId where u.userId = :id and (t.expired = false or t.revoked = false)")
//    List<Token> findAllValidTokenByUser(Long id);

    @Query("select t from Token t inner join fetch t.user u where u.userId = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByUser(@Param("id")Long id);


    Optional<Token> findByToken(String token);
}
