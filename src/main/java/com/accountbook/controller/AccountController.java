package com.accountbook.controller;

import com.accountbook.dto.AccountBookDto;
import com.accountbook.model.entity.AccountBookEntity;
import com.accountbook.service.AccountBookService;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/accountBook")
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;
    private final AccountBookService accountBookService;

    @GetMapping(value = {"/accountBookList", "/accountBookList/{page}"})
    public Page<AccountBookEntity> accountBookList(@PathVariable("page") Optional<Integer> page, Authentication authentication) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        return accountBookService.getAccountBookList(authentication.getName(), pageable);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<?> accountBookNew(Authentication authentication, @RequestBody AccountBookDto accountBookDto) {
        return ResponseEntity.ok(accountBookService.save(authentication.getName(), accountBookDto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> accountBookDtl(@PathVariable Long accountId, Authentication authentication) {
        AccountBookEntity post = accountBookService.accountBookDtl(accountId, authentication.getName());
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<?> modify(@PathVariable Long accountId, @RequestBody AccountBookDto accountBookDto, Authentication authentication) {
        AccountBookEntity post = accountBookService.modify(accountId, accountBookDto, authentication.getName());
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> delete(@PathVariable Long accountId, Authentication authentication) {
        accountBookService.delete(accountId, authentication.getName());
        return ResponseEntity.ok("삭제 완료.");
    }
}
