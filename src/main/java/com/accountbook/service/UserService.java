package com.accountbook.service;

import com.accountbook.model.entity.UserEntity;

public interface UserService {
    public UserEntity saveUser(UserEntity userEntity);

    public UserEntity loadUserByEmail(String email);

}
