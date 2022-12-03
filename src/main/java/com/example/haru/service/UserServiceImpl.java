package com.example.haru.service;

import com.example.haru.Dto.UserDto;
import com.example.haru.Exception.NotFoundMemberException;
import com.example.haru.domain.Authority;
import com.example.haru.domain.User;
import com.example.haru.repository.UserRepository;
import com.example.haru.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public UserDto signup(UserDto signUpDto) {
        if(userRepository.findByUsernameWithAuthorities(signUpDto.getUsername()).isPresent()){
            throw new RuntimeException("your email is already exist");
        }
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .nickname(signUpDto.getNickname())
                .username(signUpDto.getUsername())
                .password(encoder.encode(signUpDto.getPassword()))
                .phone(signUpDto.getPhone())
                .authorities(Collections.singleton(authority))
                .build();
        return UserDto.from(userRepository.save(user));
    }

    @Override
    public void update(UserDto userDto) {

    }

    @Override
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findByUsernameWithAuthorities)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

}
