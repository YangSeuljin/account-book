package com.accountbook.service.impl;

import com.accountbook.model.entity.UserEntity;
import com.accountbook.repository.UserRepository;
import com.accountbook.service.UserService;
import com.accountbook.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        validateDuplicateMember(userEntity);
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException());
    }

    private void validateDuplicateMember(UserEntity userEntity) {
        //이미 가입된 회원의 경우 IllegalStateException 예외를 발생시킴.
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new IllegalStateException("회원 정보가 일치하지 않습니다.");
        }
    }

    public String login(String userEmail, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = loadUserByEmail(userEmail);

        // 비밀번호 체크
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new IllegalStateException("회원 정보가 일치하지 않습니다.");
        }

        //토큰 생성
        String token = JwtTokenUtils.generateToken(userEmail, secretKey, expiredTimeMs);
        return token;
    }
}
