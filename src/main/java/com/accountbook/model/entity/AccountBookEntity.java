package com.accountbook.model.entity;

import com.accountbook.dto.AccountBookDto;
import com.accountbook.model.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "account_book")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE account_book SET use_yn = 'N' WHERE account_id = ?")
@Where(clause = "use_yn = 'Y'")
public class AccountBookEntity extends BaseTimeEntity {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "comment",nullable = true,length = 255)
    private String comment;

    @Column(name = "account_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "use_yn",nullable = false,length = 1)
    private String useYn;

    public static AccountBookEntity of(String comment, int price, String accountType, UserEntity userEntity) {
        AccountBookEntity accountBookEntity = new AccountBookEntity();
        accountBookEntity.setComment(comment);
        accountBookEntity.setPrice(price);
        accountBookEntity.setUseYn("Y");

        if(accountType.equals("income")){
            accountBookEntity.setAccountType(AccountType.INCOME);
        }else{
            accountBookEntity.setAccountType(AccountType.OUTCOME);
        }
        accountBookEntity.setUserEntity(userEntity);
        return accountBookEntity;
    }
}
