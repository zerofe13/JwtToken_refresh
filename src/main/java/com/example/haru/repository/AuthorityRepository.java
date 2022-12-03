package com.example.haru.repository;

import com.example.haru.domain.Authority;
import com.example.haru.domain.RefreshDAO;
import com.example.haru.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,String> {

}
