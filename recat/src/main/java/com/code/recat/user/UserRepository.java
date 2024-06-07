package com.code.recat.user;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends ListCrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
