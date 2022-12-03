package com.example.haru.repository;

import com.example.haru.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u left join fetch u.authorities a WHERE u.username = :username")
    Optional<User> findByUsernameWithAuthorities(@Param("username") String username);

    Optional<User> findByUsername(String username);

}
