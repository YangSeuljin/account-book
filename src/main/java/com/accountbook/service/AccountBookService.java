package com.accountbook.service;

import com.accountbook.dto.AccountBookDto;
import com.accountbook.model.entity.AccountBookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountBookService {
    public Page<AccountBookEntity> getAccountBookList(String email, Pageable pageable);

    public long save(String email, AccountBookDto accountBookDto);

    public AccountBookEntity modify(Long accountId, AccountBookDto accountBookDto, String email);

    public void delete(Long accountId, String email);
}
