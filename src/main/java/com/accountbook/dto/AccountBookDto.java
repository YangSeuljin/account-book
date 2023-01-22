package com.accountbook.dto;

import com.accountbook.model.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBookDto {
    private Long id;

    private int price;
    private String comment;
    private String accountType;

    private UserEntity userEntity;
}
