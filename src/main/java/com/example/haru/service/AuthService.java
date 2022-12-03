package com.example.haru.service;

import com.example.haru.Dto.TokenDto;
import com.example.haru.domain.RefreshDAO;
import com.example.haru.domain.User;
import com.example.haru.jwt.TokenProvider;
import com.example.haru.repository.AuthorityRepository;
import com.example.haru.repository.RefreshDAORepository;
import com.example.haru.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly= true)
public class AuthService {

    private final RefreshDAORepository refreshDAORepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public TokenDto authorize(String username, String password){
        Authentication authentication = getAuthentication(username, password);

        TokenDto token = tokenProvider.createToken(authentication);
        Optional<User> findUser = userRepository.findByUsername(username);

        RefreshDAO refreshDAO = RefreshDAO.builder().user(findUser.get()).refreshToken(token.getRefreshToken()).build();
        refreshDAORepository.save(refreshDAO);

        return token;
    }

    public TokenDto reissueToken(String refreshToken){
        log.info("--------------------------------");
        log.info(refreshToken);
        Optional<RefreshDAO> findTokenDao = refreshDAORepository.findByRefreshToken(refreshToken);
        log.info(findTokenDao.get().getRefreshToken());

        if (findTokenDao.isEmpty()) throw new RuntimeException("No refresh token found");
        if(tokenProvider.rtk_validateToken(findTokenDao.get().getRefreshToken())) throw new RuntimeException("Invalid token, please logout");
        // reissue
        Authentication authentication = tokenProvider.getAuthenticationForRefreshToken(findTokenDao.get().getRefreshToken());
        String accessToken = tokenProvider.createAccessToken(authentication);

        return TokenDto.builder().accessToken(accessToken).refreshToken(findTokenDao.get().getRefreshToken()).build();
    }

    private Authentication getAuthentication(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //Security setAuthentication
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
