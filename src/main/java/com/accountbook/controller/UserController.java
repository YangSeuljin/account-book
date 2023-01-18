package com.accountbook.controller;

import com.accountbook.dto.UserDto;
import com.accountbook.model.entity.UserEntity;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserDto userDto) {
        // join
        userService.saveUser(UserEntity.createUserEntity(userDto, passwordEncoder));
        return new ResponseEntity<>("User 등록 성공", HttpStatus.OK);
    }
}
