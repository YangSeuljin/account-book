package com.accountbook.service.impl;

import com.accountbook.model.entity.UserEntity;
import com.accountbook.repository.UserRepository;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


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
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
