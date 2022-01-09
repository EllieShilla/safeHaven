package com.example.safehaven.repo;

import com.example.safehaven.dao.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Integer i);

}
