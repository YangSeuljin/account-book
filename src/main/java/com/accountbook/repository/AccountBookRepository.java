package com.accountbook.repository;

import com.accountbook.model.entity.AccountBookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {
    @Query("select a from AccountBookEntity a where a.userEntity.id = :userId and a.useYn = 'Y'")
    Page<AccountBookEntity> findAllByUserEntityAndUseYn(@Param("userId") Long userId, Pageable pageable);
}
