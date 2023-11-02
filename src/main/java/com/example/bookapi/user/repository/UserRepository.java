package com.example.bookapi.user.repository;

import com.example.bookapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.name LIKE %?1% OR u.username LIKE %?1% OR u.email LIKE %?1%")
    List<User> searchByTerm(String term);

}
