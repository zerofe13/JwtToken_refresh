package com.example.haru.repository;

import com.example.haru.domain.RefreshDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshDAORepository extends JpaRepository<RefreshDAO,Long> {

    @Query("SELECT r FROM RefreshDAO r left join fetch r.user where r.refreshToken = :refreshToken")
    Optional<RefreshDAO> findByRefreshTokenWithUser(@Param("refreshToken") String refreshToken);

    Optional<RefreshDAO> findByRefreshToken(String refreshToken);
}
