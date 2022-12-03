package com.example.haru.service;

import com.example.haru.Dto.UserDto;
import com.example.haru.domain.User;

public interface UserService {

    public UserDto signup(UserDto signUpDto);

//    public ResponseEntity<TokenDto> login(LoginDto loginDto);



    public void update(UserDto userDto);

    public UserDto getMyUserWithAuthorities();
}
