package com.accountbook.service.impl;

import com.accountbook.dto.AccountBookDto;
import com.accountbook.model.AccountType;
import com.accountbook.model.entity.AccountBookEntity;
import com.accountbook.model.entity.UserEntity;
import com.accountbook.repository.AccountBookRepository;
import com.accountbook.repository.UserRepository;
import com.accountbook.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountBookServiceImpl implements AccountBookService {
    private final UserRepository userRepository;
    private final AccountBookRepository accountBookRepository;

    @Transactional(readOnly = true)
    public Page<AccountBookEntity> getAccountBookList(String email, Pageable pageable) {
        UserEntity userEntity = getUserEntityOrException(email);
        return accountBookRepository.findAllByUserEntityAndUseYn(userEntity.getId(), pageable);
    }

    public long save(String email, AccountBookDto accountBookDto) {
        UserEntity userEntity = getUserEntityOrException(email);
        accountBookDto.setUserEntity(userEntity);
        AccountBookEntity accountBookEntity = accountBookRepository.save(AccountBookEntity.of(accountBookDto.getComment(),
                accountBookDto.getPrice(),
                accountBookDto.getAccountType(),
                accountBookDto.getUserEntity()));
        return accountBookEntity.getId();
    }

    @Transactional
    public AccountBookEntity accountBookDtl(Long accountId, String email) {
        UserEntity userEntity = getUserEntityOrException(email);
        //post exist
        AccountBookEntity accountBookEntity = getAccountEntityOrException(accountId);

        //post permission
        if (accountBookEntity.getUserEntity() != userEntity) {
            throw new EntityNotFoundException();
        }

        return accountBookRepository.findById(accountBookEntity.getId()).get();
    }

    @Transactional
    public AccountBookEntity modify(Long accountId, AccountBookDto accountBookDto, String email) {
        UserEntity userEntity = getUserEntityOrException(email);

        //post exist
        AccountBookEntity accountBookEntity = getAccountEntityOrException(accountId);

        //post permission
        if (accountBookEntity.getUserEntity() != userEntity) {
            throw new EntityNotFoundException();
        }

        accountBookEntity.setPrice(accountBookDto.getPrice());
        accountBookEntity.setComment(accountBookDto.getComment());

        if (accountBookDto.getAccountType() == "income") {
            accountBookEntity.setAccountType(AccountType.INCOME);
        } else {
            accountBookEntity.setAccountType(AccountType.OUTCOME);
        }

        return accountBookRepository.saveAndFlush(accountBookEntity);
    }

    @Transactional
    public void delete(Long accountId, String email) {
        UserEntity userEntity = getUserEntityOrException(email);

        //post exist
        AccountBookEntity accountBookEntity = getAccountEntityOrException(accountId);

        //post permission
        if (accountBookEntity.getUserEntity() != userEntity) {
            throw new EntityNotFoundException();
        }

        accountBookRepository.delete(accountBookEntity);
    }


    private UserEntity getUserEntityOrException(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("%s not founded", email)));
    }

    private AccountBookEntity getAccountEntityOrException(Long accountId) {
        return accountBookRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException(String.format("%s not founded", accountId)));
    }
}
