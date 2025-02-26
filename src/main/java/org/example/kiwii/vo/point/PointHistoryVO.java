package org.example.kiwii.vo.point;

import java.sql.Timestamp;

public class PointHistoryVO {
    private int id;           // 포인트 기록 ID (자동 증가)
    private String content;    // 포인트 상세 내용
    private int amount;    // 포인트 양 (+/- 가능)
    private Timestamp createdAt;  // 생성일 (자동 설정)
    private Timestamp updatedAt;  // 업데이트일 (자동 설정)
    private Timestamp deletedAt;  // 삭제일 (NULL 허용)
    private int uuid;         // 회원 고유번호 (USERS 테이블 FK)

    public PointHistoryVO() {}




    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

