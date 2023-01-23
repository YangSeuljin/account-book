# account-book

가계부 API

# DDL

# <회원 테이블 DDL>

create table user (user_id bigint not null, reg_time datetime(6), update_time datetime(6), email varchar(255) not null, password varchar(255) not null, role varchar(255) not null, primary key (user_id)) engine=InnoDB

alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email)
# <가계부 테이블 DDL>

create table account_book (account_id bigint not null, reg_time datetime(6), update_time datetime(6), account_type varchar(255) not null, comment varchar(255), price integer, use_yn varchar(1) not null, user_id bigint, primary key (account_id)) engine=InnoDB

alter table account_book add constraint FKkquwu309cpmhm08oyl36f7wp2 foreign key (user_id) references user (user_id)

# API 설계
     Method   URI       Description  Response Parameters(example)
- POST /api/users/join      회원가입   {"email":"test@test.com", "password": "test"}
- POST /api/user/login 로그인 {"email":"test@test.com", "password": "test"}
- GET /api/accountBook/accountBookList/{page} 가계부 전체 목록 조회 (page)
- GET /api/accountBook/{accountId} 가계부 단일 조회
- POST /api/accountBook/new 가계부 추가 {"price":5000, "comment": "test", "accountType":"income"}
- PUT /api/accountBook/{accountId} 가계부 수정 {"price":2000, "comment": "test", "accountType":"outcome"}
- DELETE /api/accountBook/{accountId} 가계부 삭제

# 설명
- JWT TOKEN을 이용하여 로그인 기능 구현
- Dockerfile 이용하여 Docker Image 생성