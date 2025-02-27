package org.example.kiwii.vo.stock;

import java.sql.Timestamp;

public class StockVO {
    private int id;               // 주식 고유 ID
    private String ticker;        // 주식 티커
    private String name;          // 주식명
    private int probability;      // 지급 확률 (1~100)
    private String detail;        // 주식 설명
    private Timestamp createdAt;  // 생성일
    private Timestamp updatedAt;  // 업데이트일
    private Timestamp deletedAt;  // 삭제일 (NULL이면 활성 상태)

    public StockVO() {}

    public StockVO(String ticker, String name, int probability, String detail) {
        this.ticker = ticker;
        this.name = name;
        this.probability = probability;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
