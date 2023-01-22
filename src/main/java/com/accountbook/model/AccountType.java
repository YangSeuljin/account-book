package com.accountbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum AccountType {
    //수입
    INCOME,
    //지출
    OUTCOME;
}
