package com.example.haru;

import com.example.haru.domain.Authority;
import com.example.haru.domain.User;
import com.example.haru.repository.AuthorityRepository;
import com.example.haru.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
@Transactional
class HaruApplicationTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void createUser(){

        Authority auth = new Authority("ROLE_ADMIN");
        authorityRepository.save(auth);
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        em.persist(authority);

        User user = User.builder().username("test")
                .password("test")
                .nickname("test@test.com")
                .authorities(Collections.singleton(authority))
                .build();
        User saveUser = userRepository.save(user);

        Optional<User> findUser = userRepository.findByUsernameWithAuthorities(saveUser.getUsername());

        Assertions.assertThat(findUser.get().getId()).isEqualTo(saveUser.getId());
    }
}
