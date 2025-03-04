CREATE TABLE POINT_HISTORIES
(
    ID         NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, -- 포인트 기록 ID (기본 키)
    CONTENT    VARCHAR2(255) NULL,                                  -- 포인트 상세 내용
    AMOUNT     NUMBER NULL,                                         -- 포인트 양 (+/- 가능)
    CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP,                      -- 생성일 (자동 설정)
    UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP,                      -- 업데이트일 (자동 설정)
    DELETED_AT TIMESTAMP NULL,                                      -- 삭제일 (NULL 허용)
    UUID       NUMBER NOT NULL                                      -- 회원 고유번호 (외래 키)
);